package com.franciscoolivero.android.weatherapp.data;

import com.franciscoolivero.android.weatherapp.model.BaseWeatherResponseModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapApiService {

    String OPEN_WEATHER_API_KEY = "35dbcff8d1b2076d2a9596e7e4cd5b1f";

    @GET("data/2.5/onecall?lat={lat}&lon={lon}&appid=" + OPEN_WEATHER_API_KEY)
    Single<BaseWeatherResponseModel> getWeatherData(@Query(value = "lat", encoded = true) String lat, @Query(value = "lon", encoded = true) String lon);

}
