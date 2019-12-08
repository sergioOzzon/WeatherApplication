package ru.sergioozzon.weatherapplication.modelWeather;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Response;
import ru.sergioozzon.weatherapplication.modelWeather.entities.CurrentWeatherRequest;
import ru.sergioozzon.weatherapplication.modelWeather.entities.ForecastWeatherRequest;

public class JsonDataLoader extends AsyncTask<SQLiteDatabase, Void, Void> {

    private static final String KEY_API = "762ee61f52313fbd10a4eb54ae4d4de2";
    private static final String UNITS = "metric";
    private static final String REGION = "RU";

    public static void update(final City city, SQLiteDatabase database) throws IOException {
        //TODO: refactoring
        if (city != null) {
            Response response1 = OpenWeatherRepo.getSingleton().getAPI().loadForecastWeather(city.getCityName() + "," + REGION,
                    KEY_API, UNITS)
                    .execute();
            Log.d("RESPONSE_RESULT", response1.toString());
            if (response1.code() >= 200 && response1.code() < 300) {
                city.putForecastWeatherRequest((ForecastWeatherRequest) Objects.requireNonNull(response1.body()));
            } else {
                CitiesTable.deleteCity(database, city);
                City.getCities().remove(city.getCityName());
                City.getCityArrayList().remove(city);
            }

            Response response2 = OpenWeatherRepo.getSingleton().getAPI().loadCurrentWeather(city.getCityName() + "," + REGION,
                    KEY_API, UNITS)
                    .execute();
            Log.d("RESPONSE_RESULT", response2.toString());
            if (response2.code() >= 200 && response2.code() < 300) {
                city.putWeatherRequest((CurrentWeatherRequest) Objects.requireNonNull(response2.body()));
            } else {
                CitiesTable.deleteCity(database, city);
                City.getCities().remove(city.getCityName());
                City.getCityArrayList().remove(city);
            }
        }
    }

    @Override
    protected Void doInBackground(SQLiteDatabase... databases) {
        for (int i = 0; i < City.getCityArrayList().size(); i++) {
            try {
                update(City.getCityArrayList().get(i), databases[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
