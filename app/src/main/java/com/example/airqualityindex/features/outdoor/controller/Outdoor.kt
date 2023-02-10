package com.example.airqualityindex.features.outdoor.controller

import android.os.Bundle
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
import com.example.airqualityindex.shared.constant.AirQualityStatus
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity
import com.example.airqualityindex.shared.util.SpannableStringService
import com.skydoves.balloon.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class Outdoor : Fragment() {
    private lateinit var binding: FragmentOutdoorBinding

    private val navCallback: NavigationViewModel = get()
    private val userViewModel: UserViewModel = get()
    private val airQualityViewModel: AirQualityViewModel = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentOutdoorBinding.inflate(inflater, container, false)
        this.binding.onClickListener = this

        this.binding.userName.text = this.userViewModel.getUserName()
        this.binding.textUserLocation.text = this.userViewModel.getLocationName()

        this.queryDatabaseThenUpdateUi()

        this.airQualityViewModel.getRecordBySiteNameLiveData(this.userViewModel.getSiteName())
            .observe(viewLifecycleOwner) {
                this.updateAirQualityUi(it)
            }

        this.weatherForecastViewModel.getRecordByLocationNameLiveData(this.userViewModel.getLocationName())
            .observe(viewLifecycleOwner) {
                this.updateTemperatureRangeText(it)
            }

        return this.binding.root
    }

    fun onCLickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navCallback.navigationCallback?.onShowMenu()
            }

            R.id.outdoor_warning -> {
                if (this.isWarningState()) {
                    this.binding.outdoorWarning.showAlignBottom(this.getBalloon())
                }
            }
        }
    }

    private fun isWarningState(): Boolean {
        return this.binding.outdoorWarning.background.constantState == ContextCompat.getDrawable(
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

    private fun queryDatabaseThenUpdateUi() {
        this.airQualityViewModel.getDataBySiteName(this.userViewModel.getSiteName())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                this.updateAirQualityUi(it)
            }
            .flatMap {
                this.weatherForecastViewModel.getRecordByLocationName(this.userViewModel.getLocationName())
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                this.updateTemperatureRangeText(it)
            }
            .subscribe()
    }

    private fun updateTemperatureRangeText(weather: WeatherForecastEntity) {
        val range =
            weather.temperatureMin + "-" + weather.temperatureMax + resources.getString(R.string.celsius_unit)
        this.binding.textTemperatureRange.text = range
    }

    private fun updateAirQualityUi(record: PerHourAirQualityEntity) {
        this.updateAirQualityText(record)
        this.updateAirQualityImage(record.aqi.toInt())
    }

    private fun updateAirQualityText(record: PerHourAirQualityEntity) {
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

    private fun updateAirQualityImage(value: Int) {
        value.let {
            when (it) {
                in (0..50) -> this.setAirQualityUi(it, AirQualityStatus.GOOD)
                in (51..100) -> this.setAirQualityUi(it, AirQualityStatus.MODERATE)
                in (101..150) -> this.setAirQualityUi(it, AirQualityStatus.UNHEALTHY_SENSITIVE)
                in (151..200) -> this.setAirQualityUi(it, AirQualityStatus.UNHEALTHY)
                in (201..300) -> this.setAirQualityUi(it, AirQualityStatus.VERY_UNHEALTHY)
            }
            if (it >= 101) {
                this.binding.outdoorWarning.setBackgroundResource(R.drawable.ic_outdoor_warning)
            } else {
                this.binding.outdoorWarning.setBackgroundResource(R.drawable.ic_outdoor_warning_default)
            }
        }
    }

    private fun setAirQualityUi(value: Int, status: AirQualityStatus) {
        this.binding.textAqiResult.text = value.toString()
        this.binding.imgCloud.setBackgroundResource(status.statusImg)
        this.binding.backgroundAqi.setBackgroundResource(status.backgroundColor)
    }
}