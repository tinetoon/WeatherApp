package org.example.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 19.10.2021
 */

@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class City {

    // Поля класса
    @JsonProperty("name")
    private String cityName;

    // Пустой конструктор (для работы с библиотекой jackson)
    public City() {
    }

    // Конструктор
    public City(String cityName) {
        this.cityName = cityName;
    }

    // Геттеры
    @JsonGetter("name")
    public String getCityName() {
        return cityName;
    }

    // Сеттеры
    @JsonSetter("name")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
