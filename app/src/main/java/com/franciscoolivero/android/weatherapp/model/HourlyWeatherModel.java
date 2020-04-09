package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HourlyWeatherModel extends WeatherModel {
    @SerializedName("temp")
    private double temperature;
    @SerializedName("feels_like")
    private double realFeel;

    public HourlyWeatherModel(int currentTime, double realFeel, List<BasicWeatherModel> basicWeatherModelList, int pressure, int humidity, float windSpeed, float temperature) {
        super(currentTime, basicWeatherModelList, pressure, humidity, windSpeed);
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRealFeel() {
        return realFeel;
    }
}
