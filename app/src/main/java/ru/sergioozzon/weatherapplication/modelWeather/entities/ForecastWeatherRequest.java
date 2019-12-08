package ru.sergioozzon.weatherapplication.modelWeather.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ForecastWeatherRequest implements Serializable {

    @SerializedName("list") private ArrayList<WeatherForecast> weatherForecasts;

    public ArrayList<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }
}
