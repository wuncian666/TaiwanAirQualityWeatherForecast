package com.example.airqualityindex.features.search.airquality.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentSearchAirQualityBinding
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.outdoor.viewmodels.AirQualityViewModel
import com.example.airqualityindex.features.user.viewmodel.UserViewModel
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import com.example.airqualityindex.shared.units.SpannableStringService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class SearchAirQuality : Fragment() {
    private val userViewModel: UserViewModel = get()
    private val perHourAQIViewModel: AirQualityViewModel = get()
    private val navCallback: NavigationViewModel = get()

    private lateinit var binding: FragmentSearchAirQualityBinding

    private var site: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSearchAirQualityBinding.inflate(inflater, container, false)
        this.binding.searchAirQuality = this

        this.perHourAQIViewModel.getDistinctCounties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { setSpinnerCounty(it) }
            .subscribe()

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navCallback.navigationCallback?.onPressBack()
            }
            R.id.btn_search -> {
                this.searchAQI()

                if (this.binding.checkboxSetDefaultSiteName.isChecked) {
                    this.saveApplicationDefaultSiteName()
                }
            }
        }
    }

    private fun saveApplicationDefaultSiteName() {
        if (binding.spinnerSite.selectedItem.toString().isNotEmpty()) {
            val selectedSiteName = binding.spinnerSite.selectedItem.toString()
            this.userViewModel.save(UserData.GROUP, UserData.SITE_NAME, selectedSiteName)
        }
    }

    private fun setSpinnerCounty(counties: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_default_item, counties)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        val spinnerCounty = this.binding.spinnerCounty
        spinnerCounty.adapter = adapter
        spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val county = counties[position]
                this@SearchAirQuality.getSiteNameByCounty(county)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getSiteNameByCounty(county: String?) {
        val split = county?.chunked(2)
        if (split != null) {
            this.perHourAQIViewModel.getSiteNameByCounty(split[0])
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
                this@SearchAirQuality.site = sites[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun searchAQI() {
        this.site?.let {
            this.perHourAQIViewModel.getDataBySiteName(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { record ->
                    this.displayUiDetailBlock(record)
                }
                .subscribe()
        }
    }

    private fun displayUiDetailBlock(record: PerHourRecord) {
        this.binding.detailBlock.visibility = View.VISIBLE
        this.setSearchCountyAndSiteName()
        this.setUiAccordingAirQuality(record.aqi.toInt())
        this.setTextOnDetailBlock(record)
    }

    private fun setSearchCountyAndSiteName() {
        val selectedCounty = binding.spinnerCounty.selectedItem.toString()
        val selectedSiteName = binding.spinnerSite.selectedItem.toString()
        val text = "$selectedCounty $selectedSiteName"
        this.binding.textCountyWithSiteName.text = text
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
        this.binding.textAirQualityIndex.text = value.toString()
        this.binding.textAirQualityStatus.text = status
        this.binding.imgAirQuality.setBackgroundResource(imgCloud)
        this.binding.backgroundAirQualityIndex.setBackgroundResource(imgBackground)
    }

    private fun setTextOnDetailBlock(record: PerHourRecord) {
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
}