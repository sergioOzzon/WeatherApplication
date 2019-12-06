package ru.sergioozzon.weatherapplication.modelWeather;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.sergioozzon.weatherapplication.MainActivity;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class City implements Serializable {

    private String cityName;
    private WeatherRequest weatherRequest;
    private static ArrayList<City> cityArrayList = new ArrayList<>();
    private static Map<String, City> cities = new HashMap<>();
    private static City currentCity;

    City(String cityName) {
        this.cityName = cityName;
        weatherRequest = new WeatherRequest();
        weatherRequest.setName(cityName);
        cityArrayList.add(this);
        cities.put(cityName, this);
    }

    public City(String cityName, SQLiteDatabase database) {
        this.cityName = cityName;
        weatherRequest = new WeatherRequest();
        weatherRequest.setName(cityName);
        cityArrayList.add(this);
        cities.put(cityName, this);
        CitiesTable.addCity(cityName, database);
    }

    public String getCityName() {
        return cityName;
    }

    public WeatherRequest getWeatherRequest() {
        return weatherRequest;
    }

    void putWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
        weatherRequest.setUpdateDate();
    }

    public static ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public static Map<String, City> getCities() {
        return cities;
    }

    public static City getCurrentCity() {
        return currentCity;
    }

    public static void setCurrentCity(City currentCity, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MainActivity.PREFERENCE_CURRENT_CITY, currentCity.cityName);
        editor.apply();
        City.currentCity = currentCity;
    }
}
