package ru.sergioozzon.weatherapplication.modelWeather;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Response;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class JsonDataLoader extends AsyncTask<Void, Void, Void> {

    private static final String KEY_API = "762ee61f52313fbd10a4eb54ae4d4de2";
    private static final String UNITS = "metric";
    private static final String REGION = "RU";

    private static void update(final City city) throws IOException {
        Response response = OpenWeatherRepo.getSingleton().getAPI().loadWeather(city.getCityName() + "," + REGION,
                KEY_API, UNITS)
                .execute();
        Log.d("RESPONSE_RESULT", response.toString());
        city.putWeatherRequest((WeatherRequest) Objects.requireNonNull(response.body()));
    }

    @Override
    protected Void doInBackground(Void... voids) {
        int SIZE = City.getCityArrayList().size();
        for (int i = 0; i < SIZE; i++) {
            try {
                update(City.getCityArrayList().get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
