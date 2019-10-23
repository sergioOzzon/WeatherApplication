package ru.sergioozzon.weatherapplication.modelWeather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class City implements Serializable {

    private String cityName;
    private WeatherRequest weatherRequest;
    private static ArrayList<City> cityArrayList = new ArrayList<>();
    private static Map<String, WeatherRequest> cityMap = new HashMap<>();
    private static City currentCity;

    public City(String cityName){
        this.cityName = cityName;
        weatherRequest = new WeatherRequest();
        weatherRequest.setName(cityName);
        cityArrayList.add(this);
        currentCity = this;
    }

    public City(String cityName, WeatherRequest weatherRequest){
        this.cityName = cityName;
        this.weatherRequest = weatherRequest;
        cityArrayList.add(this);
        cityMap.put(cityName, weatherRequest);
    }

    public String getCityName() {
        return cityName;
    }

    public WeatherRequest getWeatherRequest() { return weatherRequest; }

    public void putWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
    }

    public static ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public static Map<String, WeatherRequest> getCityMap() {
        return cityMap;
    }

    public static City getCurrentCity() {
        return currentCity;
    }

    public static void setCurrentCity(City currentCity) {
        City.currentCity = currentCity;
    }
}
