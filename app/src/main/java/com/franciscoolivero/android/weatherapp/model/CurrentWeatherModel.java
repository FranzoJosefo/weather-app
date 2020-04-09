package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeatherModel extends WeatherModel {
    @SerializedName("sunrise")
    private int sunriseTime;
    @SerializedName("sunset")
    private int sunsetTime;

    public CurrentWeatherModel(int currentTime, float temperature, float realFeel, List<BasicWeatherModel> weatherList, int sunriseTime, int sunsetTime) {
        super(currentTime, temperature, realFeel, weatherList);
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
    }

    public int getSunriseTime() {
        return sunriseTime;
    }

    public int getSunsetTime() {
        return sunsetTime;
    }
}
