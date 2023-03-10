package com.example.airqualityindex.features.device.setup.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.features.device.setup.service.MqttEventListener
import com.example.airqualityindex.features.device.setup.service.MqttService
import com.example.airqualityindex.shared.model.AWSCertification
import com.example.airqualityindex.shared.repository.implement.HubRepositoryImpl
import com.example.airqualityindex.shared.util.NetworkInformation
import io.reactivex.rxjava3.core.Observable

class HubViewModel(
    private val context: Context,
    private val hubRepositoryImpl: HubRepositoryImpl
) : ViewModel() {

    private val networkInformation = NetworkInformation()

    private var mqttService: MqttService? = null

    private var addMasterDevice: Boolean = true

    fun setAddMasterDevice(addMasterDevice: Boolean) {
        this.addMasterDevice = addMasterDevice
    }

    fun getAddMasterDevice(): Boolean {
        return this.addMasterDevice
    }

    fun setMqttUseCase(listener: MqttEventListener) {
        this.mqttService = MqttService(this.context, listener)
    }

    fun setMqttListener(listener: MqttEventListener) {
        this.mqttService?.setMqttEventListener(listener)
    }

    fun requestCertificationApi(uuid: String): Observable<AWSCertification> {
        return this.hubRepositoryImpl.getApiResult(uuid)
    }

    fun findVisionHubSSID(): Boolean {
        val ssid = this.networkInformation.getWifiSSID(this.context)

        if (ssid.isEmpty()) return false

        val splitSSID = ssid.split("_")

        if (splitSSID[0] == "\"VisionSensorHub") return true

        return false
    }

    fun networkConnected(): Boolean {
        return this.networkInformation.networkConnected(this.context)
    }

    fun mqttConnected(): Boolean {
        return this.mqttService!!.mqttConnected()
    }

    fun mqttDisconnect() {
        this.mqttService?.mqttDisconnect()
    }

    fun mqttConnect() {
        this.mqttService?.mqttConnect()
    }

    fun mqttPublish(topic: String, msg: String, qos: Int = 0) {
        this.mqttService?.publish(topic, msg, qos)
    }
}