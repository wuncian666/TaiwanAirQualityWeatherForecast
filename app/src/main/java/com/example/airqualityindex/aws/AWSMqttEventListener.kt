package com.example.airqualityindex.aws

import org.json.JSONObject

interface AWSMqttEventListener {
    fun mqttStatusCallback(status: Int)
    fun subscribe(message: JSONObject)
}