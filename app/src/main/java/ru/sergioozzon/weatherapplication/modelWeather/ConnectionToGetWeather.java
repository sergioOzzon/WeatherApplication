package ru.sergioozzon.weatherapplication.modelWeather;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.GregorianCalendar;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionToGetWeather extends AsyncTask<Void, Void, Void> {

    private static WeatherRequest weatherRequest;


    private static WeatherRequest getWeatherRequestFromJson(City city) {

        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + city.getCityName() + ",RU&appid=240af58b6f095eb759a3ecd2d282d448";
        Connection connection = new JsonFromConn();
        String data = connection.getData(WEATHER_URL);
        Gson gson = new Gson();
        weatherRequest = gson.fromJson(data, WeatherRequest.class);
        weatherRequest.setUpdateDate(new GregorianCalendar());

        return weatherRequest;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final int SIZE = City.getCityArrayList().size();
        for (int i = 0; i < SIZE; i++) {
            City city = City.getCityArrayList().get(i);
            city.putWeatherRequest(getWeatherRequestFromJson(city));
        }
        return null;
    }
}
