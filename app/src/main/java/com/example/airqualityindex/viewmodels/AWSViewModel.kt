package com.example.airqualityindex.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.aws.AWSMqttEventListener
import com.example.airqualityindex.repository.UserDataRepositoryImpl
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.aws.AWSMqttService
import io.reactivex.rxjava3.core.Observable

class AWSViewModel(
    private val context: Context,
    private val userDataRepositoryImpl: UserDataRepositoryImpl
) : ViewModel() {
    private val TAG = AWSViewModel::class.java.simpleName

    private var awsMqttUseCase: AWSMqttService? = null

    fun setAWSMqttUseCase(listener: AWSMqttEventListener) {
        this.awsMqttUseCase = AWSMqttService(listener)
    }

    fun connectAWSMqtt() {
        val keyStorePath = this.context.filesDir.path
        awsMqttUseCase?.initAWSMqtt()
        awsMqttUseCase?.connectMqtt(context, keyStorePath)
    }

    fun subscribeTopic(gatewayUuid: String?) {
        awsMqttUseCase?.subscribeTopic(gatewayUuid)
    }

    fun getAllGatewayInformation() {
        // TODO: get node list, get fail node list ...
    }

    fun parse(): Observable<Int> {
        return Observable.just(1)
        // TODO: do something
    }

    fun getDeviceUuid(): String? {
        return this.userDataRepositoryImpl.getData(UserData.GROUP, UserData.DEVICE_UUID)
    }
}