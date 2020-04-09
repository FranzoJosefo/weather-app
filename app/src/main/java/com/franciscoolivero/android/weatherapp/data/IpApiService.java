package com.franciscoolivero.android.weatherapp.data;

import com.franciscoolivero.android.weatherapp.model.LocationModel;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IpApiService {

    @GET("json")
    Single<LocationModel> getCurrentLocation();
}
