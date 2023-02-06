package com.example.airqualityindex.workers

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentOutdoorBinding
import com.example.airqualityindex.model.aqiPerHour.PerHourRecord
import com.example.airqualityindex.shared.SharedPreferencesManager
import com.example.airqualityindex.viewmodels.PerHourAirQualityViewModel
import com.example.airqualityindex.viewmodels.WeatherForecastViewModel
import com.skydoves.balloon.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class OutdoorFragment : Fragment() {
    companion object {
        private val TAG = OutdoorFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentOutdoorBinding

    private val navCallback: NavigationCallbackImpl = get()
    private val perHourAQIViewModel: PerHourAirQualityViewModel = get()
    private val weatherForecastViewModel: WeatherForecastViewModel = get()
    private val sharedPreferencesManager: SharedPreferencesManager = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentOutdoorBinding.inflate(inflater, container, false)

        this.binding.openCustomDrawer.setOnClickListener {
            this.navCallback.navigationCallback?.onShowMenu()
        }

        this.binding.outdoorWarning.setOnClickListener {
            if (isWarningState()) {
                binding.outdoorWarning.showAlignBottom(this.getBalloon())
            }
        }

        this.binding.userName.text = sharedPreferencesManager.getUserName()
        this.binding.textUserLocation.text = sharedPreferencesManager.getLocationName()

        this.queryDatabaseThenUpdateUI()

        perHourAQIViewModel.getRecordBySiteNameLiveData(sharedPreferencesManager.getSiteName())
            .observe(viewLifecycleOwner) {
                Log.d(TAG, "onCreateView: observer on change")
                updateAirQualityUi(it)
            }

        weatherForecastViewModel.getRecordByLocationNameLiveData(sharedPreferencesManager.getLocationName())
            .observe(viewLifecycleOwner) {
                setTemperatureRangeText(it.temperatureMin, it.temperatureMax)
            }

        return this.binding.root
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
        perHourAQIViewModel.getDataBySiteName(sharedPreferencesManager.getSiteName())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                updateAirQualityUi(it)
            }
            .flatMap {
                weatherForecastViewModel.getRecordByLocationName(sharedPreferencesManager.getLocationName())
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                setTemperatureRangeText(it.temperatureMin, it.temperatureMax)
            }
            .subscribe({}, { Log.e(TAG, "queryDatabaseThenUpdateUI: $it.message") })
    }

    private fun updateAirQualityUi(record: PerHourRecord) {
        this.updateAirQualityText(record)
        this.updateAirQualityIndexImage(record.aqi.toInt())
    }

    private fun setTemperatureRangeText(temperatureMin: String, temperatureMax: String) {
        val text =
            temperatureMin + "-" + temperatureMax + resources.getString(R.string.celsius_unit)
        binding.textTemperatureRange.text = text
    }

    private fun updateAirQualityText(record: PerHourRecord) {
        setSuperscriptText(
            record.pm25 + resources.getString(R.string.micrometer_per_cubic_meter),
            binding.textPm25
        )
        setSuperscriptText(
            record.pm10 + resources.getString(R.string.micrometer_per_cubic_meter),
            binding.textPm10
        )

        val o3withUnit = record.o3 + resources.getString(R.string.parts_per_billion)
        binding.textO3.text = o3withUnit
        val coWithUnit = record.co + resources.getString(R.string.parts_per_million)
        binding.textCo.text = coWithUnit
        val so2WithUnit = record.so2 + resources.getString(R.string.parts_per_billion)
        binding.textSo2.text = so2WithUnit
        val no2WithUnit = record.no2 + resources.getString(R.string.parts_per_billion)
        binding.textNo2.text = no2WithUnit
    }

    private fun setSuperscriptText(string: String, textView: TextView) {
        val spannableStringBuilder = SpannableStringBuilder(string)

        val superscriptSpan = SuperscriptSpan()
        spannableStringBuilder.setSpan(
            superscriptSpan, string.lastIndex, string.lastIndex + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val relativeSizeSpan = RelativeSizeSpan(.5f)
        spannableStringBuilder.setSpan(
            relativeSizeSpan, string.lastIndex, string.lastIndex + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textView.text = spannableStringBuilder
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
                        R.drawable.aqi_normal,
                        R.drawable.background_yellow
                    )
                }

                in (101..150) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_unhealthy_sensitive,
                        R.drawable.background_orange
                    )
                }

                in (151..200) -> {
                    setAirQualityImageAndBackgroundColor(
                        it,
                        R.drawable.aqi_unhealthy_all,
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

                else -> {
                    Log.d(TAG, "setText: out of range")
                }
            }
        }

        if (value >= 101) {
            binding.outdoorWarning.setBackgroundResource(R.drawable.ic_outdoor_warning)
        } else {
            binding.outdoorWarning.setBackgroundResource(R.drawable.ic_outdoor_warning_default)
        }
    }

    private fun setAirQualityImageAndBackgroundColor(
        value: Int,
        imgCloud: Int,
        imgBackground: Int
    ) {
        binding.textAqiResult.text = value.toString()
        binding.imgCloud.setBackgroundResource(imgCloud)
        binding.backgroundAqi.setBackgroundResource(imgBackground)
    }
}