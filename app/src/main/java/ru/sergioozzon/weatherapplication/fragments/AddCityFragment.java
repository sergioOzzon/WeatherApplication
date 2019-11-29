package ru.sergioozzon.weatherapplication.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.JsonDataLoader;

public class AddCityFragment extends Fragment {

    TextInputEditText inputEditText;

    public AddCityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputEditText = view.findViewById(R.id.nameNewCity);
        String cityName;
        if (inputEditText.getText() != null){
            cityName = String.valueOf(inputEditText.getText());
            new City(cityName);
            JsonDataLoader loader = new JsonDataLoader();
            loader.execute();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        }


    }
}
