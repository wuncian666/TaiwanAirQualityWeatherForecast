package com.example.airqualityindex.features.device.setup.controller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.airqualityindex.R
import com.example.airqualityindex.databinding.FragmentHubSetupStep5Binding
import com.example.airqualityindex.features.device.setup.viewmodels.HubViewModel
import com.example.airqualityindex.shared.constant.AWSConfig
import com.example.airqualityindex.shared.constant.MqttConfig
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONObject
import org.koin.android.ext.android.get
import java.util.concurrent.TimeUnit

class HubSetupStep5 : Fragment() {
    private val viewModel: HubViewModel = get()

    private lateinit var binding: FragmentHubSetupStep5Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep5Binding.inflate(inflater, container, false)

        this.setupGateway()

        return this.binding.root
    }

    private fun setupGateway() {
        Observable.just(1)
            .map {
                this.viewModel.mqttPublish(MqttConfig.PUBLISH_TOPIC, this.getSetupEvent())
            }
            .delay(5, TimeUnit.SECONDS, Schedulers.io())
            .map {
                this.chooseAddMasterSlaveDevice(this.viewModel.getAddMasterDevice())
            }
            .delay(5, TimeUnit.SECONDS, Schedulers.io())
            .map {
                this.viewModel.mqttPublish(MqttConfig.PUBLISH_TOPIC, this.getWifiSetupEvent())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                this.viewModel.mqttDisconnect()
                findNavController().navigate(R.id.action_hubSetupStep5_to_hubSetupFailed)
            }
    }

    private fun getSetupEvent(): String {
        // TODO: certification and gateway uuid
        val uuid = AWSConfig.GATEWAY_UUID
        val json = JSONObject()
        json.put("EVENT", "SET_AWS_SETTING")
        json.put("ENABLE", "1")
        json.put("ROOT_CA", "")
        json.put("CLIENT_CRT", "AWSConfig.CER")
        json.put("CLIENT_KEY", AWSConfig.KEY_STORE_NAME)
        json.put("ROOT_CA_URL", "")
        json.put("PRIVATE_KEY_URL", "")
        json.put("CERT_URL", "")
        json.put("HOST_NAME", AWSConfig.ENDPOINT)
        json.put("TOPIC_AWS_TO_GW", "$uuid/subscribe")
        json.put("TOPIC_GW_TO_AWS", "$uuid/publish")
        json.put("CLIENT_ID", uuid)
        json.put("MULTI_CMD", "1")
        return json.toString()
    }

    private fun chooseAddMasterSlaveDevice(addMasterDevice: Boolean) {

    }

    private fun getMasterSlaveSetupEvent(): String {
        return ""
    }

    private fun getWifiSetupEvent(): String {
        val args: HubSetupStep5Args by navArgs()
        val json = JSONObject()
        json.put("EVENT", "WIFI_SETTING")
        json.put("SSID", args.wifiInfo.ssid)
        json.put("KEY", args.wifiInfo.password)
        json.put("ENCRYPTION", args.wifiInfo.encrypt)
        return json.toString()
    }
}