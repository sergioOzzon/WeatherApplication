package ru.sergioozzon.weatherapplication.ui;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import ru.sergioozzon.weatherapplication.modelWeather.CityManager;
import ru.sergioozzon.weatherapplication.R;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.DatabaseHelper;
import ru.sergioozzon.weatherapplication.modelWeather.JsonDataLoader;
import ru.sergioozzon.weatherapplication.ui.fragments.AboutAsFragment;
import ru.sergioozzon.weatherapplication.ui.fragments.LocationsFragment;
import ru.sergioozzon.weatherapplication.ui.fragments.SettingsFragment;
import ru.sergioozzon.weatherapplication.ui.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    City city;
    public SQLiteDatabase database;
    SharedPreferences preferences;
    public static final String APP_PREFERENCES = "mySettings";
    public final static String SETTING_FRAGMENT = "setting";
    public final static String WEATHER_FRAGMENT = "weather";
    public final static String LOCATION_FRAGMENT = "location";
    public final static String ABOUT_AS_FRAGMENT = "about as";
    public final static String ADD_CITY_FRAGMENT = "add city";

    private Toolbar toolbar;
    private DrawerLayout drawer;
    FloatingActionButton fab;
    NavigationView navigationView;
    public View.OnClickListener clickListenerOnFabForUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            city = CityManager.getCurrentCity();
            if (city == null) {
                city = CityManager.getDefaultCity();
            }
            JsonDataLoader loader = new JsonDataLoader();
            loader.execute();
            if (isResumed(WEATHER_FRAGMENT))
                loadFragment(WeatherFragment.newInstance(city), WEATHER_FRAGMENT);
            Toast.makeText(getApplicationContext(), (R.string.has_been_updated), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPreferences();
        initDB();
        initCityManager();
        initViews();
        initToolbar();
        initFloatingActionButton();
        initSideMenu();
        initCityList();
        loadWeatherData();
        loadFragment(WeatherFragment.newInstance(city), WEATHER_FRAGMENT);
    }

    private void initPreferences() {
        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
    }

    private void initDB() {
        database = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
    }

    private void initCityManager() {
        new CityManager(database, preferences);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
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

    private void initCityList() {
        if (City.getCities().size()== 0) {
            CityManager.loadAllCities();
            Log.d("MAIN_CITY_LIST_SIZE", " " + City.getCities().size());
        }
        city = CityManager.getSavedCity();
        if (city == null) {
            //TODO: GET GEO
            //TODO: if GEO not avalible, then:
            Toast.makeText(getApplicationContext(), R.string.could_not_get_coordinates, Toast.LENGTH_SHORT).show();
            city = CityManager.getDefaultCity();
            CityManager.addCity(city);
        }
    }

    private void loadWeatherData() {
        JsonDataLoader loader = new JsonDataLoader();
        loader.execute();
    }

    private void loadFragment(Fragment fragment, String TAG) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, TAG);
        ft.addToBackStack("");
        ft.commit();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        city = CityManager.getCurrentCity();
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
            if (!isResumed(SETTING_FRAGMENT))
                loadFragment(new SettingsFragment(), SETTING_FRAGMENT);
            return true;
        } else if (id == R.id.action_about_as) {
            if (!isResumed(ABOUT_AS_FRAGMENT))
                loadFragment(new AboutAsFragment(), ABOUT_AS_FRAGMENT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_setting) {
            if (!isResumed(SETTING_FRAGMENT))
                loadFragment(new SettingsFragment(), SETTING_FRAGMENT);
        } else if (id == R.id.nav_location) {
            if (!isResumed(LOCATION_FRAGMENT))
                loadFragment(new LocationsFragment(), LOCATION_FRAGMENT);
        } else if (id == R.id.nav_about_us) {
            if (!isResumed(ABOUT_AS_FRAGMENT))
                loadFragment(new AboutAsFragment(), ABOUT_AS_FRAGMENT);
        } else if (id == R.id.nav_home) {
            if (!isResumed(WEATHER_FRAGMENT))
                loadFragment(WeatherFragment.newInstance(city), WEATHER_FRAGMENT);
        } else if (id == R.id.nav_share) {
            //TODO
            Snackbar.make(drawer, "Coming soon", Snackbar.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            //TODO
            Snackbar.make(drawer, "Coming soon", Snackbar.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isResumed(String TAG) {
        try {
            boolean isResumed = Objects.requireNonNull(getSupportFragmentManager()
                    .findFragmentByTag(TAG))
                    .getLifecycle()
                    .getCurrentState() == Lifecycle.State.RESUMED;
            Log.d("FRAGMENT_IS_RESUMED", TAG + " " + isResumed);
            return isResumed;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

    }
}
