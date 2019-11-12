package ru.sergioozzon.weatherapplication.fragments;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.weatherapplication.R;
import java.text.DateFormat;
import java.util.Locale;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import ru.sergioozzon.weatherapplication.recyclerViewAdapters.WeatherAdapter;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;
import static android.content.Context.SENSOR_SERVICE;

public class WeatherFragment extends Fragment {

    private static final String CURRENT_CITY = "currentCity";
    private City city;
    private FrameLayout frameLayout;
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
    private SensorManager sensorManager;
    private Sensor sensorTemp;
    private Sensor sensorHumid;
    private boolean isSensorsEnable = false;


    private WeatherFragment() {
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
        if(isSensorsEnable) {
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

    private SensorEventListener setListenerSensor(final Sensor sensor){
        return new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor1, int accuracy) {}
            @Override
            public void onSensorChanged(SensorEvent event) {
                showSensor(event, sensor);
            }
        };
    }

    private void showSensor(SensorEvent event, Sensor sensor){
        StringBuilder stringBuilder = new StringBuilder();
        if (sensor.getName().equals(sensorTemp.getName())) {
            stringBuilder.append("Temperature Sensor value = ").append(event.values[0]);
            tempSensorTextView.setText(stringBuilder);
        } else if (sensor.getName().equals(sensorHumid.getName())){
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
        frameLayout = view.findViewById(R.id.frameInWeatherFragment);
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
    }

    private void setDecorator(RecyclerView weatherRecycler) {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
        itemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.weather_separator));
        weatherRecycler.addItemDecoration(itemDecoration);
    }

    private void getSensors() {
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        sensorTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorHumid = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
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
            JsonDataLoader loader = new JsonDataLoader();
            loader.update(cities[0]);
            return cities[0].getWeatherRequest();
        }
        @Override
        protected void onPostExecute(WeatherRequest weatherRequest) {
            super.onPostExecute(weatherRequest);
            if (weatherRequest != null) {
                Locale locale = new Locale(Locale.ENGLISH.getLanguage());
                DateFormat dateFormat = DateFormat.getDateInstance();
                DateFormat timeFormat = DateFormat.getTimeInstance();
                cityNameTextView.setText(String.valueOf(city.getCityName()));
                currentDateTextView.setText(dateFormat.format(weatherRequest.getUpdateDate().getTime()));
                updateTime.setText(timeFormat.format(weatherRequest.getUpdateDate().getTime()));
                cityTempTextView.setText(String.format(locale, "%.0f °C", weatherRequest.getMain().getTemp()));
                descriptionTextView.setText(String.valueOf(weatherRequest.getWeather()[0].getDescription()));
                tempOnDayTextView.setText(String.format(locale, "%.0f °C/%.0f °C", weatherRequest.getMain().getTempMin(), city.getWeatherRequest().getMain().getTempMax()));
            } else {
                cityNameTextView.setText("Ошибка");
            }
            frameLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
