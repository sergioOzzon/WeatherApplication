package ru.sergioozzon.weatherapplication.modelWeather;

import java.io.Serializable;

public class Main implements Serializable {
    private float temp;
    private float pressure;
    private int humidity;
    private float temp_min;
    private float temp_max;

    public float getTemp() {
        return temp - 273;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public float getTemp_min() {
        return temp_min - 273;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max - 273;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }
}
