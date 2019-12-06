package ru.sergioozzon.weatherapplication.modelWeather.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

class Clouds implements Serializable {
    @SerializedName("all") private int all;
}
