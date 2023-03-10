package com.example.airqualityindex.features.device.setup.service

import android.content.Context
import android.util.Log
import com.example.airqualityindex.shared.constant.MqttConfig
import info.mqtt.android.service.Ack
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttService(private val context: Context, private var mqttEventListener: MqttEventListener?) {
    private var mqttClient: MqttAndroidClient? = null

    fun setMqttEventListener(listener: MqttEventListener) {
        this.mqttEventListener = listener
    }

    init {
        this.mqttClient = MqttAndroidClient(
            this.context,
            MqttConfig.MQTT_URL,
            MqttConfig.CLIENT_ID,
            Ack.MANUAL_ACK
        )

        this.mqttClient!!.setCallback(object : MqttCallbackExtended {
            override fun connectionLost(cause: Throwable?) {}

            @Throws(Exception::class)
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                this@MqttService.mqttEventListener?.onMessageArrived(message)
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {}

            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.d("TAG", "connectComplete: $serverURI")
            }
        })
    }

    fun mqttConnected(): Boolean {
        return this.mqttClient?.isConnected == true
    }

    fun mqttDisconnect() {
        this.mqttClient!!.disconnect()
    }

    fun mqttConnect() {
        Log.d("TAG", "mqttConnect: ")

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.userName = "root"
        mqttConnectOptions.password = "1234".toCharArray()

        this.mqttClient?.connect(mqttConnectOptions, this.context, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                subscribe(MqttConfig.SUBSCRIBE_TOPIC, 1)
                this@MqttService.mqttEventListener?.onConnected()
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                this@MqttService.mqttEventListener?.onConnectFail(exception)
            }
        })
    }

    fun subscribe(subscriptionTopic: String, qos: Int = 0) {
        try {
            this.mqttClient!!.subscribe(subscriptionTopic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("TAG", "onSuccess: $subscriptionTopic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("TAG", "onFailure: $subscriptionTopic failed!")
                }
            })
        } catch (ex: MqttException) {
            Log.e(
                "TAG",
                "subscribe: Exception whilst subscribing to topic $subscriptionTopic \n" + ex.printStackTrace()
            )
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 0) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            this.mqttClient!!.publish(topic, message.payload, qos, false)
            Log.d("TAG", "publish: $msg")
        } catch (e: MqttException) {
            Log.e("TAG", "publish: Error Publish to $topic, " + e.message)
        }
    }

    fun isConnected(): Boolean {
        return this.mqttClient!!.isConnected
    }

    fun destroy() {
        this.mqttClient!!.unregisterResources()
        this.mqttClient!!.disconnect()
    }
}