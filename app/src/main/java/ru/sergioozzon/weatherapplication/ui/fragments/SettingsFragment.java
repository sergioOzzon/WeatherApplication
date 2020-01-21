package ru.sergioozzon.weatherapplication.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import ru.sergioozzon.weatherapplication.R;

public class SettingsFragment extends PreferenceFragmentCompat{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
        if (preferences.contains("sensorsIsEnable")){
            WeatherFragment.isSensorsEnable = preferences.getBoolean("sensorsIsEnable", false);
        }

    }
}