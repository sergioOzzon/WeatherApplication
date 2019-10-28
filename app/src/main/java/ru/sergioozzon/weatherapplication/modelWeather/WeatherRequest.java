package ru.sergioozzon.weatherapplication.modelWeather;

import java.io.Serializable;
import java.util.Calendar;

public class WeatherRequest implements Serializable {
    private Coord coord;
    private Weather[] weather;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private String name;
    private Calendar calendar;

    public Coord getCoord() {
        return coord;
    }
    public void setCoord(Coord coord) {
        this.coord = coord;
    }
    public Weather[] getWeather() {
        return weather;
    }
    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
    public Main getMain() {
        return main;
    }
    public void setMain(Main main) {
        this.main = main;
    }
    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;
    }
    public Clouds getClouds() {
        return clouds;
    }
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Calendar getUpdateDate() {
        return calendar;
    }
    public void setUpdateDate(Calendar calendar) {
        this.calendar = calendar;
    }
}
