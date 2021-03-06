package ru.sergioozzon.weatherapplication.fragments;

import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.weatherapplication.R;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import ru.sergioozzon.weatherapplication.recyclerViewAdapters.WeatherAdapter;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;
import static android.content.Context.SENSOR_SERVICE;

public class WeatherFragment extends Fragment {

    private static final String CURRENT_CITY = "currentCity";
    private City city;
    private ScrollView scrollView;
    private LinearLayout sensorsLayout;
    private ProgressBar progressBar;
    private TextView cityNameTextView;
    private TextView cityTempTextView;
    private TextView descriptionTextView;
    private TextView tempOnDayTextView;
    private TextView currentDateTextView;
    private TextView updateTime;
    private TextView tempSensorTextView;
    private TextView humidSensorTextView;
    private TextView humidityTextView;
    private TextView windTextView;
    private TextView pressureTextView;
    private ImageView weatherIcon;
    private SensorManager sensorManager;
    private Sensor sensorTemp;
    private Sensor sensorHumid;
    private boolean isSensorsEnable = false;

    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(City city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable(CURRENT_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        UpdateTask updateTask = new UpdateTask();
        updateTask.execute(city);
        createHourRecyclerView(view);
        if (isSensorsEnable) {
            sensorsLayout.setVisibility(View.VISIBLE);
            getSensors();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSensorsEnable) {
            sensorManager.registerListener(setListenerSensor(sensorTemp), sensorTemp,
                    SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(setListenerSensor(sensorHumid), sensorHumid,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isSensorsEnable) {
            sensorManager.unregisterListener(setListenerSensor(sensorTemp), sensorTemp);
            sensorManager.unregisterListener(setListenerSensor(sensorHumid), sensorHumid);
        }
    }

    private SensorEventListener setListenerSensor(final Sensor sensor) {
        return new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor1, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                showSensor(event, sensor);
            }
        };
    }

    private void showSensor(SensorEvent event, Sensor sensor) {
        StringBuilder stringBuilder = new StringBuilder();
        if (sensor.getName().equals(sensorTemp.getName())) {
            stringBuilder.append("Temperature Sensor value = ").append(event.values[0]);
            tempSensorTextView.setText(stringBuilder);
        } else if (sensor.getName().equals(sensorHumid.getName())) {
            stringBuilder.append("Humidity Sensor value = ").append(event.values[0]);
            humidSensorTextView.setText(stringBuilder);
        }
    }

    private void createHourRecyclerView(@NonNull View view) {
        WeatherAdapter adapter = new WeatherAdapter();
        RecyclerView hourWeatherRecycler = view.findViewById(R.id.weatherRecyclerView);
        hourWeatherRecycler.setHasFixedSize(true);
        setDecorator(hourWeatherRecycler);
        hourWeatherRecycler.setAdapter(adapter);
    }

    private void initViews(@NonNull View view) {
        scrollView = view.findViewById(R.id.scrollView);
        progressBar = view.findViewById(R.id.progressBar2);
        cityNameTextView = view.findViewById(R.id.cityName);
        cityTempTextView = view.findViewById(R.id.cityTemp);
        descriptionTextView = view.findViewById(R.id.cityDescription);
        tempOnDayTextView = view.findViewById(R.id.tempOnDay);
        currentDateTextView = view.findViewById(R.id.currentDate);
        updateTime = view.findViewById(R.id.updateTime);
        tempSensorTextView = view.findViewById(R.id.temperature_sensor);
        humidSensorTextView = view.findViewById(R.id.humidity_sensor);
        sensorsLayout = view.findViewById(R.id.sensorsLayout);
        weatherIcon = view.findViewById(R.id.iconWeather);
        humidityTextView = view.findViewById(R.id.humidity_weather);
        windTextView = view.findViewById(R.id.wind_weather);
        pressureTextView = view.findViewById(R.id.pressure_weather);
    }

    private void setDecorator(RecyclerView weatherRecycler) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(Objects.requireNonNull(getActivity()), LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.weather_separator));
        weatherRecycler.addItemDecoration(itemDecoration);
    }

    private void getSensors() {
        sensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumid = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    class UpdateTask extends AsyncTask<City, Void, WeatherRequest> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            scrollView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected WeatherRequest doInBackground(City... cities) {
            WeatherRequest weatherRequests = cities[0].getWeatherRequest();
            while (weatherRequests.getMain() == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return weatherRequests;
        }

        @Override
        protected void onPostExecute(WeatherRequest weatherRequest) {
            super.onPostExecute(weatherRequest);
            if (weatherRequest != null) {
                DateFormat dateFormat = DateFormat.getDateInstance();
                DateFormat timeFormat = DateFormat.getTimeInstance();
                cityNameTextView.setText(String.valueOf(city.getCityName()));
                currentDateTextView.setText(dateFormat.format(city.getWeatherRequest().getUpdateDate().getTime()));
                updateTime.setText(timeFormat.format(city.getWeatherRequest().getUpdateDate().getTime()));
                cityTempTextView.setText(String.format(Locale.getDefault(), "%.0f °C", city.getWeatherRequest().getMain().getTemp()));
                descriptionTextView.setText(String.valueOf(city.getWeatherRequest().getWeather()[0].getDescription()));
                tempOnDayTextView.setText(String.format(Locale.getDefault(), "%.0f °C/%.0f °C", city.getWeatherRequest().getMain().getTempMin(), city.getWeatherRequest().getMain().getTempMax()));
                humidityTextView.setText(String.format(Locale.getDefault(), "%.0f %%", city.getWeatherRequest().getMain().getHumidity()));
                windTextView.setText(String.format(Locale.getDefault(), "%s %s", city.getWeatherRequest().getWind().getSpeed(), getString(R.string.metersPerSecond)));
                pressureTextView.setText(String.format(Locale.getDefault(), "%.0f %s", city.getWeatherRequest().getMain().getPressure(), getString(R.string.mmHgPost)));
                setWeatherIcon(city.getWeatherRequest().getWeather()[0].getId(),
                        city.getWeatherRequest().getSys().getSunrise() * 1000,
                        city.getWeatherRequest().getSys().getSunset() * 1000);
                progressBar.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
            } else {
                TextView errorTextView = new TextView(getContext());
                ConstraintLayout layout = Objects.requireNonNull(getActivity()).findViewById(R.id.WeatherLayout);
                layout.addView(errorTextView);
                errorTextView.setText("Ошибка");
                progressBar.setVisibility(View.INVISIBLE);
            }

        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        Drawable icon = getResources().getDrawable(R.drawable.unhappy);

        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {
                icon = getResources().getDrawable(R.drawable.sunny);
            } else {
                icon = getResources().getDrawable(R.drawable.moon);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = getResources().getDrawable(R.drawable.thunder);
                    break;
                }
                case 3: {
                    icon = getResources().getDrawable(R.drawable.drizzly);
                    break;
                }
                case 5: {
                    icon = getResources().getDrawable(R.drawable.rain);
                    break;
                }
                case 6: {
                    icon = getResources().getDrawable(R.drawable.snowy);
                    break;
                }
                case 7: {
                    icon = getResources().getDrawable(R.drawable.foggy);
                    break;
                }
                case 8: {
                    icon = getResources().getDrawable(R.drawable.cloud);
                    break;
                }
            }
        }
        weatherIcon.setImageDrawable(icon);
    }
}
