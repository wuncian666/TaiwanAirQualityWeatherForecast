package com.example.airqualityindex.features.device.setup.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentHubSetupStep3Binding

class HubSetupStep3 : Fragment() {
    private lateinit var binding: FragmentHubSetupStep3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep3Binding.inflate(inflater, container, false)
        this.binding.hubSetupStep3 = this

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.btn_next -> {
                findNavController().navigate(R.id.action_hubSetupStep3_to_hubSetupStep4)
            }
        }
    }
}