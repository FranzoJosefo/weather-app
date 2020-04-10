package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyWeatherModel extends WeatherModel {
    @SerializedName("temp")
    private DailyTempModel dailyTempModel;

    public DailyWeatherModel(long currentTime, List<BasicWeatherModel> basicWeatherModelList, int pressure, int humidity, float windSpeed, DailyTempModel dailyTempModel) {
        super(currentTime, basicWeatherModelList, pressure, humidity, windSpeed);
        this.dailyTempModel = dailyTempModel;
    }

    public DailyTempModel getDailyTempModel() {
        return dailyTempModel;
    }
}
