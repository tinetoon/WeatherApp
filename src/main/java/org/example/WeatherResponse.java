package org.example;

import org.example.dto.Root;
import org.example.dto.RootNow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Класс ответа от сервера
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 10.10.2021
 */

public class WeatherResponse {

    // Временная переменная для хранения String ответа от сервера
    private String stringWeatherResponse;

    // Создаём объект с помощью библиотеки jackson для доступа к данным Json строки по ключу
    ObjectMapper objectMapper = new ObjectMapper();

    // Метод, возвращающий текущую погоду
    public double getTemperatureNow() throws JsonProcessingException {

        JsonNode temperatureNode = objectMapper.readTree(stringWeatherResponse).at("/main/temp");
        return temperatureNode.asDouble(); // возвращаем полученное значение, приведённое к типу double
    }

    // Метод парсинга и возврата ответа сервера в виде объекта (парсинг с помощью библиотеки jackson)
    public RootNow getTemperatureNowAfterParsing() {
        try {
            RootNow root = objectMapper.readValue(stringWeatherResponse, RootNow.class);
            return root;
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка парсинга: " + e.toString());
        }
        return null;
    }

    // Метод парсинга и возврата ответа сервера в виде объекта (парсинг с помощью библиотеки jackson)
    public Root getTemperatureAfterParsing() {
        try {
            Root root = objectMapper.readValue(stringWeatherResponse, Root.class);
            return root;
        } catch (JsonProcessingException e) {
            System.out.println("Ошибка парсинга: " + e.toString());
        }
        return null;
    }

    // Геттер (!!! не используется в приложении)
    public String getStringWeatherResponse() {
        return stringWeatherResponse;
    }

    // Сеттер
    public void setStringWeatherResponse(String stringWeatherResponse) {
        this.stringWeatherResponse = stringWeatherResponse;
    }
}