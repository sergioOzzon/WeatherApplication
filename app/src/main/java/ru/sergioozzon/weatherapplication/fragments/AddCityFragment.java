package ru.sergioozzon.weatherapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import ru.sergioozzon.weatherapplication.MainActivity;
import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.CitiesTable;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.JsonDataLoader;

public class AddCityFragment extends Fragment {

    private SharedPreferences preferences;
    private SQLiteDatabase database;

    private TextInputEditText inputEditText;
    private Button okButton;
    InputMethodManager inputMethodManager;
    View.OnClickListener okBtmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (inputEditText.getText() != null) {
                final SharedPreferences.Editor editor = preferences.edit();
                final String cityName = String.valueOf(inputEditText.getText());
                City city = new City(cityName);
                JsonDataLoader loader = new JsonDataLoader();
                loader.execute(database);
                CitiesTable.addCity(cityName, database);
                editor.putString(MainActivity.PREFERENCE_CURRENT_CITY, cityName);
                editor.apply();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, WeatherFragment.newInstance(city, database), MainActivity.WEATHER_FRAGMENT)
                        .commit();
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    };

    public AddCityFragment() {
    }

    AddCityFragment(SharedPreferences preferences, SQLiteDatabase database) {
        this.preferences = preferences;
        this.database = database;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);
        inputEditText = view.findViewById(R.id.nameNewCity);
        okButton = view.findViewById(R.id.confirmCityNameButton);
        inputEditText.requestFocus();
        if (getActivity() != null) {
            inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        okButton.setOnClickListener(okBtmClickListener);
    }
}
