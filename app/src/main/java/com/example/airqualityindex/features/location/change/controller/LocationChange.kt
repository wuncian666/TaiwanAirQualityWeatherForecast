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
import com.example.airqualityindex.features.main.viewmodel.UserViewModel
import com.example.airqualityindex.shared.model.District
import com.example.airqualityindex.shared.util.ToastViewUtil
import org.koin.android.ext.android.get

class LocationChange : Fragment() {
    private val navViewModel: NavigationViewModel = get()
    private val userViewModel: UserViewModel = get()
    private val toastViewUtil: ToastViewUtil = get()

    private lateinit var binding: FragmentChangeLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentChangeLocationBinding.inflate(inflater, container, false)
        this.binding.onClickListener = this

        this.setSpinnerCity()

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navViewModel.navigationCallback?.onPressBack()
            }

            R.id.btn_confirm -> {
                val selectedCounty = this.binding.spinnerCounty.selectedItem.toString()
                this.userViewModel.saveLocationName(selectedCounty)
                this.toastViewUtil.getToastView(this.resources.getString(R.string.location_change_success))
            }
        }
    }

    private fun setSpinnerCity() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_default_item,
            this.userViewModel.getCityList()
        )
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        this.binding.spinnerCounty.adapter = adapter
        this.binding.spinnerCounty.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    this@LocationChange.setSpinnerDistricts(
                        this@LocationChange.userViewModel.getCityDistList()[position].districts
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setSpinnerDistricts(distList: List<District>) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_default_item,
            this.userViewModel.getDistList(distList)
        )
        adapter.setDropDownViewResource(R.layout.spinner_selected_item)
        this.binding.spinnerSite.adapter = adapter
        this.binding.spinnerSite.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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