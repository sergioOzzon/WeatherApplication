package ru.sergioozzon.weatherapplication.modelWeather;

import ru.sergioozzon.weatherapplication.modelWeather.entities.CurrentWeatherRequest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.sergioozzon.weatherapplication.modelWeather.entities.ForecastWeatherRequest;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<CurrentWeatherRequest> loadCurrentWeather(@Query("q") String city,
                                                   @Query("appid") String keyApi,
                                                   @Query("units") String units);

    @GET("data/2.5/forecast")
    Call<ForecastWeatherRequest> loadForecastWeather(@Query("q") String city,
                                                     @Query("appid") String keyApi,
                                                     @Query("units") String units);
}
