package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class CurrentLocationModel {
    @SerializedName("status")
    private String responseStatus;
    @SerializedName("lat")
    private Double currentLatitude;
    @SerializedName("lon")
    private Double currentLongitude;
    @SerializedName("country")
    private String countryName;
    @SerializedName("regionName")
    private String regionName;
    @SerializedName("city")
    private String cityName;

    public CurrentLocationModel(String responseStatus, Double currentLatitude, Double currentLongitude, String countryName, String regionName, String cityName) {
        this.responseStatus = responseStatus;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.countryName = countryName;
        this.regionName = regionName;
        this.cityName = cityName;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCityName() {
        return cityName;
    }
}
