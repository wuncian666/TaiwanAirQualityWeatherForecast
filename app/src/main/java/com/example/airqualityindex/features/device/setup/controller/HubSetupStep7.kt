package com.example.airqualityindex.features.device.setup.controller

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.airqualityindex.features.device.setup.services.AWSMqttEventListener
import com.example.airqualityindex.databinding.FragmentHubSetupStep7Binding
import com.example.airqualityindex.shared.constant.AWSConfig
import com.example.airqualityindex.features.device.setup.viewmodels.AWSViewModel
import org.json.JSONObject
import org.koin.android.ext.android.get

class HubSetupStep7 : Fragment(), AWSMqttEventListener {
    private val TAG = HubSetupStep7::class.java.simpleName

    private val awsViewModel: AWSViewModel = get()
    
    private lateinit var binding: FragmentHubSetupStep7Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentHubSetupStep7Binding.inflate(inflater, container, false)

        this.awsViewModel.setAWSMqttUseCase(this)
        this.awsViewModel.connectAWSMqtt()

        return binding.root
    }

    override fun mqttStatusCallback(status: Int) {
        when (status) {
            0 -> Log.d(TAG, "mqttStatusCallback: ")
            1 -> {
                this.subscribeAndPublishTopic()
            }
            2 -> Log.d(TAG, "mqttStatusCallback: ")
            3 -> Log.d(TAG, "mqttStatusCallback: ")
            else -> Log.d(TAG, "mqttStatusCallback: ")
        }
    }

    override fun subscribe(message: JSONObject) {
        awsViewModel.parse()
    }

    private fun subscribeAndPublishTopic() {
        //val gatewayUuid = awsViewModel.getDeviceUuid()
        awsViewModel.subscribeTopic(AWSConfig.GATEWAY_UUID)
        awsViewModel.getAllGatewayInformation()
    }
}