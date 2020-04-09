package com.franciscoolivero.android.weatherapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franciscoolivero.android.weatherapp.R;
import com.franciscoolivero.android.weatherapp.model.BaseWeatherResponseModel;
import com.franciscoolivero.android.weatherapp.model.CurrentWeatherModel;
import com.franciscoolivero.android.weatherapp.model.LocationModel;
import com.franciscoolivero.android.weatherapp.utils.ImageUtils;
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentWeatherFragment extends Fragment {

    @BindView(R.id.date)
    TextView currentWeatherDate;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.weather_icon)
    ImageView currentWeatherIcon;
    @BindView(R.id.weather_description)
    TextView currentWeatherDescription;
    @BindView(R.id.temperature)
    TextView currentTemperature;
    @BindView(R.id.real_feel)
    TextView currentRealFeel;
    @BindView(R.id.pressure)
    TextView currentPressure;
    @BindView(R.id.humidity)
    TextView currentHumidity;
    @BindView(R.id.wind_measurement)
    TextView currentWindSpeed;

    private WeatherViewModel weatherViewModel;

    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObserversViewModel();
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.fetchLocationAndWeatherData(false);
    }

    private void setupObserversViewModel() {
        weatherViewModel.baseWeatherResponseMutableLiveData.observe(this, new Observer<BaseWeatherResponseModel>() {
            @Override
            public void onChanged(BaseWeatherResponseModel baseWeatherResponseModel) {
                updateCurrentWeatherInfoUI(baseWeatherResponseModel.getCurrentWeatherModel());
            }
        });

        weatherViewModel.locationModelMutableLiveData.observe(this, new Observer<LocationModel>() {
            @Override
            public void onChanged(LocationModel locationModel) {
                updateLocationUI(locationModel);
            }
        });
    }

    private void updateLocationUI(LocationModel locationModel) {
        location.setText(getString(R.string.location_format, locationModel.getCityName(), locationModel.getRegionName()));
    }

    private void updateCurrentWeatherInfoUI(CurrentWeatherModel currentWeatherModel) {
        currentTemperature.setText(getString(R.string.temperature_unit, currentWeatherModel.getTemperature()));
        currentRealFeel.setText(getString(R.string.temperature_real_feel_unit, currentWeatherModel.getRealFeel()));
        currentWindSpeed.setText(getString(R.string.wind_speed_unit, currentWeatherModel.getWindSpeed()));
        currentHumidity.setText(getString(R.string.humidity_unit, currentWeatherModel.getHumidity()));
        currentPressure.setText(getString(R.string.pressure_unit, currentWeatherModel.getPressure()));
        currentWeatherDescription.setText(String.valueOf(currentWeatherModel.getBasicWeatherModelList().get(0).getWeatherCondition()));
        ImageUtils.loadImage(currentWeatherIcon,
                getString(R.string.open_weather_image_url, currentWeatherModel.getBasicWeatherModelList().get(0).getWeatherIconCode()),
                ImageUtils.getProgressDrawable(currentWeatherIcon.getContext()));
    }
}
