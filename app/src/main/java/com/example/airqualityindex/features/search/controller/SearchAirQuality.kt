package com.example.airqualityindex.features.search.controller

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
import com.example.airqualityindex.features.main.viewmodel.UserViewModel
import com.example.airqualityindex.features.outdoor.viewmodel.AirQualityViewModel
import com.example.airqualityindex.shared.constant.AirQualityStatus
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.util.SpannableStringService
import com.example.airqualityindex.shared.util.ToastViewUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class SearchAirQuality : Fragment() {
    private lateinit var binding: FragmentSearchAirQualityBinding

    private val userViewModel: UserViewModel = get()
    private val airQualityViewModel: AirQualityViewModel = get()
    private val navCallback: NavigationViewModel = get()
    private val toastViewUtil: ToastViewUtil = get()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSearchAirQualityBinding.inflate(inflater, container, false)
        this.binding.onClickListener = this

        this.airQualityViewModel.getCountyList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                this.setSpinnerCounty(it)
            }
            .subscribe()

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navCallback.navigationCallback?.onPressBack()
            }
            R.id.btn_search -> {
                this.searchAirQuality()

                if (this.binding.checkboxSetDefaultSiteName.isChecked) {
                    this.saveDefaultSiteName()
                }
            }
        }
    }
    
    private fun saveDefaultSiteName() {
        if (this.binding.spinnerSiteName.selectedItem.toString().isNotEmpty()) {
            val selected = this.binding.spinnerSiteName.selectedItem.toString()
            this.userViewModel.saveSiteName(selected)
            this.toastViewUtil.getToastView(this.resources.getString(R.string.site_name_change_success))
        }
    }

    private fun setSpinnerCounty(counties: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_default_item, counties)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        val spinner = this.binding.spinnerCounty
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        this.airQualityViewModel.getSiteListByCounty(county)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                this.setSpinnerSiteName(it)
            }
            .subscribe()
    }

    private fun setSpinnerSiteName(sites: List<String>) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.spinner_default_item, sites)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        val spinner = this.binding.spinnerSiteName
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun searchAirQuality() {
        this.binding.spinnerSiteName.selectedItem.toString().let {
            this.airQualityViewModel.getDataBySite(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { record ->
                    this.displayUiDetailBlock(record)
                }
                .subscribe()
        }
    }

    private fun displayUiDetailBlock(record: PerHourAirQualityEntity) {
        this.binding.detailBlock.visibility = View.VISIBLE
        this.updateDetailBlockImg(record.aqi.toInt())
        this.updateDetailBlockText(record)
    }

    private fun updateDetailBlockImg(airQualityIndex: Int) {
        airQualityIndex.let {
            when (it) {
                in (0..50) -> this.setAirQualityUi(it, AirQualityStatus.GOOD)
                in (51..100) -> this.setAirQualityUi(it, AirQualityStatus.MODERATE)
                in (101..150) -> this.setAirQualityUi(it, AirQualityStatus.UNHEALTHY_SENSITIVE)
                in (151..200) -> this.setAirQualityUi(it, AirQualityStatus.UNHEALTHY)
                in (201..300) -> this.setAirQualityUi(it, AirQualityStatus.VERY_UNHEALTHY)
            }
            this.binding.textAirQualityIndex.text = airQualityIndex.toString()
        }
    }

    private fun setAirQualityUi(value: Int, status: AirQualityStatus) {
        this.binding.textAirQualityIndex.text = value.toString()
        this.binding.textAirQualityStatus.text = resources.getString(status.statusDescription)
        this.binding.backgroundAirQualityIndex.setBackgroundResource(status.backgroundColor)
        this.binding.imgAirQuality.setBackgroundResource(status.statusImg)
    }

    private fun updateDetailBlockText(record: PerHourAirQualityEntity) {
        val spannable = SpannableStringService()
        spannable.setSuperscriptText(
            "${record.pm25}${resources.getString(R.string.micrometer_per_cubic_meter)}",
            this.binding.textPm25
        )
        spannable.setSuperscriptText(
            "${record.pm10}${resources.getString(R.string.micrometer_per_cubic_meter)}",
            this.binding.textPm10
        )

        val selectedCounty = this.binding.spinnerCounty.selectedItem.toString()
        val selectedSiteName = this.binding.spinnerSiteName.selectedItem.toString()
        val text = "$selectedCounty $selectedSiteName"
        val o3 = "${record.o3}${resources.getString(R.string.parts_per_billion)}"
        val co = "${record.co}${resources.getString(R.string.parts_per_million)}"
        val so2 = "${record.so2}${resources.getString(R.string.parts_per_billion)}"
        val no = "${record.no2}${resources.getString(R.string.parts_per_billion)}"

        this.binding.textCountyWithSiteName.text = text
        this.binding.textO3.text = o3
        this.binding.textCo.text = co
        this.binding.textSo2.text = so2
        this.binding.textNo2.text = no
    }
}