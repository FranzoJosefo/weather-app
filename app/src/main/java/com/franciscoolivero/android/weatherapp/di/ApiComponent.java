package com.franciscoolivero.android.weatherapp.di;

import com.franciscoolivero.android.weatherapp.data.NetworkService;
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(NetworkService networkService);

    void inject(WeatherViewModel weatherViewModel);
}
