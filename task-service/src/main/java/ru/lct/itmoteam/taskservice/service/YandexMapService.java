package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return "https://yandex.ru/maps/35/krasnodar/?ll=39.026047%2C45.031821&mode=routes&rtext=45.012835%2C39.071711~45.044960%2C38.977047";
    }

    public String getLinkOfRouteByOneAddress(String address) {
        // TODO: по адресу возвращает маршрут в Яндекс картах от местоположения до адреса
        return "https://yandex.ru/maps/35/krasnodar/?ll=39.026047%2C45.031821&mode=routes&rtext=45.012835%2C39.071711~45.044960%2C38.977047";
    }

    public int getTimeBetweenTwoAddresses(String address1, String address2, int daytime) {
        // TODO: возвращает время езды на машине между ними в минутах по двум существующим адресам и daytime
        //  daytime - время дня в минутах (19:03 = 1143)
        //  должно использовать final таблицу или хэшмапу, а адреса могут быть как текстом, так и айдишниками
        return 36;
    }


}
