package ru.sergioozzon.weatherapplication.modelWeather.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sys implements Serializable {
    @SerializedName("type") private int type;
    @SerializedName("id") private int id;
    @SerializedName("message") private float message;
    @SerializedName("country") private String country;
    @SerializedName("sunrise") private long sunrise;
    @SerializedName("sunset") private long sunset;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }
}
