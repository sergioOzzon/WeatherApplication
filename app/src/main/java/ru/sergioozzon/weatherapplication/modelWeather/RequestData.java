package ru.sergioozzon.weatherapplication.modelWeather;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.GregorianCalendar;

public class RequestData extends AsyncTask<Void, Void, Void> {

    private static WeatherRequest weatherRequest;
    private static  final String API_KEY = "240af58b6f095eb759a3ecd2d282d448";

    private static WeatherRequest getWeatherRequestFromJson(City city) {

        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + city.getCityName() + ",RU&appid=" + API_KEY;
        DataLoader dataLoader = new JsonLoader();
        String data = dataLoader.getData(WEATHER_URL);
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
