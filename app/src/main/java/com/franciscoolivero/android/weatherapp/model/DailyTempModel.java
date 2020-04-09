package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class DailyTempModel {
        @SerializedName("min")
        private Double min;
        @SerializedName("max")
        private Double max;
}
