package com.franciscoolivero.android.weatherapp.view;

import android.os.Bundle;

import com.franciscoolivero.android.weatherapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setupTabLayoutWithViewPager();
    }

    private void setupTabLayoutWithViewPager() {
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
