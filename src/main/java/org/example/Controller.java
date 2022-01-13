package org.example;

import org.example.enums.Functionality;
import org.example.enums.Periods;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс управления запросами
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 09.10.2021
 */

public class Controller {

    // Создаём объект провайдера погоды
    WeatherProvider weatherProvider = new OpenWeatherMap();

    // Создаём объект репозитория
    DatabaseRepository myRepository = new WeatherRepository();

    // Поле класса для проверки вариантов ввода
    Map<Integer, Functionality> variantInputUser = new HashMap<>();

    public Controller() {
        this.variantInputUser.put(0, Functionality.GET_NOW_WEATHER);
        this.variantInputUser.put(1, Functionality.GET_CUSTOM_WEATHER);
        this.variantInputUser.put(2, Functionality.GET_CUSTOM_WEATHER);
        this.variantInputUser.put(3, Functionality.GET_CUSTOM_WEATHER);
        this.variantInputUser.put(4, Functionality.GET_CUSTOM_WEATHER);
        this.variantInputUser.put(5, Functionality.GET_FIVE_DAYS_WEATHER);
        this.variantInputUser.put(6, Functionality.GET_READ_BD);
    }

    // Проверка ввода пользователя (!!! код взят с урока, разобраться с логикой)
    public void onUserInput(String input) throws IOException {

        int value = Integer.parseInt(input);

        switch (variantInputUser.get(value)) {
            case GET_NOW_WEATHER:
                getCurrentWeather();
                break;
            case GET_CUSTOM_WEATHER:
                getWeatherCustomDays();
                break;
            case GET_FIVE_DAYS_WEATHER:
                getWeatherIn5Days();
                break;
            case GET_READ_BD:
//                System.out.println("Дописать метод получения даныых из БД");// Дописать метод получения даныых из БД
                getReadBD();
                break;
        }
    }

    // Метод устанавливающий значение периода равным нулю
    public void getCurrentWeather() throws IOException {
        weatherProvider.getWeather(Periods.NOW);
    }

    // Метод устанавливающий значение периода равным значению от 1 до 4
    public void getWeatherCustomDays() throws IOException {
        weatherProvider.getWeather(Periods.CUSTOM);
    }

    // Метод устанавливающий значение периода равным пяти
    public void getWeatherIn5Days() throws IOException {
        weatherProvider.getWeather(Periods.FIVE_DAYS);
    }

    // Метод Выводящий в консоль записи БД
    public void getReadBD() throws IOException {
        myRepository.printDataBase();
    }
}
