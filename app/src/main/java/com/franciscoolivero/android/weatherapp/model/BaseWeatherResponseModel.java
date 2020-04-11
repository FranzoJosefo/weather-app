package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseWeatherResponseModel {
    @SerializedName("lat")
    private double latitude;
    @SerializedName("lon")
    private double longitude;
    @SerializedName("current")
    private CurrentWeatherModel currentWeatherModel;
    @SerializedName("hourly")
    private List<HourlyWeatherModel> hourlyWeatherModelList;
    @SerializedName("daily")
    private List<DailyWeatherModel> dailyWeatherModelList;

    public BaseWeatherResponseModel(float latitude, float longitude, CurrentWeatherModel currentWeatherModel, List<HourlyWeatherModel> hourlyWeatherModelList, List<DailyWeatherModel> dailyWeatherModelList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.currentWeatherModel = currentWeatherModel;
        this.hourlyWeatherModelList = hourlyWeatherModelList;
        this.dailyWeatherModelList = dailyWeatherModelList;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public CurrentWeatherModel getCurrentWeatherModel() {
        return currentWeatherModel;
    }

    public List<HourlyWeatherModel> getHourlyWeatherModelList() {
        return hourlyWeatherModelList;
    }

    public List<DailyWeatherModel> getDailyWeatherModelList() {
        return dailyWeatherModelList;
    }
}
