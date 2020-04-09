package com.franciscoolivero.android.weatherapp.data;

import com.franciscoolivero.android.weatherapp.di.DaggerApiComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkService {
    private static NetworkService instance;

    @Inject
    public OpenWeatherMapApiService weatherMapApiService;

    @Inject
    public IpApiService ipApiService;

    private NetworkService() {
        DaggerApiComponent.create().inject(this);
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }
}
