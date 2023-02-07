package com.example.airqualityindex.drawerFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentSearchAQIBinding
import com.example.airqualityindex.model.aqiPerHour.PerHourRecord
import com.example.airqualityindex.shared.SharedPreferencesManager
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.viewmodels.PerHourAirQualityViewModel
import com.example.airqualityindex.workers.NavigationCallbackImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get

class SearchAirQualityFragment : Fragment() {
    companion object {
        private val TAG = SearchAirQualityFragment::class.java.simpleName
    }

    private val perHourAQIViewModel: PerHourAirQualityViewModel = get()
    private val sharedPreferencesManager: SharedPreferencesManager = get()
    private val navCallback: NavigationCallbackImpl = get()

    private lateinit var binding: FragmentSearchAQIBinding

    private var site: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentSearchAQIBinding.inflate(inflater, container, false)

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
                .doOnSuccess { record -> this.setTextOnDetailBlock(record) }
                .subscribe()
        }
    }


    private fun setTextOnDetailBlock(record: PerHourRecord) {
        binding.detailBlock.visibility = View.VISIBLE
        binding.textPm25.text = record.pm25
        binding.textPm10.text = record.pm10
        binding.textO3.text = record.o3
        binding.textCo.text = record.co
        binding.textSo2.text = record.so2
        binding.textNo2.text = record.no2
    }
}