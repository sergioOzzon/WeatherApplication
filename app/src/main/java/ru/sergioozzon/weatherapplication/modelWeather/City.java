package ru.sergioozzon.weatherapplication.modelWeather;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.sergioozzon.weatherapplication.ui.MainActivity;
import ru.sergioozzon.weatherapplication.modelWeather.entities.CurrentWeatherRequest;
import ru.sergioozzon.weatherapplication.modelWeather.entities.ForecastWeatherRequest;

public class City implements Serializable {

    private String cityName;
    private CurrentWeatherRequest currentWeatherRequest;
    private ForecastWeatherRequest forecastWeatherRequest;
    private static ArrayList<City> cityArrayList = new ArrayList<>();
    private static Map<String, City> cities = new HashMap<>();
    private static City currentCity;

    public City(String cityName) {
        this.cityName = cityName;
        currentWeatherRequest = new CurrentWeatherRequest();
        forecastWeatherRequest = new ForecastWeatherRequest();
        currentWeatherRequest.setName(cityName);
        cityArrayList.add(this);
        cities.put(cityName, this);
    }

    public String getCityName() {
        return cityName;
    }

    public CurrentWeatherRequest getCurrentWeatherRequest() {
        return currentWeatherRequest;
    }

    void putWeatherRequest(CurrentWeatherRequest currentWeatherRequest) {
        this.currentWeatherRequest = currentWeatherRequest;
        currentWeatherRequest.setUpdateDate();
    }

    public ForecastWeatherRequest getForecastWeatherRequest() {
        return forecastWeatherRequest;
    }

    public void putForecastWeatherRequest(ForecastWeatherRequest forecastWeatherRequest) {
        this.forecastWeatherRequest = forecastWeatherRequest;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        City guest = (City) obj;
        return cityName.equals(guest.cityName)
                && (currentWeatherRequest == guest.currentWeatherRequest
                || (currentWeatherRequest != null && currentWeatherRequest.equals(guest.getCurrentWeatherRequest())))
                && (forecastWeatherRequest == guest.forecastWeatherRequest
                || (forecastWeatherRequest != null && forecastWeatherRequest .equals(guest.getForecastWeatherRequest())
        ));
    }
}
