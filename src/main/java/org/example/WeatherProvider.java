package org.example;

import org.example.enums.Periods;

import java.io.IOException;

/**
 * Интерфейс для провайдеров погоды
 * Ответственный за создание интерфейса - А.А. Дюжаков
 * Дата создания: 09.10.2021
 */

public interface WeatherProvider {

    void getWeather(Periods periods) throws IOException;
}
