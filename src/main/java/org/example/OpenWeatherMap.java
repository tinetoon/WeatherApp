package org.example;

import org.example.classes.DataWeather;
import org.example.dto.ListWeather;
import org.example.dto.Root;
import org.example.enums.Periods;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Класс реализации запроса погоды на ресурсе openweathermap.org
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 09.10.2021
 */

public class OpenWeatherMap implements WeatherProvider {

    // Поля класса для GET запроса после "/"
    private static final String BASE_HOST = "api.openweathermap.org";
    private static final String DATA = "data";
    private static final String API_VERSION = "2.5";
    private static final String WEATHER = "weather";
    private static final String FORECAST = "forecast";

    // Поля класса для параметров KEY/VALUE
    private static String citiZip = "null";
    private static final String RUSSIA_CODE = "RU";
    private static final String APPI_ID = ApplicationGlobalState.getInstance().getAPPI_ID_OW();
    private static String cnt = "40";
    private static final String UNITS = "metric";


    // Клиент для подключения к серверу
    private final OkHttpClient client = new OkHttpClient();

    // Объект ответа от сервера
    WeatherResponse weatherResponse = new WeatherResponse();

    // Объект репозитория
    WeatherRepository myRepository = new WeatherRepository();

    // Переопределяем главный метод интерфейса WeatherProvider
    @Override
    public void getWeather(Periods periods) throws IOException {

        // Устанавливаем значения почтового индекса и периода получения прогноза погоды
        setCitiZip(ApplicationGlobalState.getInstance().getSelectedCityZip());
        setCnt(ApplicationGlobalState.getInstance().getSelectedHourPeriod());

        if (periods.equals(Periods.NOW)) {

            System.out.println("===== ПРОГНОЗ ПОГОДЫ НА ТЕКУЩУЮ ДАТУ =====");
//            weatherResponse.setStringWeatherResponse(getWeatherNow(client));
//            System.out.println("Температура: " + weatherResponse.getTemperatureNow() + " градусов.");
            weatherResponse.setStringWeatherResponse(getWeatherNow(client));
            System.out.println("Прогноз погоды для города: " + weatherResponse
                    .getTemperatureNowAfterParsing()
                    .getCityName());
            System.out.println("Дата: " + weatherResponse
                    .getTemperatureNowAfterParsing()
                    .getData() +
                    ", Температура: " + weatherResponse
                    .getTemperatureNowAfterParsing()
                    .getMain()
                    .getTemperature());

        } else {

            System.out.println("===== ПРОГНОЗ ПОГОДЫ НА " + ApplicationGlobalState.getInstance().getSelectedDayPeriod() + " ДНЕЙ =====");
            weatherResponse.setStringWeatherResponse(getWeatherPeriod(client));
            Root weatherResponseRoot = weatherResponse.getTemperatureAfterParsing();
            System.out.println("Прогноз погоды для города: " + weatherResponseRoot
                    .getCity()
                    .getCityName());
            for (ListWeather it: weatherResponseRoot.getListWeather()) {
                if (it.getDataAndTime().charAt(11) == '0' && it.getDataAndTime().charAt(12) == '9') {
                    System.out.println("Дата: " + it.getDataAndTime() + ", Температура: " + it.getMain().getTemperature());
                }
            }

            try {
                getDataWeather(weatherResponseRoot, myRepository); // Сохраняем данные в базу данных
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод возвращающий прогноз погоды на текущую дату
    public static String getWeatherNow(OkHttpClient client) throws IOException {

        // Создаём URL для отправки GET запроса
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(BASE_HOST)
                .addPathSegment(DATA)
                .addPathSegment(API_VERSION)
                .addPathSegment(WEATHER)
                .addQueryParameter("zip", (citiZip + "," + RUSSIA_CODE))
                .addQueryParameter("appid", APPI_ID)
                .addQueryParameter("units", UNITS)
                .build();

        // Выводим в консоль информацию об отправке запроса
        System.out.println("Отправляем GET запрос: " + url.toString());

        // Создаём объект отправки запросов на сервер
        Request request = new Request.Builder()
                .header("Content-type", "application/json")
                .url(url)
                .build();

        // Создаём объект получения ответов с сервера
        Response response = client.newCall(request).execute();

        // Объявляем строковую переменную и инициализируем её ответом сервера
        String body = response.body().string();

        return body;
    }

    // Метод возвращающий прогноз погоды на 5 (1-5) дней
    public static String getWeatherPeriod(OkHttpClient client) throws IOException {

        // Создаём URL для отправки GET запроса
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(BASE_HOST)
                .addPathSegment(DATA)
                .addPathSegment(API_VERSION)
                .addPathSegment(FORECAST)
                .addQueryParameter("zip", (citiZip + "," + RUSSIA_CODE))
                .addQueryParameter("appid", APPI_ID)
                .addQueryParameter("cnt", cnt)
                .addQueryParameter("units", UNITS)
                .build();

        // Выводим в консоль информацию об отправке запроса
        System.out.println("Отправляем GET запрос: " + url.toString());

        // Создаём объект отправки запросов на сервер
        Request request = new Request.Builder()
                .header("Content-type", "application/json")
                .url(url)
                .build();

        // Создаём объект получения ответов с сервера
        Response response = client.newCall(request).execute();

        // Объявляем строковую переменную и инициализируем её ответом сервера
        String body = response.body().string();

        return body;
    }

    // Метод заполняющий базу данных
    public static void getDataWeather(Root root, WeatherRepository myRepo) throws SQLException {
        DataWeather weather = new DataWeather();
        weather.setCity(root.getCity().getCityName());
        weather.setWeatherText("Ожидаемая температура: ");
        for (ListWeather it: root.getListWeather()) {
            if (it.getDataAndTime().charAt(11) == '0' && it.getDataAndTime().charAt(12) == '9') {
                weather.setDateTime(it.getDataAndTime());
                weather.setTemperature(it.getMain().getTemperature());
                myRepo.saveWeatherData(weather);
            }
        }
    }

    // Сеттер на код города (для присвоения другого кода из пользовательского ввода, см. класс UserInterface)
    public static void setCitiZip(String citiZip) {
        OpenWeatherMap.citiZip = citiZip;
    }

    // Сеттер на количество дней погоды (для присвоения значения из пользовательского ввода)
    public static void setCnt(String cnt) {
        OpenWeatherMap.cnt = cnt;
    }
}
