package com.example.airqualityindex.shared.constant

object MqttConfig {
    const val MQTT_URL = "tcp://192.168.109.1:1883"
    const val CLIENT_ID = "USER_CLIENT"
    const val SUBSCRIBE_TOPIC = "MIDDLE_TO_USER"
    const val PUBLISH_TOPIC = "USER_TO_MIDDLE"
    const val EVENT = "EVENT"
    const val ENABLE = "1"
}