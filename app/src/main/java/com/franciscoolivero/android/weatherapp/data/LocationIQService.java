package com.franciscoolivero.android.weatherapp.data;

import com.franciscoolivero.android.weatherapp.model.CityLocationModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationIQService {

    String LOCATION_IQ_API_KEY = "9a43a7b511e45a";
    String FORMAT = "json";

    @GET("v1/search.php")
    Single<CityLocationModel> getCityLocation(@Query(value = "key", encoded = true) String apiKey,
                                              @Query(value = "q", encoded = true) String cityName,
                                              @Query(value = "format", encoded = true) String format);
}
