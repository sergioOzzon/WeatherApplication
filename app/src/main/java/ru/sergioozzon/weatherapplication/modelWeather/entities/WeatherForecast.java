package ru.sergioozzon.weatherapplication.modelWeather.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WeatherForecast implements Serializable {

    @SerializedName("dt") private long dt;
    @SerializedName("main") private Main main;
    @SerializedName("weather") private Weather[] weather;
    @SerializedName("clouds") private Clouds clouds;
    @SerializedName("wind") private Wind wind;
    @SerializedName("snow") private Snow snow;
    @SerializedName("sys") private Sys sys;
    @SerializedName("dt_txt") private String dt_txt;
    private Calendar calendar;

    public long getDt() {
        return this.dt;
    }
    public Main getMain(){
        return this.main;
    }
    public Weather[] getWeather(){
        return this.weather;
    }
    public Clouds getClouds(){
        return this.clouds;
    }
    public Wind getWind(){
        return this.wind;
    }
    public Snow getSnow(){
        return this.snow;
    }
    public Sys getSys(){
        return this.sys;
    }
    public String getDt_txt(){
        return this.dt_txt;
    }
    public String getTime(){
        String time = dt_txt.substring(11, 16);
        return time;
    }
    public void setUpdateDate() {
        this.calendar = new GregorianCalendar();
    }
    public Calendar getUpdateDate() {
        return calendar;
    }
}
