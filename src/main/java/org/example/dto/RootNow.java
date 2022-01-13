package org.example.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 20.10.2021
 */

@JsonIgnoreProperties(ignoreUnknown = true) // Игнорируем лишние поля из строки JSON
public class RootNow {

    // Поля класса
    @JsonProperty("main")
    private Main main;
    @JsonProperty("dt")
    private Long data;
    @JsonProperty("name")
    private String cityName;

    // Пустой конструктор (для работы с библиотекой jackson)
    public RootNow() {
    }

    // Конструктор
    public RootNow(Main main, String cityName) {
        this.main = main;
        this.cityName = cityName;
    }

    // Геттеры
    @JsonGetter("main")
    public Main getMain() {
        return main;
    }
    @JsonGetter("dt")
    public Long getData() {
        return data;
    }
    @JsonGetter("name")
    public String getCityName() {
        return cityName;
    }

    // Сеттеры
    @JsonSetter("main")
    public void setMain(Main main) {
        this.main = main;
    }
    @JsonSetter("dt")
    public void setData(Long data) {
        this.data = data;
    }
    @JsonSetter("name")
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
