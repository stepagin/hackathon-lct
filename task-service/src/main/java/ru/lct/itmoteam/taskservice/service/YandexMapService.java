package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class YandexMapService {
    @Autowired
    LocationService locationService;
    private static final String API_KEY = "";
    private static final String GEOCODE_ENDPOINT = "https://geocode-maps.yandex.ru/1.x";
    private static final String ROUTING_ENDPOINT = "https://api.routing.yandex.net/v2/route";
    public String getJsonFromUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, String.class);
    }
    private static List<String> getAddressesValuesFromJSON(String xml, String value){
        List<String> textValues = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xml)));
            NodeList nodeList = document.getElementsByTagName(value);
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
//        String geocode_uri1 = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
//                .queryParam("apikey", API_KEY)
//                .queryParam("geocode", address1.replace(" ", "+"))
//                .queryParam("region", "russia")
//                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
//                .queryParam("results", "1")
//                .build().toUriString()), "pos").get(0).replace(" ", "+");
//        String geocode_uri2 = getAddressesValuesFromJSON(getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
//                .queryParam("apikey", API_KEY)
//                .queryParam("geocode", address2.replace(" ", ","))
//                .queryParam("region", "russia")
//                .queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
//                .queryParam("results", "1")
//                .build().toUriString()), "pos").get(0).replace(" ", ",");
//        String route_uri = UriComponentsBuilder.fromHttpUrl(ROUTING_ENDPOINT)
//                .queryParam("apikey", API_KEY)
//                .queryParam("waypoints", geocode_uri1+"|"+geocode_uri2)
//                .build().toUriString();
        return "https://yandex.ru/maps/35/krasnodar/?ll=39.026047%2C45.031821&mode=routes&rtext=45.012835%2C39.071711~45.044960%2C38.977047";
    }

    public String getLinkOfRouteByOneAddress(String address) {
        // TODO: по адресу возвращает маршрут в Яндекс картах от местоположения до адреса
        //  гарантируется, что адрес существует на карте мира
        return "https://yandex.ru/maps/35/krasnodar/?ll=39.026047%2C45.031821&mode=routes&rtext=45.012835%2C39.071711~45.044960%2C38.977047";
    }

    public int getMeanTimeBetweenTwoAddresses(String address1, String address2) {
        // TODO: возвращает время езды на машине между ними в минутах по двум существующим адресам
        //  гарантируется, что адреса существуют на карте мира
        //  должно использовать final таблицу или хэшмапу, а адреса могут быть как текстом, так и айдишниками
        return 36;
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
        return "https://yandex.ru/maps/35/krasnodar/?ll=39.008926%2C45.037492&mode=routes&rtext=45.053773%2C38.941986~45.044960%2C38.977047~45.012835%2C39.071711&rtt=auto&ruri=ymapsbm";
    }


}
