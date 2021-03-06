package ru.sergioozzon.weatherapplication.modelWeather;

import java.io.Serializable;
import java.util.ArrayList;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class City implements Serializable {

    private String cityName;
    private WeatherRequest weatherRequest;
    private static ArrayList<City> cityArrayList = new ArrayList<>();
    private static City currentCity;
    public static boolean dataIsExist = false;

    public City(String cityName){
        this.cityName = cityName;
        weatherRequest = new WeatherRequest();
        weatherRequest.setName(cityName);
        cityArrayList.add(this);
    }

    public String getCityName() {
        return cityName;
    }

    public WeatherRequest getWeatherRequest() { return weatherRequest; }

    void putWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
        weatherRequest.setUpdateDate();
    }

    public static ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public static City getCurrentCity() {
        return currentCity;
    }

    public static void setCurrentCity(City currentCity) {
        City.currentCity = currentCity;
    }
}
