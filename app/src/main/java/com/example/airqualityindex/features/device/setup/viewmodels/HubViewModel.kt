package com.example.airqualityindex.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.features.device.setup.services.MqttEventListener
import com.example.airqualityindex.shared.models.AWSCertification
import com.example.airqualityindex.shared.repositories.HubRepositoryImpl
import com.example.airqualityindex.shared.units.JsonFormatTransform
import com.example.airqualityindex.features.device.setup.services.Mqttervice
import com.example.airqualityindex.shared.units.NetworkInformation
import io.reactivex.rxjava3.core.Single

class HubViewModel(private val context: Context, private val hubRepositoryImpl: HubRepositoryImpl) : ViewModel() {
    private val TAG = HubViewModel::class.java.simpleName
    private val apiUseCase = JsonFormatTransform()

    private val networkUseCase = NetworkInformation()

    private var mqttUserCase: Mqttervice? = null

    private var addMasterDevice: Boolean = true

    fun setAddMasterDevice(addMasterDevice: Boolean) {
        this.addMasterDevice = addMasterDevice
    }

    fun getAddMasterDevice(): Boolean {
        return this.addMasterDevice
    }

    fun setMqttUseCase(listener: MqttEventListener) {
        this.mqttUserCase = Mqttervice(context, listener)
    }

    fun setMqttListener(listener: MqttEventListener) {
        mqttUserCase?.setMqttEventListener(listener)
    }

    fun requestCertificationApi(uuid: String): Single<AWSCertification> {
        return hubRepositoryImpl.requestCertificationApi(uuid)
    }

    fun findVisionHubSSID(): Boolean {
        val ssid = this.networkUseCase.getWifiSSID(context)
        Log.d(TAG, "findVisionHubSSID: $ssid")

        if (ssid.isEmpty()) return false

        val splitSSID = ssid.split("_")

        if (splitSSID[0] == "\"VisionSensorHub") return true

        return false
    }

    fun networkConnected(): Boolean {
        return this.networkUseCase.networkConnected(context)
    }

    fun mqttConnected(): Boolean {
        return mqttUserCase!!.mqttConnected()
    }

    fun mqttDisconnect() {
        mqttUserCase?.mqttDisconnect()
    }

    fun mqttConnect() {
        mqttUserCase?.mqttConnect()
    }

    fun mqttPublish(topic: String, msg: String, qos: Int = 0) {
        mqttUserCase?.publish(topic, msg, qos)
    }
}