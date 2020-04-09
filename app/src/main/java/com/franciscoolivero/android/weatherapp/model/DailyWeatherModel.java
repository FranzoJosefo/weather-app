package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class DailyWeatherModel extends WeatherModel {
    @SerializedName("temp")
    private DailyTempModel dailyTempModel;

    public DailyWeatherModel(int currentTime, float temperature, float realFeel, List<BasicWeatherModel> weatherList, DailyTempModel dailyTempModel) {
        super(currentTime, temperature, realFeel, weatherList);
        this.dailyTempModel = dailyTempModel;
    }

    public DailyTempModel getDailyTempModel() {
        return dailyTempModel;
    }
}
