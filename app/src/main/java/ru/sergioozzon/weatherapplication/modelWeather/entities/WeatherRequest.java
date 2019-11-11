package ru.sergioozzon.weatherapplication.modelWeather.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Calendar;

public class WeatherRequest implements Serializable {
    @SerializedName("coord") private Coord coordinates;
    @SerializedName("weather") private Weather[] weather;
    @SerializedName("base") private String base;
    @SerializedName("main") private Main main;
    @SerializedName("visibility") private int visibility;
    @SerializedName("wind") private Wind wind;
    @SerializedName("clouds") private Clouds clouds;
    @SerializedName("dt") private long dt;
    @SerializedName("sys") private Sys sys;
    @SerializedName("id") private  long id;
    @SerializedName("timezone") private int timezone;
    @SerializedName("name") private String name;
    @SerializedName("cod") private int cod;
    private Calendar calendar;

    public Coord getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coord coordinates) {
        this.coordinates = coordinates;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
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

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public Calendar getUpdateDate() {
        return calendar;
    }
    public void setUpdateDate(Calendar calendar) {
        this.calendar = calendar;
    }
}
