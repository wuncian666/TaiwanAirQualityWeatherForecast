package com.example.airqualityindex.features.search.airquality.controller

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentSearchAirQualityBinding
import com.example.airqualityindex.features.main.viewmodels.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodels.AirQualityViewModel
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.database.SharedPreferencesManager
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import com.example.airqualityindex.shared.units.SpannableStringService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class SearchAirQuality : Fragment() {
    companion object {
        private val TAG = SearchAirQuality::class.java.simpleName
    }

    private val perHourAQIViewModel: AirQualityViewModel = get()
    private val sharedPreferencesManager: SharedPreferencesManager = get()
    private val navCallback: NavigationViewModel = get()

    private lateinit var binding: FragmentSearchAirQualityBinding

    private var site: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSearchAirQualityBinding.inflate(inflater, container, false)

        this.navCallback.navigationCallback?.onInvisibleBottomNavigation()
        this.binding.openCustomDrawer.setOnClickListener {
            this.navCallback.navigationCallback?.onPressBack()
        }

        this.perHourAQIViewModel.getDistinctCounties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { setSpinnerCounty(it) }
            .subscribe()

        this.binding.btnSearch.setOnClickListener {
            this.searchAQI()

            if (this.binding.checkboxSetDefaultSiteName.isChecked) {
                this.saveApplicationDefaultSiteName()
            }
        }

        return this.binding.root
    }

    private fun saveApplicationDefaultSiteName() {
        if (binding.spinnerSite.selectedItem.toString().isNotEmpty()) {
            this.sharedPreferencesManager.saveData(
                UserData.GROUP,
                UserData.SITE_NAME,
                binding.spinnerSite.selectedItem.toString()
            )
        }
    }

    private fun setSpinnerCounty(counties: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_default_item, counties)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        val spinnerCounty = binding.spinnerCounty
        spinnerCounty.adapter = adapter
        spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (counties.isEmpty()) {
                    Log.e(TAG, "onItemSelected: counties array is empty!")
                } else {
                    val county = counties[position]
                    getSiteNameByCounty(county)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getSiteNameByCounty(county: String?) {
        val split = county?.chunked(2)
        if (split != null) {
            Log.d(TAG, "getSiteNameByCounty: " + split[0])

            perHourAQIViewModel.getSiteNameByCounty(split[0])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    this.setSpinnerSite(it)
                }
                .subscribe()
        }
    }

    private fun setSpinnerSite(sites: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_default_item, sites)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        val spinnerSite = binding.spinnerSite
        spinnerSite.adapter = adapter
        spinnerSite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                site = sites[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun searchAQI() {
        this.site?.let {
            perHourAQIViewModel.getDataBySiteName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { record ->
                    this.displayUiDetailBlock(record)
                }
                .subscribe()
        }
    }

    private fun displayUiDetailBlock(record: PerHourRecord) {
        binding.detailBlock.visibility = View.VISIBLE
        this.setSearchCountyAndSiteName(
            binding.spinnerCounty.selectedItem.toString(),
            binding.spinnerSite.selectedItem.toString()
        )
        this.setUiAccordingAirQuality(record.aqi.toInt())
        this.setTextOnDetailBlock(record)
    }

    private fun setSearchCountyAndSiteName(county: String, siteName: String) {
        val text = "$county $siteName"
        binding.textCountyWithSiteName.text = text
    }

    private fun setUiAccordingAirQuality(airQualityIndex: Int) {
        airQualityIndex.let {
            when (it) {
                in (0..50) -> {
                    this.setAirQualityStatus(
                        it,
                        resources.getString(R.string.good),
                        R.drawable.aqi_good,
                        R.drawable.background_green
                    )
                }

                in (51..100) -> {
                    this.setAirQualityStatus(
                        it,
                        resources.getString(R.string.moderate),
                        R.drawable.aqi_moderate,
                        R.drawable.background_yellow
                    )
                }

                in (101..150) -> {
                    this.setAirQualityStatus(
                        it,
                        resources.getString(R.string.unhealthy_for_sensitive_groups),
                        R.drawable.aqi_unhealthy_sensitive_groups,
                        R.drawable.background_orange
                    )
                }

                in (151..200) -> {
                    this.setAirQualityStatus(
                        it,
                        resources.getString(R.string.unhealthy),
                        R.drawable.aqi_unhealthy,
                        R.drawable.background_red
                    )
                }

                in (201..300) -> {
                    this.setAirQualityStatus(
                        it,
                        resources.getString(R.string.very_unhealthy),
                        R.drawable.aqi_very_unhealthy,
                        R.drawable.background_purple
                    )
                }
            }
        }
    }

    private fun setAirQualityStatus(
        value: Int,
        status: String,
        imgCloud: Int,
        imgBackground: Int
    ) {
        binding.textAirQualityIndex.text = value.toString()
        binding.textAirQualityStatus.text = status
        binding.imgAirQuality.setBackgroundResource(imgCloud)
        binding.backgroundAirQualityIndex.setBackgroundResource(imgBackground)
    }

    private fun setTextOnDetailBlock(record: PerHourRecord) {
        val spannableStringService = SpannableStringService()
        spannableStringService.setSuperscriptText(
            record.pm25 + resources.getString(R.string.micrometer_per_cubic_meter),
            binding.textPm25
        )
        spannableStringService.setSuperscriptText(
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
}