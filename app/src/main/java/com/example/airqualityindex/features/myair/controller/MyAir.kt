package com.example.airqualityindex.features.myair.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentMyAirBinding
import com.example.airqualityindex.features.main.viewmodel.NavigationViewModel
import org.koin.android.ext.android.get

class MyAir : Fragment() {
    private val navCallback: NavigationViewModel = get()
    private lateinit var binding: FragmentMyAirBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentMyAirBinding.inflate(inflater, container, false)

        return this.binding.root
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.open_custom_drawer -> {
                this.navCallback.navigationCallback?.onPressBack()
            }
        }
    }
}