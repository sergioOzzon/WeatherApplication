package com.example.weatherapplication.modelWeather;

public class Main {
    private float temp;
    private int pressure;
    private int humidity;

    public float getTemp() {
        return temp - 273;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getPressure() {
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
}
