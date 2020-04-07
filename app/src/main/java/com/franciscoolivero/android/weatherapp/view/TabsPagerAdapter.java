package com.franciscoolivero.android.weatherapp.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabsPagerAdapter extends FragmentStateAdapter {

    public TabsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return CurrentWeatherFragment.newInstance();
        } else {
            return DailyWeatherFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
