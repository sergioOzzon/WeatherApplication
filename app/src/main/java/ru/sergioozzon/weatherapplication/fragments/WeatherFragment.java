package ru.sergioozzon.weatherapplication.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.weatherapplication.R;

import java.util.Locale;

import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.ConnectionToGetWeather;
import ru.sergioozzon.weatherapplication.modelWeather.WeatherRequest;

public class WeatherFragment extends Fragment {

    private static final String ARG_CURRENT_CITY = "currentCity";

    private City city;

    private FrameLayout frameLayout;
    private ProgressBar progressBar;
    private TextView cityNameTextView;
    private TextView cityTempTextView;
    private TextView descriptionTextView;
    private TextView TempOnDayTextView;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(City city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENT_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(ARG_CURRENT_CITY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        UpdateTask updateTask = new UpdateTask();
        updateTask.execute(city);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(@NonNull View view) {
        frameLayout = view.findViewById(R.id.frameInWeatherFragment);
        progressBar = view.findViewById(R.id.progressBar2);
        cityNameTextView = view.findViewById(R.id.cityName);
        cityTempTextView = view.findViewById(R.id.cityTemp);
        descriptionTextView = view.findViewById(R.id.cityDescription);
        TempOnDayTextView = view.findViewById(R.id.tempOnDay);
    }

    class UpdateTask extends AsyncTask<City, Void, WeatherRequest>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            frameLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected WeatherRequest doInBackground(City... cities) {
            city.putWeatherRequest(ConnectionToGetWeather.getWeatherRequestFromJson(cities[0]));
            return null;
        }

        @Override
        protected void onPostExecute(WeatherRequest weatherRequest) {
            super.onPostExecute(weatherRequest);
            cityNameTextView.setText(String.valueOf(city.getCityName()));
            cityTempTextView.setText(String.format("%.0f °C", city.getWeatherRequest().getMain().getTemp()));
            descriptionTextView.setText(String.valueOf(city.getWeatherRequest().getWeather()[0].getDescription()));
            TempOnDayTextView.setText(String.format("%.0f °C/%.0f °C", city.getWeatherRequest().getMain().getTemp_min(), city.getWeatherRequest().getMain().getTemp_max()));
            frameLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
