package ru.sergioozzon.weatherapplication.modelWeather;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Response;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class JsonDataLoader extends AsyncTask<SQLiteDatabase, Void, Void> {

    private static final String KEY_API = "762ee61f52313fbd10a4eb54ae4d4de2";
    private static final String UNITS = "metric";
    private static final String REGION = "RU";

    public static void update(final City city, SQLiteDatabase database) throws IOException {
        if (city != null) {
            Response response = OpenWeatherRepo.getSingleton().getAPI().loadWeather(city.getCityName() + "," + REGION,
                    KEY_API, UNITS)
                    .execute();
            Log.d("RESPONSE_RESULT", response.toString());
            if (response.code() >= 200 && response.code() < 300) {
                city.putWeatherRequest((WeatherRequest) Objects.requireNonNull(response.body()));
            } else {
                CitiesTable.deleteCity(database, city);
                City.getCities().remove(city.getCityName());
                City.getCityArrayList().remove(city);
            }
        }
    }

    @Override
    protected Void doInBackground(SQLiteDatabase... databases) {
        int SIZE = City.getCityArrayList().size();
        for (int i = 0; i < SIZE; i++) {
            try {
                update(City.getCityArrayList().get(i), databases[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
