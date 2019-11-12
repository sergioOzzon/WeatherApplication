package ru.sergioozzon.weatherapplication.modelWeather;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Response;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class JsonDataLoader extends AsyncTask<Void, Void, Void>{

    public void update(final City city) throws IOException {
        Response response = OpenWeatherRepo.getSingleton().getAPI().loadWeather(city.getCityName() + ",ru",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .execute();
        Log.d("RESPONSE_RESULT", response.toString());
        city.putWeatherRequest((WeatherRequest)response.body());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            ArrayList<City> list = City.getCityArrayList();
            for (int i = 0; i < list.size(); i++) {
                update(list.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
