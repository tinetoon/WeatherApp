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
public class Main {

    // Поля класса
    @JsonProperty("temp")
    private Double temperature;

    // Пустой конструктор (для работы с библиотекой jackson)
    public Main() {
    }

    // Конструктор
    public Main(double temperature) {
        this.temperature = temperature;
    }

    // Геттеры
    @JsonGetter("temp")
    public Double getTemperature() {
        return temperature;
    }

    // Сеттеры
    @JsonSetter("temp")
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
