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
public class ListWeather {

    // Поля класса
    @JsonProperty("main")
    private Main main;
    @JsonProperty("dt_txt")
    private String dataAndTime;

    // Пустой конструктор (для работы с библиотекой jackson)
    public ListWeather() {
    }

    // Конструктор
    public ListWeather(Main main, String dataAndTime) {
        this.main = main;
        this.dataAndTime = dataAndTime;
    }

    // Геттеры
    @JsonGetter("main")
    public Main getMain() {
        return main;
    }
    @JsonGetter("dt_txt")
    public String getDataAndTime() {
        return dataAndTime;
    }

    // Сеттеры
    @JsonSetter("main")
    public void setMainDto(Main main) {
        this.main = main;
    }
    @JsonSetter("dt_txt")
    public void setDataAndTime(String dataAndTime) {
        this.dataAndTime = dataAndTime;
    }
}
