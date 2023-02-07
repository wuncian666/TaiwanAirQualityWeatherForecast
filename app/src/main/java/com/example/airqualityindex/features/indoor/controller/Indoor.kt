package com.example.airqualityindex.features.indoor.controller

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentIndoorBinding
import com.example.airqualityindex.shared.models.WeatherForecastStore
import com.example.airqualityindex.shared.database.SharedPreferencesManager
import com.example.airqualityindex.features.indoor.viewmodels.WeatherForecastViewModel
import com.example.airqualityindex.features.main.viewmodels.NavigationCallbackImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class IndoorFragment : Fragment() {
    companion object {
        private val TAG = IndoorFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentIndoorBinding

    private val navCallback: NavigationCallbackImpl = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()
    private val sharedPreferencesManager: SharedPreferencesManager = get()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentIndoorBinding.inflate(inflater, container, false)

        this.binding.openCustomDrawer.setOnClickListener { this.navCallback.navigationCallback?.onShowMenu() }

        this.queryWeatherThenUpdateUi(sharedPreferencesManager.getLocationName())

        this.binding.userName.text = sharedPreferencesManager.getUserName()
        this.binding.textUserLocation.text = sharedPreferencesManager.getLocationName()

        this.weatherForecastViewModel.getRecordByLocationNameLiveData(sharedPreferencesManager.getLocationName())
            .observe(viewLifecycleOwner) {
                this.updateUi(it)
            }

        return binding.root
    }

    private fun queryWeatherThenUpdateUi(locationName: String?) {
        weatherForecastViewModel.getRecordByLocationName(locationName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                updateUi(it)
            }
            .subscribe()
    }

    private fun updateUi(weatherForecast: WeatherForecastStore) {
        this.updateWeatherImage(weatherForecast.weatherPhenomenon)
        this.updateTemperatureRangeText(weatherForecast.temperatureMin, weatherForecast.temperatureMax)
        this.updateProbabilityOfPrecipitationText(weatherForecast.probabilityOfPrecipitation)
    }

    private fun updateWeatherImage(weatherPhenomenon: String) {
        weatherPhenomenon.let {
            if (it == resources.getString(R.string.clear)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_clear)
            } else if (it == resources.getString(R.string.mostly_clear)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear)
            } else if (it == resources.getString(R.string.partly_clear)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_clear)
            } else if (it == resources.getString(R.string.partly_cloudy)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy)
            } else if (resources.getStringArray(R.array.mostly_cloudy).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_cloudy)
            } else if (resources.getStringArray(R.array.mostly_clear_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_rain)
            } else if (resources.getStringArray(R.array.rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_rain)
            } else if (resources.getStringArray(R.array.partly_cloudy_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy_rain)
            } else if (resources.getStringArray(R.array.mostly_cloudy_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_cloudy_rain)
            } else if (resources.getStringArray(R.array.partly_clear_rain_thunderstorms)
                    .contains(it)
            ) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_clear_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.cloudy_rain_thunderstorms).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.partly_cloudy_rain_thunderstorms)
                    .contains(it)
            ) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.mostly_cloudy_rain_thunderstorms)
                    .contains(it)
            ) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_cloudy_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.partly_clear_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_clear_rain)
            } else if (resources.getStringArray(R.array.mostly_clear_thunderstorms).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_thounderstorms)
            } else if (resources.getStringArray(R.array.cloudy_snowy_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_snowy_rain)
            } else if (resources.getStringArray(R.array.clear_foggy).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_clear_foggy)
            } else if (resources.getStringArray(R.array.mostly_clear_foggy).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_foggy)
            } else if (resources.getStringArray(R.array.cloudy_foggy).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy)
            } else if (resources.getStringArray(R.array.cloudy_foggy_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy_rain)
            } else if (resources.getStringArray(R.array.cloudy_thunderstorms).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_thunderstorms)
            } else if (resources.getStringArray(R.array.cloudy_foggy_snowy_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy_snowy_rain)
            } else if (resources.getStringArray(R.array.mostly_clear_foggy_rain).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_foggy_rain)
            } else if (resources.getStringArray(R.array.snowy).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_snowy)
            } else if (resources.getStringArray(R.array.partly_cloudy_foggy_thunderstorms)
                    .contains(it)
            ) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy_foggy_thunderstorms)
            } else if (resources.getStringArray(R.array.cloudy_foggy_thunderstorms).contains(it)) {
                binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy_thunderstorms)
            }
        }
    }

    private fun updateTemperatureRangeText(temperatureMin: String, temperatureMax: String) {
        val temperatureRange =
            temperatureMin + "-" + temperatureMax + resources.getString(R.string.celsius_unit)
        binding.textTemperatureRange.text = temperatureRange
    }

    private fun updateProbabilityOfPrecipitationText(probabilityOfPrecipitation: String) {
        val probabilityOfPrecipitationWithPercentSign =
            probabilityOfPrecipitation + resources.getString(R.string.percent_sign)
        binding.textProbabilityOfPrecipitation.text = probabilityOfPrecipitationWithPercentSign
    }
}