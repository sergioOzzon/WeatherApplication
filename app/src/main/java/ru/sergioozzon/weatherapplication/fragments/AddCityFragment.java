package ru.sergioozzon.weatherapplication.fragments;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import ru.sergioozzon.weatherapplication.MainActivity;
import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.JsonDataLoader;

public class AddCityFragment extends Fragment {

    private SharedPreferences preferences;
    private SQLiteDatabase database;

    public AddCityFragment() {
    }

    AddCityFragment(SharedPreferences preferences, SQLiteDatabase database) {
        this.preferences = preferences;
        this.database = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextInputEditText inputEditText = view.findViewById(R.id.nameNewCity);
        final Button okButton = view.findViewById(R.id.confirmCityNameButton);
        final SharedPreferences.Editor editor = preferences.edit();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEditText.getText() != null) {
                    final String cityName = String.valueOf(inputEditText.getText());
                    City city = new City(cityName);
                    JsonDataLoader loader = new JsonDataLoader();
                    loader.execute(database);
                    editor.putString(MainActivity.PREFERENCE_CURRENT_CITY, cityName);
                    editor.apply();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, WeatherFragment.newInstance(city, database), MainActivity.WEATHER_FRAGMENT)
                            .commit();
                }
            }
        });

    }
}
