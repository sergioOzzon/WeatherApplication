package ru.sergioozzon.weatherapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.example.weatherapplication.R;
import ru.sergioozzon.weatherapplication.fragments.AboutAsFragment;
import ru.sergioozzon.weatherapplication.fragments.LocationsFragment;
import ru.sergioozzon.weatherapplication.fragments.SettingsFragment;
import ru.sergioozzon.weatherapplication.fragments.WeatherFragment;
import ru.sergioozzon.weatherapplication.modelWeather.City;
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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    City city;

    final static String SETTING_FRAGMENT = "setting";
    final static String WEATHER_FRAGMENT = "weather";
    final static String LOCATION_FRAGMENT = "location";
    final static String ABOUT_AS_FRAGMENT = "about as";
    private static final String CURRENT_CITY = "current city";
    public static String currentFragment = WEATHER_FRAGMENT;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    FloatingActionButton fab;
    Drawable homeDrawable;
    Drawable updateDrawable;
    NavigationView navigationView;
    View.OnClickListener clickListenerOnFabForUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            JsonDataLoader loader = new JsonDataLoader();
            loader.execute();
            city = City.getCurrentCity();
            loadFragment(currentFragment);
            Toast.makeText(getApplicationContext(), (R.string.has_been_updated), Toast.LENGTH_SHORT).show();
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
        getCityListFromDB();
        loadFragment(currentFragment);
        if (savedInstanceState == null) {
            JsonDataLoader loader = new JsonDataLoader();
            loader.execute();
        }
    }

    private void getCityListFromDB() {
        if (City.getCityArrayList().size() == 0) {
            city = new City("Surgut");
            City.setCurrentCity(city);
            new City("Moscow");
            new City("Samara");
        } else {
            city = City.getCurrentCity();
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        homeDrawable = getResources().getDrawable(R.drawable.home);
        updateDrawable = getResources().getDrawable(R.drawable.reload);
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

    private void loadFragment(String currentFragment){
        Fragment fragment;
        switch (currentFragment) {
            case ABOUT_AS_FRAGMENT:
                fragment = new AboutAsFragment();
                break;
            case LOCATION_FRAGMENT:
                fragment = new LocationsFragment();
                break;
            case SETTING_FRAGMENT:
                fragment = new SettingsFragment();
                break;
            default:
                fragment = WeatherFragment.newInstance(city);
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack("");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
            if (!currentFragment.equals(SETTING_FRAGMENT))
                loadFragment(SETTING_FRAGMENT);
            currentFragment = SETTING_FRAGMENT;
            return true;
        } else if (id == R.id.action_about_as) {
            if (!currentFragment.equals(ABOUT_AS_FRAGMENT))
                loadFragment(ABOUT_AS_FRAGMENT);
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
                loadFragment(SETTING_FRAGMENT);
            currentFragment = SETTING_FRAGMENT;
        } else if (id == R.id.nav_location){
            if(!currentFragment.equals(LOCATION_FRAGMENT))
                loadFragment(LOCATION_FRAGMENT);
            currentFragment = LOCATION_FRAGMENT;
        } else if (id == R.id.nav_about_us) {
            if (!currentFragment.equals(ABOUT_AS_FRAGMENT))
                loadFragment(ABOUT_AS_FRAGMENT);
            currentFragment = ABOUT_AS_FRAGMENT;
        } else if (id == R.id.nav_home) {
            if (!currentFragment.equals(WEATHER_FRAGMENT))
                loadFragment(WEATHER_FRAGMENT);
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
