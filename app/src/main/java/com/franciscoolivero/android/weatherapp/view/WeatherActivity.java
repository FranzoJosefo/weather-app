package com.franciscoolivero.android.weatherapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.franciscoolivero.android.weatherapp.R;
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.weather_tabs_layout)
    TabLayout tabLayout;

    @BindView(R.id.weather_toolbar)
    Toolbar toolbar;

    @BindView(R.id.weather_appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.weather_view_pager)
    ViewPager2 viewPager;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        setupTabLayoutWithViewPager();
        setupSwipeRefreshListener();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.more) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSwipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            weatherViewModel.fetchLocationAndWeatherData(true, this.getApplicationContext());
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void setupTabLayoutWithViewPager() {
        viewPager.setUserInputEnabled(false);
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this);
        viewPager.setAdapter(tabsPagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText(getResources().getString(R.string.today_tab));
                    } else {
                        tab.setText(getResources().getString(R.string.daily_tab));
                    }
                }).
                attach();
    }
}
