package org.example;

/**
 * Класс состояний приложения
 * Ответственный за создание класса - А.А. Дюжаков
 * Дата создания: 09.10.2021
 */

public final class ApplicationGlobalState {

    // Поля класса по запросу погоды от сервера
    private static ApplicationGlobalState INSTANCE;
    private String selectedCityZip = null;
    private String selectedDayPeriod = null;
    private String selectedHourPeriod = null;
    private final String APPI_ID_OW = "7063829f1fd4a64b42f6787514262064"; // API_ID (!!! создан в учебных целях, возможно потребуется замена)

    // Поля класса по работе с базой данных
    private final String DATABASE_FILE_NAME = "HomeworkWeatherDataBase.db";

    private ApplicationGlobalState() {
    }

    // Непотокобезопасный код для упрощения (!!! взят из кода с лекции, разобраться с логикой)
    public static ApplicationGlobalState getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ApplicationGlobalState();
        }
        return INSTANCE;
    }

    // Геттеры
    public String getSelectedCityZip() {
        return selectedCityZip;
    }
    public String getSelectedDayPeriod() {
        return selectedDayPeriod;
    }
    public String getSelectedHourPeriod() {
        return selectedHourPeriod;
    }
    public String getAPPI_ID_OW() {
        return APPI_ID_OW;
    }
    public String getDATABASE_FILE_NAME() {
        return DATABASE_FILE_NAME;
    }

    // Сеттеры
    public void setSelectedCity(String selectedCity) {
        this.selectedCityZip = selectedCity;
    }
    public void setSelectedDayPeriod(String selectedDayPeriod) {
        this.selectedDayPeriod = selectedDayPeriod;
    }
    public void setSelectedHourPeriod(String selectedHourPeriod) {
        this.selectedHourPeriod = selectedHourPeriod;
    }
}