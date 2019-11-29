package ru.sergioozzon.weatherapplication.modelWeather.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Main implements Serializable {
    @SerializedName("temp") private float temp;
    @SerializedName("pressure") private float pressure;
    @SerializedName("humidity") private float humidity;
    @SerializedName("temp_min") private float tempMin;
    @SerializedName("temp_max") private float tempMax;

    public float getTemp() {
        return temp;
    }

    public float getPressure() {
        final float HgPressure = pressure * 0.7501f;
        return HgPressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
