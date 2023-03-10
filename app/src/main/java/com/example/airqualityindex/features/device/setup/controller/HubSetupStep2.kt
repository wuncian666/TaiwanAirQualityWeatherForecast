package com.example.airqualityindex.features.device.setup.controller

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentHubSetupStep2Binding
import com.example.airqualityindex.features.device.setup.viewmodel.HubViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit

class HubSetupStep2 : Fragment() {
    private val viewModel: HubViewModel = get()

    private lateinit var binding: FragmentHubSetupStep2Binding
    private lateinit var timerFindVisionHubSSID: Disposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep2Binding.inflate(inflater, container, false)
        this.binding.onClickListener = this

        return this.binding.root
    }

    fun onClickListener(view: View) {
        when (view.id) {
            R.id.btn_goto_settings -> {
                val intentWifiSettings = Intent(Settings.ACTION_WIFI_SETTINGS)
                intentWifiSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intentWifiSettings)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        this.timerFindVisionHubSSID = Observable.interval(1000, 1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (this.viewModel.findVisionHubSSID()) {
                    this.timerFindVisionHubSSID.dispose()
                    findNavController().navigate(R.id.action_hubSetupStep2_to_hubSetupStep3)
                }
            }
            .subscribe()
    }

    override fun onPause() {
        super.onPause()
        this.timerFindVisionHubSSID.dispose()
    }
}