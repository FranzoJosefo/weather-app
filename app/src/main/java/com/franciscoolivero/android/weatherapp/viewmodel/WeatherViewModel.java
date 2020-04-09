package com.franciscoolivero.android.weatherapp.viewmodel;

import com.franciscoolivero.android.weatherapp.data.NetworkService;
import com.franciscoolivero.android.weatherapp.di.DaggerApiComponent;
import com.franciscoolivero.android.weatherapp.model.BaseWeatherResponseModel;
import com.franciscoolivero.android.weatherapp.model.CurrentWeatherModel;
import com.franciscoolivero.android.weatherapp.model.DailyWeatherModel;
import com.franciscoolivero.android.weatherapp.model.LocationModel;
import com.franciscoolivero.android.weatherapp.model.WeatherModel;

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
    public MutableLiveData<CurrentWeatherModel> currentWeatherMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<DailyWeatherModel>> dailyWeatherListMutableLiveDataList = new MutableLiveData<>();
    public MutableLiveData<List<WeatherModel>> weatherModeMutableLiveDataList = new MutableLiveData<>();
    public MutableLiveData<Boolean> weatherErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoadingMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<LocationModel> locationModelMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> locationErrorLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> locationIsLoadingLiveData = new MutableLiveData<>();

    @Inject
    public NetworkService networkService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public WeatherViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }


    public void fetchLocationAndWeatherData(Boolean isRefreshing) {
        if (!isRefreshing) {
            locationIsLoadingLiveData.setValue(true);
        }

        disposable.add(
                networkService.getCurrentLocation()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<LocationModel>() {
                            @Override
                            public void onSuccess(LocationModel locationModel) {
                                locationModelMutableLiveData.setValue(locationModel);
                                if (locationModelMutableLiveData.getValue() != null) {
                                    locationErrorLiveData.setValue(false);
                                    fetchWeatherData(String.valueOf(locationModelMutableLiveData.getValue().getCurrentLatitude()), String.valueOf(locationModelMutableLiveData.getValue().getCurrentLongitude()));
                                } else {
                                    locationErrorLiveData.setValue(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                locationErrorLiveData.setValue(true);
                                isLoadingMutableLiveData.setValue(false);
                                e.printStackTrace();
                            }
                        })
        );
    }

    private void fetchWeatherData(String lat, String lon) {
        disposable.add(
                networkService.getWeatherData(lat, lon)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<BaseWeatherResponseModel>() {
                            @Override
                            public void onSuccess(BaseWeatherResponseModel baseWeatherResponseModel) {
                                baseWeatherResponseMutableLiveData.setValue(baseWeatherResponseModel);
                                locationErrorLiveData.setValue(false);
                                isLoadingMutableLiveData.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                weatherErrorLiveData.setValue(true);
                                locationIsLoadingLiveData.setValue(false);
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
