package com.example.airqualityindex.features.location.change.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentChangeLocationBinding
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import com.example.airqualityindex.features.user.viewmodel.UserViewModel
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.models.CityWithDistricts
import com.example.airqualityindex.shared.models.District
import org.koin.android.ext.android.get

class LocationChange : Fragment() {
    private val navViewModel: NavigationViewModel = get()
    private val userViewModel: UserViewModel = get()

    private lateinit var binding: FragmentChangeLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentChangeLocationBinding.inflate(inflater, container, false)

        this.setSpinnerCity(this.userViewModel.getTaiwanDistricts())

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navViewModel.navigationCallback?.onPressBack()
            }

            R.id.btn_confirm -> {
                val selectedCounty = binding.spinnerCounty.selectedItem.toString()
                this.userViewModel.save(UserData.GROUP, UserData.LOCATION, selectedCounty)
            }
        }
    }

    private fun setSpinnerCity(cityWithDistrictsList: List<CityWithDistricts>) {
        val cityList = arrayListOf<String>()
        for (city in cityWithDistrictsList) {
            cityList.add(city.name)
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_default_item, cityList)
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        this.binding.spinnerCounty.adapter = adapter
        this.binding.spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                this@LocationChange.setSpinnerDistricts(cityWithDistrictsList[position].districts)
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
        this.binding.spinnerSite.adapter = adapter
        this.binding.spinnerSite.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
}