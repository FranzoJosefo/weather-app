package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {
    @SerializedName("dt")
    private int currentTime;
    @SerializedName("temp")
    private float temperature;
    @SerializedName("feels_like")
    private float realFeel;
    @SerializedName("weather")
    private List<BasicWeatherModel> basicWeatherModelList;

    public WeatherModel(int currentTime, float temperature, float realFeel, List<BasicWeatherModel> weatherList) {
        this.currentTime = currentTime;
        this.temperature = temperature;
        this.realFeel = realFeel;
        this.basicWeatherModelList = weatherList;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getRealFeel() {
        return realFeel;
    }

    public List<BasicWeatherModel> getBasicWeatherModelList() {
        return basicWeatherModelList;
    }
}
