package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class YandexMapService {
    @Autowired
    LocationService locationService;
    private static final String API_KEY = "";
    private static final String GEOCODE_ENDPOINT = "https://geocode-maps.yandex.ru/1.x";

    public String getJsonFromUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, String.class);
    }

    private static List<String> getAddressesValuesFromJSON(String xml, String type){
        List<String> textValues = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            NodeList nodeList = document.getElementsByTagName(type);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String text = element.getTextContent();
                textValues.add(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textValues;
    }

    public List<String> getAddressesByInput(String input) {
        // TODO: по входным значениям добывает не более 5 адресов, которые наиболее похожи на введённый
        String geocode_uri = UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", input.replace(" ", "+"))
                .queryParam("region", "russia")
                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
                .queryParam("results", "5")
                .build().toUriString();
        String json = getJsonFromUrl(geocode_uri);
        return getAddressesValuesFromJSON(json, "text");
    }

    public String getLinkOfRouteByTwoAddresses(String address1, String address2) {
        // TODO: по двум существующим адресам возвращает ссылку на маршрут между ними в Яндекс картах
        //  гарантируется, что адреса существуют на карте мира
        String geocode1 = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", address1.replace(" ", "+"))
                .queryParam("region", "russia")
                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
                .queryParam("results", "1")
                .build().toUriString()), "pos").get(0).replace(" ", ",");
        String[] parts = geocode1.split(",");
        geocode1 = parts[1] + "," + parts[0];
        String geocode2 = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", address2.replace(" ", "+"))
                .queryParam("region", "russia")
                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
                .queryParam("results", "1")
                .build().toUriString()), "pos").get(0).replace(" ", ",");
        parts = geocode2.split(",");
        geocode2 = parts[1] + "," + parts[0];
        return "https://yandex.ru/maps/?rtext=" + geocode1 + "~" + geocode2 + "&rtt=auto";
    }

    public String getLinkOfRouteByOneAddress(String address) {
        // TODO: по адресу возвращает маршрут в Яндекс картах от местоположения до адреса
        //  гарантируется, что адрес существует на карте мира
        String geocode = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", address.replace(" ", "+"))
                .queryParam("region", "russia")
                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
                .queryParam("results", "1")
                .build().toUriString()), "pos").get(0).replace(" ", ",");
        String[] parts = geocode.split(",");
        geocode = parts[1] + "," + parts[0];
        return "https://yandex.ru/maps/?rtext=" + geocode + "&rtt=auto";
    }

    public int getMeanTimeBetweenTwoAddresses(String address1, String address2) {
        String geocode1 = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", address1.replace(" ", "+"))
                .queryParam("region", "russia")
                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
                .queryParam("results", "1")
                .build().toUriString()), "pos").get(0).replace(" ", ",");
        String[] parts = geocode1.split(",");
        geocode1 = parts[1] + "," + parts[0];
        String geocode2 = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                .queryParam("apikey", API_KEY)
                .queryParam("geocode", address2.replace(" ", "+"))
                .queryParam("region", "russia")
                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
                .queryParam("results", "1")
                .build().toUriString()), "pos").get(0).replace(" ", ",");
        parts = geocode2.split(",");
        geocode2 = parts[1] + "," + parts[0];
        String response = getJsonFromUrl(UriComponentsBuilder.fromHttpUrl("https://graphhopper.com/api/1/matrix")
                        .queryParam("key", "530a162f-ce50-4a51-91c0-ab6408e1294f")
                        .queryParam("point", geocode1)
                        .queryParam("point", geocode2)
                        .queryParam("profile", "car")
                        .queryParam("out_array", "times")
                        .build().toUriString());
        HashMap<String, Integer> resultMap = new HashMap<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray timesArray = jsonObject.getJSONArray("times");
        for (int i = 0; i < timesArray.length(); i++) {
            JSONArray innerArray = timesArray.getJSONArray(i);
            for (int j = 0; j < innerArray.length(); j++) {
                if (i != j) {
                    return innerArray.getInt(j) / 60;
                }
            }
        }
        return 0;
    }

    public int getTimeBetweenTwoAddressesWithDaytime(String address1, String address2, int daytime) {
        // TODO: возвращает время езды на машине между ними в минутах по двум адресам и daytime
        //  daytime - время дня в минутах (19:03 = 1143)
        //  гарантируется, что адреса существуют на карте мира
        //  должно использовать запрос на Яндекс карты
        return 44;
    }

    public String createRouteFromList(List<String> points) {
        // TODO: возвращает ссылку на сложный маршрут по точкам, сохраняя порядок
        //  на входе указывается список адресов точек
        //  гарантируется, что точки существуют на карте мира
        List<String> geocodedAddresses = new ArrayList<>();

        for (String address : points) {
            String geocode = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
                    .queryParam("apikey", API_KEY)
                    .queryParam("geocode", address.replace(" ", "+"))
                    .queryParam("region", "russia")
                    .queryParam("results", "1")
                    .build().toUriString()), "pos").get(0).replace(" ", ",");
            String[] parts = geocode.split(",");
            geocode = parts[1] + "," + parts[0];
            geocodedAddresses.add(geocode);
        }
        String link = "https://yandex.ru/maps/?rtext=";
        String coordinatesString = String.join("~", geocodedAddresses);
        link += coordinatesString + "&rtt=auto";
        return link;
    }

}
