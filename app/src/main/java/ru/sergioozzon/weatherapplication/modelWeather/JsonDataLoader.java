package ru.sergioozzon.weatherapplication.modelWeather;

import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class JsonDataLoader {

    public void update(final City city) {
        OpenWeatherRepo.getSingleton().getAPI().loadWeather(city.getCityName() + ",ru",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherRequest> call,
                                           @NonNull Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            Log.d("JSON_RESULT", response.message());
                            city.putWeatherRequest(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {

                    }
                });
    }
}
