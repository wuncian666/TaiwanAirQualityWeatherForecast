package com.example.airqualityindex.hubSetup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_hubSetupStep3_to_hubSetupStep4)
        }
        return this.binding.root
    }
}