package com.franciscoolivero.android.weatherapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.franciscoolivero.android.weatherapp.R;
import com.franciscoolivero.android.weatherapp.model.CurrentWeatherModel;
import com.franciscoolivero.android.weatherapp.model.LocationModel;
import com.franciscoolivero.android.weatherapp.utils.DateUtils;
import com.franciscoolivero.android.weatherapp.utils.ImageUtils;
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    @BindView(R.id.current_weather_error_layout_container)
    LinearLayout loadingErrorContainer;

    @BindView(R.id.current_weather_progress_spinner)
    ProgressBar loadingSpinner;

    @BindView(R.id.weather_layout_container)
    LinearLayout weatherLayoutContainer;

    @BindView(R.id.hourly_recycler_view)
    RecyclerView hourlyRecyclerView;

    private static final String TAG = "CurrentWeatherFragment";
    private WeatherViewModel weatherViewModel;
    private HourlyRecyclerAdapter hourlyRecyclerAdapter = new HourlyRecyclerAdapter(new ArrayList<>());


    public static CurrentWeatherFragment newInstance() {
        return new CurrentWeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        weatherViewModel = ViewModelProviders.of(requireActivity()).get(WeatherViewModel.class);
        setupObserversViewModel();
        setupRecyclerView();
        weatherViewModel.fetchLocationAndWeatherData(false);
    }

    private void setupRecyclerView() {
        hourlyRecyclerView.setHasFixedSize(true);
        hourlyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(hourlyRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        hourlyRecyclerView.addItemDecoration(dividerItemDecoration);
        hourlyRecyclerView.setAdapter(hourlyRecyclerAdapter);
    }

    private void setupObserversViewModel() {
        weatherViewModel.baseWeatherResponseMutableLiveData.observe(this, baseWeatherResponseModel -> {
            updateCurrentWeatherInfoUI(baseWeatherResponseModel.getCurrentWeatherModel());
            hourlyRecyclerAdapter.updateHourlyForecast(baseWeatherResponseModel.getHourlyWeatherModelList());
        });
        weatherViewModel.locationModelMutableLiveData.observe(this, this::updateLocationUI);
        weatherViewModel.isLoadingMutableLiveData.observe(this, this::setLoadingSpinnerVisibility);
        weatherViewModel.errorLoadingMutableLiveData.observe(this, isError -> {
            if(isError!=null) {
                setErrorStateVisibility(isError);
            }
        });
    }

    private void setErrorStateVisibility(boolean show) {
        loadingErrorContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        weatherLayoutContainer.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void setLoadingSpinnerVisibility(boolean show) {
        loadingSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            weatherLayoutContainer.setVisibility(View.GONE);
        }
    }

    private void updateLocationUI(LocationModel locationModel) {
        location.setText(getString(R.string.location_format, locationModel.getCityName(), locationModel.getRegionName()));
    }

    private void updateCurrentWeatherInfoUI(CurrentWeatherModel currentWeatherModel) {
        currentTemperature.setText(getString(R.string.temperature_unit, currentWeatherModel.getTemperature()));
        currentRealFeel.setText(getString(R.string.temperature_unit, currentWeatherModel.getRealFeel()));
        currentWindSpeed.setText(getString(R.string.wind_speed_unit, currentWeatherModel.getWindSpeed()));
        currentHumidity.setText(getString(R.string.humidity_unit, currentWeatherModel.getHumidity()));
        currentPressure.setText(getString(R.string.pressure_unit, currentWeatherModel.getPressure()));
        currentWeatherDescription.setText(currentWeatherModel.getBasicWeatherModelList().get(0).getWeatherDescriptionCapitalized());
        currentWeatherDate.setText(DateUtils.getFormattedDateFromEpoch(currentWeatherModel.getCurrentTime(), getString(R.string.EEEE_MMMM_dd_pattern)));
        ImageUtils.loadImage(currentWeatherIcon,
                getString(R.string.open_weather_image_url, currentWeatherModel.getBasicWeatherModelList().get(0).getWeatherIconCode()),
                ImageUtils.getProgressDrawable(currentWeatherIcon.getContext()));
    }
}
