package ru.sergioozzon.weatherapplication;

import android.os.Bundle;

import com.example.weatherapplication.R;

import ru.sergioozzon.weatherapplication.modelWeather.ConnectionToGetWeather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    City city;
    static String currentFragment = "";
    final static String SETTING_FRAGMENT = "setting";
    final static String WEATHER_FRAGMENT = "weather";
    final static String LOCATION_FRAGMENT = "location";
    final static String ABOUT_AS_FRAGMENT = "about as";
    Toolbar toolbar;
    View.OnClickListener clickListenerOnFab = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            updateWeather();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFloatingActionButton();
        //TODO: удалить инициализацию городов из кода
        city = new City("Surgut");
        new City("Moscow");
        updateWeather();
        loadFragment(WeatherFragment.newInstance(city));
    }

    private void initFloatingActionButton() {
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(clickListenerOnFab);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void updateWeather() {
        ConnectionToGetWeather connection = new ConnectionToGetWeather();
        connection.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            if (!currentFragment.equals(SETTING_FRAGMENT))
                loadFragment(new SettingsFragment());
            currentFragment = SETTING_FRAGMENT;
            return true;
        } else if (id == R.id.action_location){
            if(!currentFragment.equals(LOCATION_FRAGMENT))
                loadFragment(new LocationsFragment());
            currentFragment = LOCATION_FRAGMENT;
            return true;
        } else if (id == R.id.action_about_as) {
            if (!currentFragment.equals(ABOUT_AS_FRAGMENT))
                loadFragment(new AboutAsFragment());
            currentFragment = ABOUT_AS_FRAGMENT;
            return true;
        } else if (id == R.id.action_main) {
            if (!currentFragment.equals(WEATHER_FRAGMENT))
                loadFragment(WeatherFragment.newInstance(city));
            currentFragment = WEATHER_FRAGMENT;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }



}
