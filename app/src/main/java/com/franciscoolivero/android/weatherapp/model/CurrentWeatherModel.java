package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherModel extends WeatherModel {
    @SerializedName("sunrise")
    private int sunriseTime;
    @SerializedName("sunset")
    private int sunsetTime;
    @SerializedName("temp")
    private double temperature;
    @SerializedName("feels_like")
    private double realFeel;

    public CurrentWeatherModel(int currentTime, double temperature, double realFeel, List<BasicWeatherModel> basicWeatherModelList, int pressure, int humidity, double windSpeed, int sunriseTime, int sunsetTime) {
        super(currentTime, basicWeatherModelList, pressure, humidity, windSpeed);
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.temperature = temperature;
    }

    public int getSunriseTime() {
        return sunriseTime;
    }

    public int getSunsetTime() {
        return sunsetTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRealFeel() {
        return realFeel;
    }
}
