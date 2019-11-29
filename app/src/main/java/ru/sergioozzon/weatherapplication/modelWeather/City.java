package ru.sergioozzon.weatherapplication.modelWeather;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import ru.sergioozzon.weatherapplication.modelWeather.entities.WeatherRequest;

public class City implements Serializable {

    private String cityName;
    private WeatherRequest weatherRequest;
    private static ArrayList<City> cityArrayList = new ArrayList<>();
    private static City currentCity;
    private static final String FILE_NAME = "current_city_file.city";
    private static String path;

    public City(String cityName){
        this.cityName = cityName;
        weatherRequest = new WeatherRequest();
        weatherRequest.setName(cityName);
        cityArrayList.add(this);
    }

    public String getCityName() {
        return cityName;
    }

    public WeatherRequest getWeatherRequest() { return weatherRequest; }

    void putWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
        weatherRequest.setUpdateDate();
    }

    public static ArrayList<City> getCityArrayList() {
        return cityArrayList;
    }

    public static void setCityArrayList(ArrayList<City> cityArrayList) {
        City.cityArrayList = cityArrayList;
    }

    public static City getCurrentCity() {
        try {
            currentCity = readFromFile(path);
        } catch (Exception e){
            e.printStackTrace();
        }
        return currentCity;
    }

    public static void setCurrentCity(City currentCity, String filesDirPath) {
        City.currentCity = currentCity;
        path = filesDirPath + "/" + FILE_NAME;
        saveToFile(currentCity, path);
    }

    private static void saveToFile(City currentCity, String fileName) {
        File file;
        try {
            file = new File(fileName);

            FileOutputStream fileOutputStream;
            ObjectOutputStream objectOutputStream;

            if(!file.exists()) {
                file.createNewFile();
            }

            fileOutputStream  = new FileOutputStream(file, false);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(currentCity);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static City readFromFile(String fileName) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        City city = null;
        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);

            city = (City) objectInputStream.readObject();

            fileInputStream.close();
            objectInputStream.close();
        } catch(Exception exc) {
            exc.printStackTrace();
        }
        return city;
    }
}
