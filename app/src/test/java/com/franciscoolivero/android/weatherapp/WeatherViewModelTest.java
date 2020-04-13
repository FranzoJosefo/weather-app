package com.franciscoolivero.android.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.franciscoolivero.android.weatherapp.data.IpApiService;
import com.franciscoolivero.android.weatherapp.data.LocationIQService;
import com.franciscoolivero.android.weatherapp.data.OpenWeatherMapApiService;
import com.franciscoolivero.android.weatherapp.model.CityLocationModel;
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class WeatherViewModelTest {

    //Any executed task will be instant
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private OpenWeatherMapApiService openWeatherMapApiServiceMock;

    @Mock
    private LocationIQService locationIQServiceMock;

    @Mock
    private IpApiService ipApiServiceMock;

    @Mock
    private SharedPreferences sharedPreferences;

    @Mock
    private Context context;

    private Single<List<CityLocationModel>> cityLocationsSingle;

    @InjectMocks
    WeatherViewModel weatherViewModel = new WeatherViewModel();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    //Setup RxSchedulers so that we don't interrupt execution when background thread and main thread are created in our viewModel fetch async calls, instead they'll return immediately.
    @Before
    public void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Test
    public void fetchCityLocationWeatherDataOnSuccessTest() {
        CityLocationModel cityLocationModel = new CityLocationModel(52.5170365, 13.3888599, "Berlin, 10117, Germany");
        ArrayList<CityLocationModel> cityLocationsList = new ArrayList<>();
        cityLocationsList.add(cityLocationModel);

        cityLocationsSingle = Single.just(cityLocationsList);

        Mockito.when(locationIQServiceMock.getCityLocation("1234", "Berlin", "json")).thenReturn(cityLocationsSingle);
        Mockito.when(sharedPreferences.getString(Mockito.anyString(), Mockito.anyString())).thenReturn("berlin");
        Mockito.when(context.getString(Mockito.anyInt())).thenReturn("current");

        weatherViewModel.fetchLocationAndWeatherData(false, context, "berlin");
        Assert.assertEquals(cityLocationModel.getCityLatitude(), weatherViewModel.cityLocationModelMutableLiveData.getValue().getCityLatitude());
        Assert.assertEquals(cityLocationModel.getCityLongitude(), weatherViewModel.cityLocationModelMutableLiveData.getValue().getCityLongitude());
        Assert.assertEquals(cityLocationModel.getDisplayName(), weatherViewModel.cityLocationModelMutableLiveData.getValue().getDisplayName());
    }
}
