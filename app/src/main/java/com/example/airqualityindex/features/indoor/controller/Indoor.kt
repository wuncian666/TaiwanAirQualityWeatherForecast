package com.example.airqualityindex.features.indoor.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentIndoorBinding
import com.example.airqualityindex.features.indoor.viewmodel.WeatherForecastViewModel
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.user.viewmodel.UserViewModel
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class Indoor : Fragment() {
    private lateinit var binding: FragmentIndoorBinding

    private val navViewModel: NavigationViewModel = get()
    private val userViewModel: UserViewModel = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentIndoorBinding.inflate(inflater, container, false)
        this.binding.onClickListener = this

        this.binding.userName.text = this.userViewModel.getUserName()
        this.binding.textUserLocation.text = this.userViewModel.getLocationName()

        this.queryWeatherThenUpdateUi(this.userViewModel.getLocationName())

        this.weatherForecastViewModel.getRecordByLocationNameLiveData(this.userViewModel.getLocationName())
            .observe(viewLifecycleOwner) {
                this.updateUi(it)
            }

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navViewModel.navigationCallback?.onShowMenu()
            }
        }
    }

    private fun queryWeatherThenUpdateUi(locationName: String?) {
        this.weatherForecastViewModel.getRecordByLocationName(locationName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                this.updateUi(it)
            }
            .subscribe()
    }

    private fun updateUi(weather: WeatherForecastEntity) {
        this.updateWeatherImage(weather.weatherPhenomenon)
        this.updateWeatherText(weather)
    }

    private fun updateWeatherImage(phenom: String) {
        phenom.let {
            if (it == resources.getString(R.string.clear)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_clear)
            } else if (it == resources.getString(R.string.mostly_clear)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear)
            } else if (it == resources.getString(R.string.partly_clear)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_clear)
            } else if (it == resources.getString(R.string.partly_cloudy)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy)
            } else if (resources.getStringArray(R.array.mostly_cloudy).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_cloudy)
            } else if (resources.getStringArray(R.array.mostly_clear_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_rain)
            } else if (resources.getStringArray(R.array.rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_rain)
            } else if (resources.getStringArray(R.array.partly_cloudy_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy_rain)
            } else if (resources.getStringArray(R.array.mostly_cloudy_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_cloudy_rain)
            } else if (resources.getStringArray(R.array.partly_clear_rain_thunderstorms)
                    .contains(it)
            ) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_clear_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.cloudy_rain_thunderstorms).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.partly_cloudy_rain_thunderstorms)
                    .contains(it)
            ) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.mostly_cloudy_rain_thunderstorms)
                    .contains(it)
            ) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_cloudy_rain_thunderstorms)
            } else if (resources.getStringArray(R.array.partly_clear_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_clear_rain)
            } else if (resources.getStringArray(R.array.mostly_clear_thunderstorms).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_thounderstorms)
            } else if (resources.getStringArray(R.array.cloudy_snowy_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_snowy_rain)
            } else if (resources.getStringArray(R.array.clear_foggy).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_clear_foggy)
            } else if (resources.getStringArray(R.array.mostly_clear_foggy).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_foggy)
            } else if (resources.getStringArray(R.array.cloudy_foggy).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy)
            } else if (resources.getStringArray(R.array.cloudy_foggy_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy_rain)
            } else if (resources.getStringArray(R.array.cloudy_thunderstorms).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_thunderstorms)
            } else if (resources.getStringArray(R.array.cloudy_foggy_snowy_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy_snowy_rain)
            } else if (resources.getStringArray(R.array.mostly_clear_foggy_rain).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_mostly_clear_foggy_rain)
            } else if (resources.getStringArray(R.array.snowy).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_snowy)
            } else if (resources.getStringArray(R.array.partly_cloudy_foggy_thunderstorms)
                    .contains(it)
            ) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_partly_cloudy_foggy_thunderstorms)
            } else if (resources.getStringArray(R.array.cloudy_foggy_thunderstorms).contains(it)) {
                this.binding.imgWeather.setBackgroundResource(R.drawable.weather_cloudy_foggy_thunderstorms)
            }
        }
    }

    private fun updateWeatherText(weather: WeatherForecastEntity) {
        val range =
            weather.temperatureMin + "-" + weather.temperatureMax + this.resources.getString(R.string.celsius_unit)
        this.binding.textTemperatureRange.text = range

        val pop =
            weather.probabilityOfPrecipitation + this.resources.getString(R.string.percent_sign)
        this.binding.textProbabilityOfPrecipitation.text = pop
    }
}