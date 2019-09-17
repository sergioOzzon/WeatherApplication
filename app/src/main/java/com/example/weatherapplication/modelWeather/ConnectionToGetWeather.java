package com.example.weatherapplication.modelWeather;

import android.util.Log;

import com.example.weatherapplication.City;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionToGetWeather {

    private static String WEATHER_URL;
    private static WeatherRequest weatherRequest;
    private static City city;

    public static WeatherRequest getWeatherRequestFromJson(City city){

        ConnectionToGetWeather.city = city;

        WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + getCityName() + ",RU&appid=240af58b6f095eb759a3ecd2d282d448";

        try {
            String result;
            final URL uri = new URL(WEATHER_URL);
            HttpsURLConnection urlConnection;
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            result = in.lines().collect(Collectors.joining("\n"));
            Log.d("RESULT OF CONNECTION", result);
            Gson gson = new Gson();
            weatherRequest = gson.fromJson(result, WeatherRequest.class);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherRequest;
    }

    public static String getCityName(){
        return city.getCityName();
    }
}
