package org.example;

import org.example.classes.DataWeather;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс для репозитория
 * Ответственный за создание интерфейса - А.А. Дюжаков
 * Дата создания: 18.10.2021
 */

public interface DatabaseRepository {

    // Контракт на подтверждение успешности сохранения данных
    boolean saveWeatherData(DataWeather dataWeather) throws SQLException;

    List<DataWeather> getAllSavedData() throws IOException;

    void printDataBase() throws IOException;
}
