package org.example;

import org.example.classes.DataWeather;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс взаимодействия с БД
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 18.10.2021
 *
 * 1. Добавить поддержку SQLite в проект. Через Мавен добавлена библиотека SQLite JDBC » 3.36.0.3
 * 2. Создать класс-репозиторий, отвечающий за взаимодействие с базой данных.
 * 3. Организовать запись данных в базу при каждом успешном API запросе.
 * Формат - String city, String localDate, String weatherText, Double temperature.
 * 4. Организовать чтение из базы всех данных по пункту меню (требует переработки меню)
 * 5. Учесть, что соединение всегда нужно закрывать
 */

public class WeatherRepository implements DatabaseRepository {

    // Поля класса
    private String filename;
    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS weather (\n" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "city TEXT NOT NULL,\n" +
            "date_time TEXT NOT NULL,\n" +
            "weather_text TEXT NOT NULL,\n" +
            "temperature REAL NOT NULL\n" +
            ");";
    private final String INSERT_WEATHER_QUERY = "INSERT INTO weather (city, date_time, weather_text, temperature) VALUES (?, ?, ?, ?)";
    private final String READ_WEATHER_QUERY = "SELECT * FROM weather";

    // Статический блок для инициализации драйвера работы с БД
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер SQLite не зарегистрирован: " + e.toString());
        }
    }

    // Конструктор класса
    public WeatherRepository() {
        this.filename = ApplicationGlobalState.getInstance().getDATABASE_FILE_NAME();
//        createTableIfNotExists();
    }

    // Метод возвращающий объект соединения с базой данных
    private Connection getConnection() throws SQLException {
        Connection myConnection = DriverManager.getConnection("jdbc:sqlite:" + filename); // Параметры (драйвер менеджер:тип драйвера:путь до БД - jdbc:sqlite:filename)
        return myConnection;
    }

    // Метод создания таблицы
    private void createTableIfNotExists() {
        try (Connection newConnection = getConnection()) { // Конструкция try-with-resources автоматически закрывает ресурсы, открытые в блоке try
            newConnection.createStatement().execute(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            System.out.println("Повторное создание таблицы остановлено: " + e.toString());
        }
    }

    // Метод возвращающий результат сохранения данных в БД (!!! проверить возврат ошибки в нормальном режиме работы)
    @Override
    public boolean saveWeatherData(DataWeather dataWeather) throws SQLException {
//        printTest(dataWeather);
        createTableIfNotExists();
        try (Connection newConnection = getConnection(); // Конструкция try-with-resources автоматически закрывает ресурсы, открытые в блоке try
             PreparedStatement saveWeather = newConnection.prepareStatement(INSERT_WEATHER_QUERY)) {
                saveWeather.setString(1, dataWeather.getCity());
                saveWeather.setString(2, dataWeather.getDateTime());
                saveWeather.setString(3, dataWeather.getWeatherText());
                saveWeather.setDouble(4, (Double) dataWeather.getTemperature());
                return saveWeather.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Ошибка при сохранении строки с погодой в БД"); // Создаём сообщение об ошибке для выброса на верх
    }

    // Метод возвращающий лист с данными из БД (!!! дописать логику)
    @Override
    public List<DataWeather> getAllSavedData() throws IOException {
        List<DataWeather> list = new LinkedList<>();
        try (Connection newConnection = getConnection();
             Statement printWeather = newConnection.createStatement()) {
            ResultSet result = printWeather.executeQuery(READ_WEATHER_QUERY);
            while (result.next()) {
                DataWeather dataWeather = new DataWeather();
                dataWeather.setCity(result.getString(2));
                dataWeather.setDateTime(result.getString(3));
                dataWeather.setWeatherText(result.getString(4));
                dataWeather.setTemperature(result.getDouble(5));
                list.add(dataWeather);
//                System.out.println(
//                        result.getInt(1) + " | " +
//                                result.getString(2) + " | " +
//                                result.getString(3) + " | " +
//                                result.getString(4) + " | " +
//                                result.getDouble(5)
//                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IOException("Not implemented exception");
    }

    // Метод печати в консоль базы данных погоды
    @Override
    public void printDataBase() throws IOException {
        try (Connection newConnection = getConnection();
        Statement printWeather = newConnection.createStatement()) {
            ResultSet result = printWeather.executeQuery(READ_WEATHER_QUERY);
            while (result.next()) {
//                System.out.println(
//                    result.getString("city") + " | " +
//                    result.getString("date_time") + " | " +
//                    result.getString("weather_text") + " | " +
//                    result.getDouble("temperature")
//                );
                System.out.println(
                        result.getInt(1) + " | " +
                        result.getString(2) + " | " +
                        result.getString(3) + " | " +
                        result.getString(4) + " | " +
                        result.getDouble(5)
                );
            }
            System.out.println("===== Сведения из БД получены успешно =====");
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        throw new IOException("Ошибка печати БД с погодой"); // Создаём сообщение об ошибке для выброса на верх
    }

    // Проверочный метод печати значений
    private static void printTest(DataWeather dataWeather) {
        System.out.println("city: " + dataWeather.getCity() +
                ", date_time: " + dataWeather.getDateTime() +
                ", weather_text: " + dataWeather.getWeatherText() +
                ", temperature: " + dataWeather.getTemperature());
    }

}
