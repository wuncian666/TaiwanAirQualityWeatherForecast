package com.example.airqualityindex.features.device.setup.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.features.device.setup.services.MqttEventListener
import com.example.airqualityindex.features.device.setup.services.MqttService
import com.example.airqualityindex.shared.models.AWSCertification
import com.example.airqualityindex.shared.repositories.implement.HubRepositoryImpl
import com.example.airqualityindex.shared.units.NetworkInformation
import io.reactivex.rxjava3.core.Single

class HubViewModel(private val context: Context, private val hubRepositoryImpl: HubRepositoryImpl) : ViewModel() {
    private val networkUseCase = NetworkInformation()

    private var mqttService: MqttService? = null

    private var addMasterDevice: Boolean = true

    fun setAddMasterDevice(addMasterDevice: Boolean) {
        this.addMasterDevice = addMasterDevice
    }

    fun getAddMasterDevice(): Boolean {
        return this.addMasterDevice
    }

    fun setMqttUseCase(listener: MqttEventListener) {
        this.mqttService = MqttService(context, listener)
    }

    fun setMqttListener(listener: MqttEventListener) {
        this.mqttService?.setMqttEventListener(listener)
    }

    fun requestCertificationApi(uuid: String): Single<AWSCertification> {
        return this.hubRepositoryImpl.requestCertificationApi(uuid)
    }

    fun findVisionHubSSID(): Boolean {
        val ssid = this.networkUseCase.getWifiSSID(context)

        if (ssid.isEmpty()) return false

        val splitSSID = ssid.split("_")

        if (splitSSID[0] == "\"VisionSensorHub") return true

        return false
    }

    fun networkConnected(): Boolean {
        return this.networkUseCase.networkConnected(context)
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