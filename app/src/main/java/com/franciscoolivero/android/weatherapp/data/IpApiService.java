package com.franciscoolivero.android.weatherapp.data;

import com.franciscoolivero.android.weatherapp.model.LocationModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IpApiService {

    @GET("json/{ip_address}")
    Single<LocationModel> getCurrentLocation(@Path(value = "ip_address", encoded = true) String ip_address);
}
