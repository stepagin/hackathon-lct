package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YandexMapService {
    @Autowired
    LocationService locationService;

    public List<String> getAddressesByInput(String input) {
        // TODO: по входным значениям добывает не более 5 адресов, которые наиболее похожи на введённый
        return List.of(new String[]{"Краснодар, Красная, д. 139", "Краснодар, В.Н. Мачуги, 41", "Краснодар, Красных Партизан, 321"});
    }

    public String getLinkOfRouteByTwoAddresses(String address1, String address2) {
        // TODO: по двум существующим адресам возвращает ссылку на маршрут между ними в Яндекс картах
        //  гарантируется, что адреса существуют на карте мира
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
