package ru.sergioozzon.weatherapplication;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import ru.sergioozzon.weatherapplication.fragments.AboutAsFragment;
import ru.sergioozzon.weatherapplication.fragments.LocationsFragment;
import ru.sergioozzon.weatherapplication.fragments.SettingsFragment;
import ru.sergioozzon.weatherapplication.fragments.WeatherFragment;
import ru.sergioozzon.weatherapplication.modelWeather.CitiesTable;
import ru.sergioozzon.weatherapplication.modelWeather.City;
import ru.sergioozzon.weatherapplication.modelWeather.DatabaseHelper;
import ru.sergioozzon.weatherapplication.modelWeather.JsonDataLoader;

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
import androidx.lifecycle.Lifecycle;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DEFAULT_CITY = "Moscow";
    City city;
    public static SQLiteDatabase database;
    SharedPreferences preferences;
    public static final String APP_PREFERENCES = "mySettings";
    public static final String PREFERENCE_CURRENT_CITY = "cityName";
    public final static String SETTING_FRAGMENT = "setting";
    public final static String WEATHER_FRAGMENT = "weather";
    public final static String LOCATION_FRAGMENT = "location";
    public final static String ABOUT_AS_FRAGMENT = "about as";
    public final static String ADD_CITY_FRAGMENT = "add city";
    private static final String CURRENT_CITY = "current city";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    FloatingActionButton fab;
    NavigationView navigationView;
    public View.OnClickListener clickListenerOnFabForUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            city = City.getCurrentCity();
            if (city == null){
                city = City.getCities().get(DEFAULT_CITY);
            }
            JsonDataLoader loader = new JsonDataLoader();
            loader.execute(database);
            if (isResumed(WEATHER_FRAGMENT))
                loadFragment(WeatherFragment.newInstance(city, database), WEATHER_FRAGMENT);
            Toast.makeText(getApplicationContext(), (R.string.has_been_updated), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        initDB();
        initViews();
        initToolbar();
        initFloatingActionButton();
        initSideMenu();
        initCityList();
        loadWeatherData();
        loadFragment(WeatherFragment.newInstance(city, database), WEATHER_FRAGMENT);
    }

    private void initCityList() {
        int CITIES_SIZE = City.getCities().size();
        if (CITIES_SIZE == 0) {
            CitiesTable.loadAllCities(database);
            Log.d("MAIN_CITY_LIST_SIZE", " " + City.getCities().size());
        }
        if (preferences.contains(PREFERENCE_CURRENT_CITY)) {
            Log.d("PREFERENCES", "preferences.contains(APP_PREFERENCES) = true");
            city = City.getCities().get(preferences.getString(PREFERENCE_CURRENT_CITY, DEFAULT_CITY));
        } else city = City.getCurrentCity();
        if (city == null) {
            //TODO: GET GEO
            //TODO: if GEO not avalible, then:
            Toast.makeText(getApplicationContext(), R.string.could_not_get_coordinates, Toast.LENGTH_SHORT).show();
            city = new City(DEFAULT_CITY, database);
            City.setCurrentCity(city, preferences);
        }
    }

    private void loadWeatherData() {
        JsonDataLoader loader = new JsonDataLoader();
        loader.execute(database);
    }

    private void initDB() {
        database = new DatabaseHelper(getApplicationContext()).getWritableDatabase();
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

    private void loadFragment(Fragment fragment, String TAG) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment, TAG);
        ft.addToBackStack("");
        ft.commit();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        city = (City) savedInstanceState.getSerializable(CURRENT_CITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle args = new Bundle();
        args.putSerializable(CURRENT_CITY, city);
        onSaveInstanceState(args);
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
                loadFragment(new LocationsFragment(preferences, database), LOCATION_FRAGMENT);
        } else if (id == R.id.nav_about_us) {
            if (!isResumed(ABOUT_AS_FRAGMENT))
                loadFragment(new AboutAsFragment(), ABOUT_AS_FRAGMENT);
        } else if (id == R.id.nav_home) {
            if (!isResumed(WEATHER_FRAGMENT))
                loadFragment(WeatherFragment.newInstance(city, database), WEATHER_FRAGMENT);
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
