package com.franciscoolivero.android.weatherapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.franciscoolivero.android.weatherapp.R;
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

public class DailyWeatherFragment extends Fragment {

    @BindView(R.id.daily_recycler_view)
    RecyclerView dailyRecyclerView;
    @BindView(R.id.daily_error_layout_container)
    LinearLayout loadingErrorContainer;
    @BindView(R.id.daily_progress_spinner)
    ProgressBar loadingSpinner;

    private WeatherViewModel weatherViewModel;
    private DailyRecyclerAdapter dailyRecyclerAdapter = new DailyRecyclerAdapter(new ArrayList<>());

    public static DailyWeatherFragment newInstance() {
        return new DailyWeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_daily_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherViewModel = ViewModelProviders.of(requireActivity()).get(WeatherViewModel.class);
        setupObserversViewModel();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        dailyRecyclerView.setHasFixedSize(true);
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dailyRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dailyRecyclerView.addItemDecoration(dividerItemDecoration);
        dailyRecyclerView.setAdapter(dailyRecyclerAdapter);
    }

    private void setupObserversViewModel() {
        weatherViewModel.isLoadingMutableLiveData.observe(this, this::setLoadingSpinnerVisibility);
        weatherViewModel.errorLoadingMutableLiveData.observe(this, isError -> {
            if (isError != null) {
                setErrorStateVisibility(isError);
            }
        });
        weatherViewModel.baseWeatherResponseMutableLiveData.observe(this, baseWeatherResponseModel -> {
            dailyRecyclerAdapter.updateDailyForecast(baseWeatherResponseModel.getDailyWeatherModelList());
        });
    }

    private void setErrorStateVisibility(boolean show) {
        loadingErrorContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        dailyRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void setLoadingSpinnerVisibility(boolean show) {
        loadingSpinner.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            dailyRecyclerView.setVisibility(View.GONE);
        }
    }

}
