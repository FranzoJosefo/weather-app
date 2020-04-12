package com.franciscoolivero.android.weatherapp.data;

import com.franciscoolivero.android.weatherapp.di.DaggerApiComponent;
import com.franciscoolivero.android.weatherapp.model.BaseWeatherResponseModel;
import com.franciscoolivero.android.weatherapp.model.CityLocationModel;
import com.franciscoolivero.android.weatherapp.model.CurrentLocationModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class NetworkService {
    private static NetworkService instance;

    @Inject
    public OpenWeatherMapApiService weatherMapApiService;

    @Inject
    public IpApiService ipApiService;

    @Inject
    public LocationIQService locationIQService;

    private NetworkService() {
        DaggerApiComponent.create().inject(this);
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public Single<CurrentLocationModel> getCurrentLocation() {
        return ipApiService.getCurrentLocation();
    }

    public Single<BaseWeatherResponseModel> getWeatherData(String lat, String lon) {
        return weatherMapApiService.getWeatherData(lat, lon, OpenWeatherMapApiService.UNIT_METRIC, OpenWeatherMapApiService.OPEN_WEATHER_API_KEY);
    }

    public Single<CityLocationModel> getCityLocation(String cityName) {
        return locationIQService.getCityLocation(LocationIQService.LOCATION_IQ_API_KEY, cityName, LocationIQService.FORMAT);
    }

}
