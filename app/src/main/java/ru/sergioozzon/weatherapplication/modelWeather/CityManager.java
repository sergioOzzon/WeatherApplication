package ru.sergioozzon.weatherapplication.modelWeather;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.sergioozzon.weatherapplication.ui.MainActivity;

public class CityManager {

    private static final String DEFAULT_CITY = "Moscow";
    private static final String PREFERENCE_CURRENT_CITY = "cityName";
    private static City currentCity = new City(DEFAULT_CITY);
    private static SharedPreferences preferences;
    private static SQLiteDatabase database;

    public CityManager(SQLiteDatabase database, SharedPreferences preferences) {
        CityManager.database = database;
        CityManager.preferences = preferences;
    }

    public static void deleteCity(City city) {
        CitiesTable.deleteCity(database, city);
        City.getCityArrayList().remove(city);
        City.getCities().remove(city.getCityName());
    }

    public static City getDefaultCity() {
        return new City(DEFAULT_CITY);
    }

    public static void addCity(City city) {
        CitiesTable.addCity(city.getCityName(), database);
        setCurrentCity(city);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCE_CURRENT_CITY, city.getCityName());
        editor.apply();
    }

    public static City getCurrentCity() {
        return currentCity;
    }

    public static void setCurrentCity(City currentCity) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCE_CURRENT_CITY, currentCity.getCityName());
        editor.apply();
        CityManager.currentCity = currentCity;
    }


    public static City getSavedCity() {
        if (preferences.contains(PREFERENCE_CURRENT_CITY)) {
            Log.d("PREFERENCES", "preferences.contains(APP_PREFERENCES) = true");
            return City.getCities().get(preferences.getString(PREFERENCE_CURRENT_CITY, CityManager.getDefaultCity().getCityName()));
        } else return getCurrentCity();
    }

    public static void loadAllCities() {
        CitiesTable.loadAllCities(database);
    }
}
