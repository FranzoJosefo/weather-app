package com.franciscoolivero.android.weatherapp.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.franciscoolivero.android.weatherapp.R;
import com.franciscoolivero.android.weatherapp.data.NetworkService;
import com.franciscoolivero.android.weatherapp.di.DaggerApiComponent;
import com.franciscoolivero.android.weatherapp.model.BaseWeatherResponseModel;
import com.franciscoolivero.android.weatherapp.model.CityLocationModel;
import com.franciscoolivero.android.weatherapp.model.CurrentLocationModel;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends ViewModel {

    public MutableLiveData<BaseWeatherResponseModel> baseWeatherResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorLoadingMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<CurrentLocationModel> currentLocationModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<CityLocationModel> cityLocationModelMutableLiveData = new MutableLiveData<>();

    @Inject
    public NetworkService networkService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public WeatherViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }

    public void fetchLocationAndWeatherData(Boolean isRefreshing, Context context) {
        if (!isRefreshing) {
            isLoadingMutableLiveData.setValue(true);
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String location = sharedPrefs.getString(
                context.getResources().getString(R.string.location_preference_key),
                context.getResources().getString(R.string.settings_select_location_default)
        );

        if (location.equals(context.getResources().getString(R.string.settings_location_current_value))) {
            fetchCurrentLocationWeatherData();
        } else {
            fetchCityLocationWeatherData(location);
        }
    }

    private void fetchCityLocationWeatherData(String cityName) {
        disposable.add(
                networkService.getCityLocation(cityName)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<CityLocationModel>>() {
                            @Override
                            public void onSuccess(List<CityLocationModel> cityLocationModelList) {
                                cityLocationModelMutableLiveData.setValue(cityLocationModelList.get(0));
                                if (cityLocationModelMutableLiveData.getValue() != null) {
                                    fetchWeatherDataFromService(String.valueOf(cityLocationModelMutableLiveData.getValue().getCityLatitude()), String.valueOf(cityLocationModelMutableLiveData.getValue().getCityLongitude()));
                                } else {
                                    errorLoadingMutableLiveData.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorLoadingMutableLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void fetchCurrentLocationWeatherData() {
        disposable.add(
                networkService.getCurrentLocation()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CurrentLocationModel>() {
                            @Override
                            public void onSuccess(CurrentLocationModel currentLocationModel) {
                                currentLocationModelMutableLiveData.setValue(currentLocationModel);
                                if (currentLocationModelMutableLiveData.getValue() != null) {
                                    fetchWeatherDataFromService(String.valueOf(currentLocationModelMutableLiveData.getValue().getCurrentLatitude()), String.valueOf(currentLocationModelMutableLiveData.getValue().getCurrentLongitude()));
                                } else {
                                    errorLoadingMutableLiveData.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorLoadingMutableLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void fetchWeatherDataFromService(String lat, String lon) {
        disposable.add(
                networkService.getWeatherData(lat, lon)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BaseWeatherResponseModel>() {
                            @Override
                            public void onSuccess(BaseWeatherResponseModel baseWeatherResponseModel) {
                                baseWeatherResponseMutableLiveData.setValue(baseWeatherResponseModel);
                                errorLoadingMutableLiveData.setValue(false);
                                isLoadingMutableLiveData.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                errorLoadingMutableLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
