package com.franciscoolivero.android.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class DailyTempModel {
        @SerializedName("min")
        private double min;
        @SerializedName("max")
        private double max;

        public DailyTempModel(float min, float max) {
                this.min = min;
                this.max = max;
        }

        public double getMin() {
                return min;
        }

        public double getMax() {
                return max;
        }
}
