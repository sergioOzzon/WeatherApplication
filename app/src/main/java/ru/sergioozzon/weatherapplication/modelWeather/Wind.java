package ru.sergioozzon.weatherapplication.modelWeather;

import java.io.Serializable;

public class Wind implements Serializable {
    private float speed;
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
