package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class CityLocationModel {
    @SerializedName("lat")
    private Double cityLatitude;
    @SerializedName("lon")
    private Double cityLongitude;
    @SerializedName("display_name")
    private String displayName;

    public CityLocationModel(Double cityLatitude, Double cityLongitude, String displayName) {
        this.cityLatitude = cityLatitude;
        this.cityLongitude = cityLongitude;
        this.displayName = displayName;
    }

    public Double getCityLatitude() {
        return cityLatitude;
    }

    public Double getCityLongitude() {
        return cityLongitude;
    }

    public String getDisplayName() {
        return displayName;
    }
}
