package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {
    @SerializedName("dt")
    private int currentTime;
    @SerializedName("temp")
    private int temperature;
    @SerializedName("feels_like")
    private int realFeel;
    @SerializedName("weather")
    private List<BasicWeatherModel> basicWeatherModelList;

    public WeatherModel(int currentTime, int temperature, int realFeel, List<BasicWeatherModel> weatherList) {
        this.currentTime = currentTime;
        this.temperature = temperature;
        this.realFeel = realFeel;
        this.basicWeatherModelList = weatherList;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getRealFeel() {
        return realFeel;
    }

    public List<BasicWeatherModel> getBasicWeatherModelList() {
        return basicWeatherModelList;
    }
}
