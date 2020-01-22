package ru.sergioozzon.weatherapplication.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.CityManager;
import ru.sergioozzon.weatherapplication.modelWeather.JsonDataLoader;
import ru.sergioozzon.weatherapplication.ui.MainActivity;

public class AddCityFragment extends Fragment {

    private TextInputEditText inputEditText;
    private Button okButton;
    private InputMethodManager inputMethodManager;
    private View.OnClickListener okBtmClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (inputEditText.getText() != null) {
                final String cityName = String.valueOf(inputEditText.getText());
                City city = new City(cityName);
                CityManager.addCity(city);
                JsonDataLoader loader = new JsonDataLoader();
                loader.execute();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, WeatherFragment.newInstance(city), MainActivity.WEATHER_FRAGMENT)
                        .commit();
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    };


    public AddCityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);
        inputEditText = view.findViewById(R.id.nameNewCity);
        okButton = view.findViewById(R.id.confirmCityNameButton);
        inputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                okBtmClickListener.onClick(v);
                return false;
            }
        });
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
