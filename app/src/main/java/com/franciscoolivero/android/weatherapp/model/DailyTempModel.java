package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class DailyTempModel {
        @SerializedName("min")
        private float min;
        @SerializedName("max")
        private float max;
}
