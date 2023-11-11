package ru.lct.itmoteam.taskservice;

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
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Service
class TaskServiceApplicationTests {
	public String getJsonFromUrl(String url) {
		RestTemplate restTemplate = new RestTemplate();

		return restTemplate.getForObject(url, String.class);
	}
	private static final String API_KEY = "067cdf79-c9bc-476e-97de-8de349a232d6";
	private static final String GEOCODE_ENDPOINT = "https://geocode-maps.yandex.ru/1.x";
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
	public static void main(String[] args) {
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
	}
}
