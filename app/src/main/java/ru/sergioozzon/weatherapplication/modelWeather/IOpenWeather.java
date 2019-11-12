package ru.sergioozzon.weatherapplication.modelWeather;

import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String city,
                                     @Query("appid") String keyApi,
                                     @Query("units") String units);
}
