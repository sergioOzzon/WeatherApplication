package ru.sergioozzon.weatherapplication.modelWeather;

import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import ru.sergioozzon.weatherapplication.CitiesTable;

public class DataLoader {
    private City city;
    private SQLiteDatabase database;
    private JsonDataLoader jsonDataLoader;

    public DataLoader(City city, SQLiteDatabase database){
        this.city = city;
        this.database = database;
        jsonDataLoader = new JsonDataLoader();
    }

    public void execute() {
        ArrayList<City> cityList = new ArrayList<>(CitiesTable.getAllCities(database));
        if (cityList.size() != 0) City.setCityArrayList(cityList);
    }
}
