package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherModel {
    @SerializedName("dt")
    private long currentTime;
    @SerializedName("weather")
    private List<BasicWeatherModel> basicWeatherModelList;
    @SerializedName("pressure")
    private int pressure;
    @SerializedName("humidity")
    private int humidity;
    @SerializedName("wind_speed")
    private double windSpeed;

    public WeatherModel(long currentTime, List<BasicWeatherModel> basicWeatherModelList, int pressure, int humidity, double windSpeed) {
        this.currentTime = currentTime;
        this.basicWeatherModelList = basicWeatherModelList;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public List<BasicWeatherModel> getBasicWeatherModelList() {
        return basicWeatherModelList;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }
}
