package com.example.airqualityindex.hubSetup

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentHubSetupStep6Binding
import com.example.airqualityindex.viewmodels.HubViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit

class HubSetupStep6 : Fragment() {
    private val viewModel: HubViewModel = get()

    private lateinit var binding: FragmentHubSetupStep6Binding

    private lateinit var timerCheckNetworkConnected: Disposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep6Binding.inflate(inflater, container, false)
        this.binding.btnConnectInternet.setOnClickListener {
            val wifiSetting = Intent(Settings.ACTION_WIFI_SETTINGS)
            wifiSetting.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(wifiSetting)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setTimerCheckNetworkConnected()
    }

    private fun setTimerCheckNetworkConnected() {
        this.timerCheckNetworkConnected = Observable.interval(1, 1, TimeUnit.SECONDS)
            .take(1)
            .observeOn(Schedulers.computation())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (viewModel.networkConnected()) {
                    findNavController().navigate(R.id.action_hubSetupStep6_to_hubSetupStep7)
                }
            }
            .subscribe()
    }

    override fun onPause() {
        super.onPause()
        this.timerCheckNetworkConnected.dispose()
    }
}