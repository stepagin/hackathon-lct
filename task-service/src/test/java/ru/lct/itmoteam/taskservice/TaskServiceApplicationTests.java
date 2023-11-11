package ru.lct.itmoteam.taskservice;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Service
class TaskServiceApplicationTests {
	public String getJsonFromUrl(String url) {
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.getForObject(url, String.class);
	}
	private static final String API_KEY = "067cdf79-c9bc-476e-97de-8de349a232d6";
	private static final String GEOCODE_ENDPOINT = "https://geocode-maps.yandex.ru/1.x/";
	private static final String ROUTING_ENDPOINT = "https://api.routing.yandex.net/v2/route";
	public static List<String> getTextValuesFromXML(String xml, String pos){
		List<String> textValues = new ArrayList<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			NodeList nodeList = document.getElementsByTagName(pos);
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
	public static void main(String[] args) throws JSONException {
		TaskServiceApplicationTests taskServiceApplicationTests = new TaskServiceApplicationTests();
		String geocode_uri1 = getTextValuesFromXML(taskServiceApplicationTests.getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
				.queryParam("apikey", API_KEY)
				.queryParam("geocode", "142.03519 47.33614".replace(" ", ","))
				.queryParam("region", "russia")
				.queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
				.queryParam("results", "1")
				.build().toUriString()), "pos").get(0).replace(" ", "+");
		String geocode_uri2 = getTextValuesFromXML(taskServiceApplicationTests.getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
				.queryParam("apikey", API_KEY)
				.queryParam("geocode", "5.143367 60.425573".replace(" ", ","))
				.queryParam("region", "russia")
				.queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
				.queryParam("results", "1")
				.build().toUriString()), "pos").get(0).replace(" ", ",");
		String route_uri = UriComponentsBuilder.fromHttpUrl(ROUTING_ENDPOINT)
				.queryParam("apikey", API_KEY)
				.queryParam("waypoints", geocode_uri1+"|"+geocode_uri2)
				.build().toUriString();
		System.out.println(route_uri);
		OkHttpClient client = new OkHttpClient();
		System.out.println(UriComponentsBuilder.fromHttpUrl("https://graphhopper.com/api/1/matrix")
						.queryParam("key", "530a162f-ce50-4a51-91c0-ab6408e1294f")
						.queryParam("point", "60.077292,30.344758")
						.queryParam("point", "59.927288,30.338353")
						.queryParam("point", "59.956363,30.310029")
						.queryParam("profile", "car")
						.queryParam("out_array", "times")
						.build().toUriString());
		String response = taskServiceApplicationTests.getJsonFromUrl(UriComponentsBuilder.fromHttpUrl("https://graphhopper.com/api/1/matrix")
				.queryParam("key", "530a162f-ce50-4a51-91c0-ab6408e1294f")
				.queryParam("point", "60.077292,30.344758")
				.queryParam("point", "59.927288,30.338353")
				.queryParam("profile", "car")
				.queryParam("out_array", "times")
				.build().toUriString());
		System.out.println(response);
		HashMap<String, Integer> resultMap = new HashMap<>();

		JSONObject jsonObject = new JSONObject(response);
		JSONArray timesArray = jsonObject.getJSONArray("times");

		for (int i = 0; i < timesArray.length(); i++) {
			JSONArray innerArray = timesArray.getJSONArray(i);
			for (int j = 0; j < innerArray.length(); j++) {
				if (i != j) {
					int timeInMinutes = innerArray.getInt(j) / 60;
					String key = "Точка " + (i + 1) + " -> Точка " + (j + 1);
					resultMap.put(key, timeInMinutes);
				}
			}
		}
		for (String key : resultMap.keySet()) {
			System.out.println(key + ": " + resultMap.get(key) + " minutes");
		}
//		String geocode1 = taskServiceApplicationTests.getJsonFromUrl(UriComponentsBuilder.fromHttpUrl("https://graphhopper.com/api/1/geocode")
//				.queryParam("key", "530a162f-ce50-4a51-91c0-ab6408e1294f")
//				.queryParam("q", "")
//				.queryParam("locale", "en")
//				.build().toUriString());
		String geocode1 = getTextValuesFromXML(taskServiceApplicationTests.getJsonFromUrl(UriComponentsBuilder.fromHttpUrl(GEOCODE_ENDPOINT)
				.queryParam("apikey", API_KEY)
				.queryParam("geocode", "ул. Ставропольская, д. 140, Краснодар".replace(" ", ","))
				.queryParam("region", "russia")
				.queryParam("bbox", "19.642601,41.185207~190.10042,81.25044")
				.queryParam("results", "1")
				.build().toUriString()), "pos").get(0).replace(" ", ",");
		System.out.println(geocode1);
	}
}
