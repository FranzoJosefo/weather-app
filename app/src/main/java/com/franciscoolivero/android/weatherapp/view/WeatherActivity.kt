package com.franciscoolivero.android.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.franciscoolivero.android.weatherapp.R
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        ButterKnife.bind(this)
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        setupTabLayoutWithViewPager()
        setupSwipeRefreshListener()
        setSupportActionBar(weather_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.weather_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.more) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupSwipeRefreshListener() {
        swipe_refresh_layout.setOnRefreshListener {
            weatherViewModel.fetchLocationAndWeatherData(true, this.applicationContext)
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun setupTabLayoutWithViewPager() {
        weather_view_pager.isUserInputEnabled = false
        val tabsPagerAdapter = TabsPagerAdapter(this)
        weather_view_pager.adapter = tabsPagerAdapter
        TabLayoutMediator(weather_tabs_layout, weather_view_pager,
                TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                    if (position == 0) {
                        tab.text = resources.getString(R.string.today_tab)
                    } else {
                        tab.text = resources.getString(R.string.daily_tab)
                    }
                }).attach()
    }
}