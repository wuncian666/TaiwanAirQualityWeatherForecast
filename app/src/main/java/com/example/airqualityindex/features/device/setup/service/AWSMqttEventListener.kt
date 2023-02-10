package com.example.airqualityindex.features.device.setup.service

import org.json.JSONObject

interface AWSMqttEventListener {
    fun mqttStatusCallback(status: Int)

    fun subscribe(message: JSONObject)
}