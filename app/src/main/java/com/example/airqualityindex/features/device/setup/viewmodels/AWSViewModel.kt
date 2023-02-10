package com.example.airqualityindex.features.device.setup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.features.device.setup.services.AWSMqttEventListener
import com.example.airqualityindex.features.device.setup.services.AWSMqttService
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.repository.implement.UserDataRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class AWSViewModel(
    private val context: Context,
    private val userDataRepositoryImpl: UserDataRepositoryImpl
) : ViewModel() {
    private var mqttService: AWSMqttService? = null

    fun setAWSMqttUseCase(listener: AWSMqttEventListener) {
        this.mqttService = AWSMqttService(listener)
    }

    fun connectAWSMqtt() {
        val keyStorePath = this.context.filesDir.path
        this.mqttService?.initAWSMqtt()
        this.mqttService?.connectMqtt(this.context, keyStorePath)
    }

    fun subscribeTopic(gatewayUuid: String?) {
        this.mqttService?.subscribeTopic(gatewayUuid)
    }

    fun getAllGatewayInformation() {
        // TODO: get node list, get fail node list ...
    }

    fun parse(): Observable<Int> {
        return Observable.just(1)
        // TODO: do something
    }

    fun getDeviceUuid(): String? {
        return this.userDataRepositoryImpl.getDeviceUuid()
    }
}