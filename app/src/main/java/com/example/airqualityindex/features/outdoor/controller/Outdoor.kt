package com.example.airqualityindex.features.outdoor.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentOutdoorBinding
import com.example.airqualityindex.features.indoor.viewmodel.WeatherForecastViewModel
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodels.AirQualityViewModel
import com.example.airqualityindex.features.user.viewmodel.UserViewModel
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import com.example.airqualityindex.shared.units.SpannableStringService
import com.skydoves.balloon.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class Outdoor : Fragment() {
    private lateinit var binding: FragmentOutdoorBinding

    private val navCallback: NavigationViewModel = get()
    private val userViewModel: UserViewModel = get()
    private val perHourAQIViewModel: AirQualityViewModel = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentOutdoorBinding.inflate(inflater, container, false)
        this.binding.outdoor = this

        this.queryDatabaseThenUpdateUI()

        this.perHourAQIViewModel.getRecordBySiteNameLiveData(userViewModel.getSiteName())
            .observe(viewLifecycleOwner) {
                this.updateAirQualityUi(it)
            }

        this.weatherForecastViewModel.getRecordByLocationNameLiveData(userViewModel.getLocationName())
            .observe(viewLifecycleOwner) {
                this.setTemperatureRangeText(it.temperatureMin, it.temperatureMax)
            }

        return this.binding.root
    }

    fun onCLickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navCallback.navigationCallback?.onShowMenu()
            }

            R.id.outdoor_warning -> {
                if (isWarningState()) {
                    this.binding.outdoorWarning.showAlignBottom(this.getBalloon())
                }
            }
        }
    }

    private fun isWarningState(): Boolean {
        return binding.outdoorWarning.background.constantState == ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_outdoor_warning
        )?.constantState
    }

    private fun getBalloon(): Balloon {
        return Balloon.Builder(requireContext())
            .setWidthRatio(0.2f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setLayout(R.layout.fragment_outdoor_dialog)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setLifecycleOwner(this)
            .build()
    }

    private fun queryDatabaseThenUpdateUI() {
        this.perHourAQIViewModel.getDataBySiteName(userViewModel.getSiteName())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                updateAirQualityUi(it)
            }
            .flatMap {
                weatherForecastViewModel.getRecordByLocationName(userViewModel.getLocationName())
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                setTemperatureRangeText(it.temperatureMin, it.temperatureMax)
            }
            .subscribe()
    }

    private fun updateAirQualityUi(record: PerHourRecord) {
        this.updateAirQualityText(record)
        this.updateAirQualityIndexImage(record.aqi.toInt())
    }

    private fun setTemperatureRangeText(temperatureMin: String, temperatureMax: String) {
        val text =
            temperatureMin + "-" + temperatureMax + resources.getString(R.string.celsius_unit)
        this.binding.textTemperatureRange.text = text
    }

    private fun updateAirQualityText(record: PerHourRecord) {
        val spannableStringService = SpannableStringService()
        spannableStringService.setSuperscriptText(
            record.pm25 + resources.getString(R.string.micrometer_per_cubic_meter),
            this.binding.textPm25
        )
        spannableStringService.setSuperscriptText(
            record.pm10 + resources.getString(R.string.micrometer_per_cubic_meter),
            this.binding.textPm10
        )

        val o3withUnit = record.o3 + resources.getString(R.string.parts_per_billion)
        this.binding.textO3.text = o3withUnit
        val coWithUnit = record.co + resources.getString(R.string.parts_per_million)
        this.binding.textCo.text = coWithUnit
        val so2WithUnit = record.so2 + resources.getString(R.string.parts_per_billion)
        this.binding.textSo2.text = so2WithUnit
        val no2WithUnit = record.no2 + resources.getString(R.string.parts_per_billion)
        this.binding.textNo2.text = no2WithUnit
    }

    private fun updateAirQualityIndexImage(value: Int) {
        value.let {
            when (it) {
                in (0..50) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_good,
                        R.drawable.background_green
                    )
                }

                in (51..100) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_moderate,
                        R.drawable.background_yellow
                    )
                }

                in (101..150) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_unhealthy_sensitive_groups,
                        R.drawable.background_orange
                    )
                }

                in (151..200) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_unhealthy,
                        R.drawable.background_red
                    )
                }

                in (201..300) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_very_unhealthy,
                        R.drawable.background_purple
                    )
                }

                else -> {}
            }
        }

        if (value >= 101) {
            this.binding.outdoorWarning.setBackgroundResource(R.drawable.ic_outdoor_warning)
        } else {
            this.binding.outdoorWarning.setBackgroundResource(R.drawable.ic_outdoor_warning_default)
        }
    }

    private fun setAirQualityImageAndBackgroundColor(
        value: Int,
        imgCloud: Int,
        imgBackground: Int
    ) {
        this.binding.textAqiResult.text = value.toString()
        this.binding.imgCloud.setBackgroundResource(imgCloud)
        this.binding.backgroundAqi.setBackgroundResource(imgBackground)
    }
}