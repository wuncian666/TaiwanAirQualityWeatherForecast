package com.example.airqualityindex.features.main.controller

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.ActivityMainBinding
import com.example.airqualityindex.shared.units.AlarmReceiver
import com.example.airqualityindex.shared.units.SystemTime
import com.example.airqualityindex.features.outdoor.viewmodels.AirQualityViewModel
import com.example.airqualityindex.features.indoor.viewmodel.WeatherForecastViewModel
import com.example.airqualityindex.features.main.services.NavigationCallback
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.shared.constant.MainConfig.ID_INDOOR
import com.example.airqualityindex.shared.constant.MainConfig.ID_OUTDOOR
import com.google.android.material.navigation.NavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    NavigationCallback {
    private val navCallback: NavigationViewModel = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()
    private val perHourAirQualityViewModel: AirQualityViewModel = get()

    private lateinit var binding: ActivityMainBinding

    private var navHostFragment: NavHostFragment? = null
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        this.navCallback.navigationCallback = this

        this.navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        this.navController = this.navHostFragment!!.navController

        this.setDrawerNavigation()

        this.setBottomNavigation()

        this.requestPermission()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        this.requestApi()

        this.periodicWorkRequest()
    }

    override fun onStop() {
        super.onStop()

        this.cancelDailyApiRequest(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun requestApi() {
        this.weatherForecastViewModel.getApiResponse(SystemTime().getCurrentTime())
            .doOnSuccess {
                val weatherForecastStores = weatherForecastViewModel.turnStoreFormat(it)
                this.weatherForecastViewModel.insertWeatherForecastInDatabase(weatherForecastStores)
            }
            .flatMap {
                this.perHourAirQualityViewModel.requestApi()
            }
            .doOnSuccess {
                this.perHourAirQualityViewModel.insert(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun periodicWorkRequest() {
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getService(this, 0, intent, 0)

        val triggerTime = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            triggerTime,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun cancelDailyApiRequest(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getService(context, 0, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

    private fun setBottomNavigation() {
        val bottomNavigation = binding.bottomNavigation

        bottomNavigation.apply {
            add(MeowBottomNavigation.Model(ID_INDOOR, R.drawable.ic_indoor))
            add(MeowBottomNavigation.Model(ID_OUTDOOR,R.drawable.ic_outdoor))

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
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            this.binding.drawerLayout,
            this.binding.toolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )

        this.binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        this.binding.navigationView.setNavigationItemSelectedListener(this)
        this.binding.navigationView.itemIconTintList = null
        actionBarDrawerToggle.syncState()
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