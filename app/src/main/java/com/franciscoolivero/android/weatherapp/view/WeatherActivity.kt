package com.franciscoolivero.android.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.franciscoolivero.android.weatherapp.R
import com.franciscoolivero.android.weatherapp.viewmodel.WeatherViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy

class WeatherActivity : AppCompatActivity() {
    @JvmField
    @BindView(R.id.coordinator_layout)
    var coordinatorLayout: CoordinatorLayout? = null

    @JvmField
    @BindView(R.id.weather_tabs_layout)
    var tabLayout: TabLayout? = null

    @JvmField
    @BindView(R.id.weather_toolbar)
    var toolbar: Toolbar? = null

    @JvmField
    @BindView(R.id.weather_appbar)
    var appBarLayout: AppBarLayout? = null

    @JvmField
    @BindView(R.id.weather_view_pager)
    var viewPager: ViewPager2? = null

    @JvmField
    @BindView(R.id.swipe_refresh_layout)
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var weatherViewModel: WeatherViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        ButterKnife.bind(this)
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        setupTabLayoutWithViewPager()
        setupSwipeRefreshListener()
        setSupportActionBar(toolbar)
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
        swipeRefreshLayout!!.setOnRefreshListener {
            weatherViewModel!!.fetchLocationAndWeatherData(true, this.applicationContext)
            swipeRefreshLayout!!.isRefreshing = false
        }
    }

    private fun setupTabLayoutWithViewPager() {
        viewPager!!.isUserInputEnabled = false
        val tabsPagerAdapter = TabsPagerAdapter(this)
        viewPager!!.adapter = tabsPagerAdapter
        TabLayoutMediator(tabLayout!!, viewPager!!,
                TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                    if (position == 0) {
                        tab.text = resources.getString(R.string.today_tab)
                    } else {
                        tab.text = resources.getString(R.string.daily_tab)
                    }
                }).attach()
    }
}