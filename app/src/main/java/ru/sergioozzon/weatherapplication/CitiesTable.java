package ru.sergioozzon.weatherapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import ru.sergioozzon.weatherapplication.modelWeather.City;

public class CitiesTable {

    private static final String TABLE_NAME = "Cities";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_CITY_WEATHER = "weatherData";

    static void createTable(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY + " TEXT);");
    }

    static void onUpgrade(SQLiteDatabase database) {
        database.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_CITY_WEATHER
                + " TEXT DEFAULT 'Default title'");
    }

    public static void addCity(String cityName, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, cityName);

        database.insert(TABLE_NAME, null, values);
    }

    public static void editCity(String weatherData, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_WEATHER, weatherData);
        database.replace(TABLE_NAME, null, values);

        /*database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_CITY + " = " + newNote + " WHERE "
                + COLUMN_CITY + " = " + noteToEdit + ";");*/
        //UPDATE Notes set note = 10 WHERE note = 5
    }

    public static void deleteCity(SQLiteDatabase database) {
        database.delete(TABLE_NAME, COLUMN_CITY, null);
    }

    public static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
        //DELETE * FROM Notes
    }

    public static List<City> getAllCities(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return getResultFromCursor(cursor);
    }

    private static List<City> getResultFromCursor(Cursor cursor) {
        ArrayList<City> result = null;
        City city;

        if(cursor != null && cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
            result = new ArrayList<>(cursor.getCount());

            String cityName = cursor.getColumnName(2);
            do {
                city = new City(cityName);
                result.add(city);
            } while (cursor.moveToNext());
        }

        try { cursor.close(); } catch (Exception ignored) {}
        return result == null ? new ArrayList<City>(0) : result;
    }
}
