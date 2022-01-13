package org.example.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

/**
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 19.10.2021
 */

@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class Root {

    // Поля класса
    @JsonProperty("list")
    private List<ListWeather> listWeather;
    @JsonProperty("city")
    private City city;

    // Пустой конструктор (для работы с библиотекой jackson)
    public Root() {
    }

    // Конструктор
    public Root(List<ListWeather> listWeather, City city) {
        this.listWeather = listWeather;
        this.city = city;
    }

    // Геттеры
    @JsonGetter("list")
    public List<ListWeather> getListWeather() {
        return listWeather;
    }
    @JsonGetter("city")
    public City getCity() {
        return city;
    }

    // Сеттеры
    @JsonSetter("list")
    public void setListWeather(List<ListWeather> listWeather) {
        this.listWeather = listWeather;
    }
    @JsonSetter("city")
    public void setCity(City city) {
        this.city = city;
    }
}
