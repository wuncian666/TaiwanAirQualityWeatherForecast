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
import com.example.airqualityindex.databinding.FragmentChangeLocationBinding
import com.example.airqualityindex.model.CityWithDistricts
import com.example.airqualityindex.model.District
import com.example.airqualityindex.shared.SharedPreferencesManager
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.workers.NavigationCallbackImpl
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.koin.android.ext.android.get

class ChangeLocationFragment : Fragment() {
    companion object {
        private val TAG = ChangeLocationFragment::class.java.simpleName
    }

    private val navCallback: NavigationCallbackImpl = get()
    private val sharedPreferencesManager: SharedPreferencesManager = get()

    private lateinit var binding: FragmentChangeLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentChangeLocationBinding.inflate(inflater, container, false)
        this.navCallback.navigationCallback?.onInvisibleBottomNavigation()
        this.binding.openCustomDrawer.setOnClickListener {
            this.navCallback.navigationCallback?.onPressBack()
        }

        this.setSpinnerCity(this.getTaiwanDistricts())

        this.binding.btnConfirm.setOnClickListener {
            this.saveSelectedLocation()
        }

        return this.binding.root
    }

    private fun setSpinnerCity(cityWithDistrictsList: List<CityWithDistricts>) {
        val cityList = arrayListOf<String>()
        for (city in cityWithDistrictsList) {
            cityList.add(city.name)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_default_item, cityList)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        binding.spinnerCounty.adapter = adapter
        binding.spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (cityList.isEmpty()) {
                    Log.e(TAG, "onItemSelected: city list is empty!")
                } else {
                    setSpinnerDistricts(cityWithDistrictsList[position].districts)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setSpinnerDistricts(districts: List<District>) {
        val districtsList = arrayListOf<String>()
        for (district in districts) {
            districtsList.add(district.name)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_default_item, districtsList)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        binding.spinnerSite.adapter = adapter
        binding.spinnerSite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (districtsList.isEmpty()) {
                    Log.e(TAG, "onItemSelected: districts list is empty!")
                } else {
                    Log.i(TAG, "onItemSelected: " + districtsList[position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    private fun getTaiwanDistricts(): List<CityWithDistricts> {
        val taiwanDistricts: String =
            requireContext().assets.open("taiwan_districts.json").bufferedReader()
                .use { it.readText() }
        val gson = GsonBuilder().create()
        return gson.fromJson<ArrayList<CityWithDistricts>>(
            taiwanDistricts,
            object : TypeToken<ArrayList<CityWithDistricts>>() {}.type
        )
    }

    private fun saveSelectedLocation() {
        sharedPreferencesManager.saveData(
            UserData.GROUP,
            UserData.LOCATION,
            binding.spinnerCounty.selectedItem.toString()
        )
    }
}