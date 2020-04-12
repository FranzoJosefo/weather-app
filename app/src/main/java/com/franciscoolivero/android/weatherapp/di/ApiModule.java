package com.franciscoolivero.android.weatherapp.di;

import com.franciscoolivero.android.weatherapp.data.IpApiService;
import com.franciscoolivero.android.weatherapp.data.LocationIQService;
import com.franciscoolivero.android.weatherapp.data.NetworkService;
import com.franciscoolivero.android.weatherapp.data.OpenWeatherMapApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiModule {

    private static final String BASE_URL_OPEN_WEATHER_MAP = "https://api.openweathermap.org";
    private static final String BASE_URL_IP_API = "http://ip-api.com";
    private static final String BASE_URL_LOCATION_IQ = "https://eu.locationiq.com";

    @Provides
    public OpenWeatherMapApiService provideWeatherService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_OPEN_WEATHER_MAP)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(OpenWeatherMapApiService.class);
    }

    @Provides
    public IpApiService provideIPApiService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_IP_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(IpApiService.class);
    }

    @Provides
    public LocationIQService provideLocationIQService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_LOCATION_IQ)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(LocationIQService.class);
    }

    @Provides
    public NetworkService provideNetworkService() {
        return NetworkService.getInstance();
    }

}

