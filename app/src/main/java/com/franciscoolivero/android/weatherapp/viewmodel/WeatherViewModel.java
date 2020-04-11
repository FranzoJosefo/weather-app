package com.franciscoolivero.android.weatherapp.viewmodel;

import com.franciscoolivero.android.weatherapp.data.NetworkService;
import com.franciscoolivero.android.weatherapp.di.DaggerApiComponent;
import com.franciscoolivero.android.weatherapp.model.BaseWeatherResponseModel;
import com.franciscoolivero.android.weatherapp.model.LocationModel;

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
    public MutableLiveData<LocationModel> locationModelMutableLiveData = new MutableLiveData<>();

    @Inject
    public NetworkService networkService;

    private CompositeDisposable disposable = new CompositeDisposable();

    public WeatherViewModel() {
        super();
        DaggerApiComponent.create().inject(this);
    }

    public void fetchLocationAndWeatherData(Boolean isRefreshing) {
        if (!isRefreshing) {
            isLoadingMutableLiveData.setValue(true);
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
                                    fetchWeatherData(String.valueOf(locationModelMutableLiveData.getValue().getCurrentLatitude()), String.valueOf(locationModelMutableLiveData.getValue().getCurrentLongitude()));
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

    private void fetchWeatherData(String lat, String lon) {
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
