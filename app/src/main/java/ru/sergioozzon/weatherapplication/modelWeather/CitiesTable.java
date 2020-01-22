package ru.sergioozzon.weatherapplication.modelWeather;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class CitiesTable {

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

    static void addCity(City city, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY, city.getCityName());
        database.insert(TABLE_NAME, null, values);
    }

    static void editCity(String weatherData, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CITY_WEATHER, weatherData);
        database.replace(TABLE_NAME, null, values);

        /*database.execSQL("UPDATE " + TABLE_NAME + " set " + COLUMN_CITY + " = " + newNote + " WHERE "
                + COLUMN_CITY + " = " + noteToEdit + ";");*/
        //UPDATE Notes set note = 10 WHERE note = 5
    }

    static void deleteCity(SQLiteDatabase database, City city) {
        int deletedCount = database.delete(TABLE_NAME, COLUMN_CITY + "=" + city.getCityName(), null);
        Log.d("CitiesTable", deletedCount + "rows was deleted");
    }

    static void deleteAll(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
        //DELETE * FROM Cities
    }

    static void loadAllCities(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        loadResultFromCursor(cursor);
    }

    private static void loadResultFromCursor(Cursor cursor) {
        if(cursor != null && cursor.moveToFirst()) {//попали на первую запись, плюс вернулось true, если запись есть
            do {
                String cityName = cursor.getString(1);
                new City(cityName);
            } while (cursor.moveToNext());
        }
        try {
            assert cursor != null;
            cursor.close(); } catch (Exception ignored) {}
    }
}
