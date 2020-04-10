package com.franciscoolivero.android.weatherapp.utils;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String getFormattedDateFromEpoch(long epochTimeStamp, String pattern) {
        String formattedDate;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(epochTimeStamp, 0, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
            formattedDate = localDateTime.format(formatter);
        } else {
            long timestamp = epochTimeStamp * 1000;
            Date date = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            formattedDate = sdf.format(date);
        }
        return formattedDate;
    }
}
