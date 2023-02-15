package com.example.airqualityindex.features.main.controller

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.ActivityMainBinding
import com.example.airqualityindex.features.indoor.viewmodel.WeatherForecastViewModel
import com.example.airqualityindex.features.main.service.NavigationCallback
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodel.AirQualityViewModel
import com.example.airqualityindex.shared.constant.MainConfig.ID_INDOOR
import com.example.airqualityindex.shared.constant.MainConfig.ID_OUTDOOR
import com.example.airqualityindex.shared.util.SystemTime
import com.google.android.material.navigation.NavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavigationCallback {
    private val navCallback: NavigationViewModel = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()
    private val perHourAirQualityViewModel: AirQualityViewModel = get()

    private var timeDisposable: Disposable? = null

    private lateinit var binding: ActivityMainBinding

    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        this.navCallback.navigationCallback = this
        this.navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        this.navController = this.navHostFragment!!.navController

        this.setDrawerNavigation()
        this.setBottomNavigation()
        this.requestPermission()
    }

    override fun onStart() {
        super.onStart()
        this.requestApi()
        this.startTimer()
    }

    override fun onStop() {
        super.onStop()
        this.timeDisposable?.dispose()
    }

    private fun startTimer() {
        this.timeDisposable?.dispose()
        this.timeDisposable = Observable.interval(
            SystemTime().getPeriodTimeNextHour(),
            3600,
            TimeUnit.SECONDS,
            Schedulers.io()
        )
            .doOnNext {
                this.requestApi()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { Log.e("TAG", "startTimer: ${it.message}") })
    }

    private fun requestApi() {
        this.weatherForecastViewModel.getApiResponse(SystemTime().getCurrentTime())
            .flatMap {
                val weatherForecast = this.weatherForecastViewModel.turnStoreFormat(it)
                this.weatherForecastViewModel.insert(weatherForecast)
            }
            .flatMap {
                this.perHourAirQualityViewModel.requestApi()
            }
            .flatMap {
                this.perHourAirQualityViewModel.insert(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun setBottomNavigation() {
        val navigation = this.binding.bottomNavigation

        navigation.apply {
            add(MeowBottomNavigation.Model(ID_INDOOR, R.drawable.ic_indoor))
            add(MeowBottomNavigation.Model(ID_OUTDOOR, R.drawable.ic_outdoor))

            setOnShowListener {
                when (it.id) {
                    ID_INDOOR -> navController?.navigate(R.id.indoorFragment)
                    ID_OUTDOOR -> navController?.navigate(R.id.outdoorFragment)
                }
            }

            show(ID_INDOOR)
        }
    }

    private fun setDrawerNavigation() {
        val toggle = ActionBarDrawerToggle(
            this,
            this.binding.drawerLayout,
            this.binding.toolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )

        this.binding.drawerLayout.addDrawerListener(toggle)
        this.binding.navigationView.setNavigationItemSelectedListener(this)
        this.binding.navigationView.itemIconTintList = null
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        item.isCheckable = false
        when (item.itemId) {
            R.id.menu_my_air -> {
                this.onInvisibleBottomNavigation()
                this.navController?.navigate(R.id.myAirFragment)
            }
            R.id.menu_aqi_search -> {
                this.onInvisibleBottomNavigation()
                this.navController?.navigate(R.id.searchAQIFragment)
            }
            R.id.menu_change_location -> {
                this.onInvisibleBottomNavigation()
                this.navController?.navigate(R.id.changeLocationFragment)
            }
            R.id.menu_logout -> {
                // TODO: menu logout 
            }
            R.id.menu_setting -> {
                // TODO: menu setting 
            }
        }
        return true
    }

    private fun requestPermission() {
        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )
        }
    }

    override fun onShowMenu() {
        this.binding.drawerLayout.open()
    }

    override fun onInvisibleBottomNavigation() {
        this.binding.bottomNavigation.visibility = View.GONE
    }

    override fun onLogout() {

    }

    override fun onPressBack() {
        this.binding.bottomNavigation.visibility = View.VISIBLE
    }

    override fun onItemOnSelected() {

    }
}