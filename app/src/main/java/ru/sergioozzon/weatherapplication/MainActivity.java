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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        city = new City("Surgut");
        new City("Moscow");

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateWeather();
            }
        });
        updateWeather();
        loadFragment(WeatherFragment.newInstance(city));
    }

    private void updateWeather() {
        ConnectionToGetWeather connection = new ConnectionToGetWeather();
        connection.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }



}
