package com.example.airqualityindex.features.device.setup.service

import org.eclipse.paho.client.mqttv3.MqttMessage

interface MqttEventListener {
    fun onConnectFail(error: Throwable?)

    fun onConnected()

    fun onMessageArrived(message: MqttMessage?)
}