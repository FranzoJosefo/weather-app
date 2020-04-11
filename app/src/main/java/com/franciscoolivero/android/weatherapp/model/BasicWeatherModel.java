package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class BasicWeatherModel {
    @SerializedName("id")
    private int weatherId;
    @SerializedName("main")
    private String weatherCondition;
    @SerializedName("description")
    private String weatherDescription;
    @SerializedName("icon")
    private String weatherIconCode;

    public BasicWeatherModel(int weatherId, String weatherCondition, String weatherDescription, String weatherIconCode) {
        this.weatherId = weatherId;
        this.weatherCondition = weatherCondition;
        this.weatherDescription = weatherDescription;
        this.weatherIconCode = weatherIconCode;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherDescriptionCapitalized() {
        String weatherDescription = getWeatherDescription();
        return weatherDescription.substring(0, 1).toUpperCase() + weatherDescription.substring(1).toLowerCase();
    }

    public String getWeatherIconCode() {
        return weatherIconCode;
    }
}
