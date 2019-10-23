package ru.sergioozzon.weatherapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.weatherapplication.R;

import ru.sergioozzon.weatherapplication.fragments.AboutAsFragment;
import ru.sergioozzon.weatherapplication.fragments.LocationsFragment;
import ru.sergioozzon.weatherapplication.fragments.SettingsFragment;
import ru.sergioozzon.weatherapplication.fragments.WeatherFragment;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.ConnectionToGetWeather;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    City city;
    public static String currentFragment = "";
    final static String SETTING_FRAGMENT = "setting";
    final static String WEATHER_FRAGMENT = "weather";
    final static String LOCATION_FRAGMENT = "location";
    final static String ABOUT_AS_FRAGMENT = "about as";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    FloatingActionButton fab;
    Drawable homeDrawable;
    Drawable updateDrawable;
    NavigationView navigationView;
    View.OnClickListener clickListenerOnFabForUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            updateWeather();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initToolbar();
        initFloatingActionButton();
        initSideMenu();
        //TODO: удалить инициализацию городов из кода
        if (City.getCityArrayList().size() == 0) {
            city = new City("Surgut");
            new City("Moscow");
        } else {
            city = City.getCurrentCity();
        }
        updateWeather();
        loadFragment(WeatherFragment.newInstance(city));
        currentFragment = WEATHER_FRAGMENT;
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        homeDrawable = getDrawable(R.drawable.home);
        updateDrawable = getDrawable(R.drawable.reload);
        navigationView = findViewById(R.id.nav_view);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initFloatingActionButton() {
        fab.setOnClickListener(clickListenerOnFabForUpdate);
    }

    private void initSideMenu() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void updateWeather() {
        ConnectionToGetWeather connection = new ConnectionToGetWeather();
        connection.execute();
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
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
        } else if (id == R.id.action_about_as) {
            if (!currentFragment.equals(ABOUT_AS_FRAGMENT))
                loadFragment(new AboutAsFragment());
            currentFragment = ABOUT_AS_FRAGMENT;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_setting) {
            if (!currentFragment.equals(SETTING_FRAGMENT))
                loadFragment(new SettingsFragment());
            currentFragment = SETTING_FRAGMENT;
        } else if (id == R.id.nav_location){
            if(!currentFragment.equals(LOCATION_FRAGMENT))
                loadFragment(new LocationsFragment());
            currentFragment = LOCATION_FRAGMENT;
        } else if (id == R.id.nav_about_us) {
            if (!currentFragment.equals(ABOUT_AS_FRAGMENT))
                loadFragment(new AboutAsFragment());
            currentFragment = ABOUT_AS_FRAGMENT;
        } else if (id == R.id.nav_home) {
            if (!currentFragment.equals(WEATHER_FRAGMENT))
                loadFragment(WeatherFragment.newInstance(city));
            currentFragment = WEATHER_FRAGMENT;
        } else if (id == R.id.nav_share){
            //TODO
            Snackbar.make(drawer, "Coming soon", Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send){
            //TODO
            Snackbar.make(drawer, "Coming soon", Snackbar.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
