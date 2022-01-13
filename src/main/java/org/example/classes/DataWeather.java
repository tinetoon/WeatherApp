package org.example.classes;

/**
 * Класс для работы с базой данных
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 18.10.2021
 */

public class DataWeather<T extends Number> {

    // Поля класса
    private String city;
    private String dateTime;
    private String weatherText;
    private T temperature;

    // Конструктор по умолчанию
    public DataWeather() {
    }

    // Конструктор для создания строки БД
    public DataWeather(String city, String dataTime, String weatherText, T temperature) {
        this.city = city;
        this.dateTime = dataTime;
        this.weatherText = weatherText;
        this.temperature = temperature;
    }

    // Геттеры
    public String getCity() {
        return city;
    }
    public String getDateTime() {
        return dateTime;
    }
    public String getWeatherText() {
        return weatherText;
    }
    public T getTemperature() {
        return temperature;
    }

    // Сеттеры
    public void setCity(String city) {
        this.city = city;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }
    public void setTemperature(T temperature) {
        this.temperature = temperature;
    }
}
