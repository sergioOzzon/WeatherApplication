package com.example.weatherapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeatherFragment extends Fragment {

    private static final String ARG_CURRENT_CITY = "currentCity";

    private City city;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }



}
