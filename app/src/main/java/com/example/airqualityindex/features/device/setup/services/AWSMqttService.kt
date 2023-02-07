package com.example.airqualityindex.aws

import android.content.Context
import android.util.Log
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.iot.AWSIotKeystoreHelper
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.iot.AWSIotClient
import com.amazonaws.services.iot.model.AttachPrincipalPolicyRequest
import com.amazonaws.services.iot.model.CreateKeysAndCertificateRequest
import com.example.airqualityindex.shared.constant.AWSConfig
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.util.UUID

class AWSMqttService(private var awsMqttEventListener: AWSMqttEventListener?) {
    private val TAG = AWSMqttService::class.java.simpleName

    private var awsMqttManager: AWSIotMqttManager? = null

    private var awsMqttConnected = false

    fun initAWSMqtt() {
        this.awsMqttManager = AWSIotMqttManager(
            UUID.randomUUID().toString(),
            AWSConfig.ENDPOINT
        )
    }

    fun connectMqtt(context: Context, keyStorePath: String) {
        this.awsMqttManager?.connect(getKeyStore(keyStorePath, getAWSIotClient(context)))
        { awsIotMqttClientStatus: AWSIotMqttClientStatus, _: Throwable ->
            this.awsMqttConnected = awsIotMqttClientStatus == AWSIotMqttClientStatus.Connected
            this.awsMqttEventListener?.mqttStatusCallback(awsIotMqttClientStatus.ordinal)
        }
    }

    private fun getKeyStore(keyStorePath: String, awsIotClient: AWSIotClient): KeyStore {
        if (keyStoreAndKeyAliasNotPresent(keyStorePath)) {
            val request = CreateKeysAndCertificateRequest()
            request.isSetAsActive = true
            val certificate = awsIotClient.createKeysAndCertificate(request)
            AWSIotKeystoreHelper.saveCertificateAndPrivateKey(
                AWSConfig.CERTIFICATE_ID,
                certificate.certificatePem,
                certificate.keyPair.privateKey,
                keyStorePath,
                AWSConfig.KEY_STORE_NAME,
                AWSConfig.KEY_STORE_PASSWORD
            )
            val attachPrincipalPolicyRequest = AttachPrincipalPolicyRequest()
            attachPrincipalPolicyRequest.policyName = AWSConfig.POLICY_NAME
            attachPrincipalPolicyRequest.principal = certificate.certificateArn

            awsIotClient.attachPrincipalPolicy(attachPrincipalPolicyRequest)
        }

        return AWSIotKeystoreHelper.getIotKeystore(
            AWSConfig.CERTIFICATE_ID,
            keyStorePath,
            AWSConfig.KEY_STORE_NAME,
            AWSConfig.KEY_STORE_PASSWORD
        )
    }

    private fun keyStoreAndKeyAliasNotPresent(keyStorePath: String): Boolean {
        val keyStoreNotPresent =
            !AWSIotKeystoreHelper.isKeystorePresent(keyStorePath, AWSConfig.KEY_STORE_NAME)
        val keyAliasNotPresent = !AWSIotKeystoreHelper.keystoreContainsAlias(
            AWSConfig.CERTIFICATE_ID,
            keyStorePath,
            AWSConfig.KEY_STORE_NAME,
            AWSConfig.KEY_STORE_PASSWORD
        )
        return keyStoreNotPresent || keyAliasNotPresent
    }

    private fun getAWSIotClient(context: Context): AWSIotClient {
        val credentialsProvider = CognitoCachingCredentialsProvider(
            context,
            AWSConfig.COGNITO_POOL_ID,
            Regions.US_EAST_1
        )

        val region = Region.getRegion(Regions.US_EAST_1)
        val awsIotClient = AWSIotClient(credentialsProvider)
        awsIotClient.setRegion(region)

        return awsIotClient
    }

    fun subscribeTopic(gatewayUuid: String?) {
        this.awsMqttManager!!.subscribeToTopic(
            "${gatewayUuid}/publish",
            AWSIotMqttQos.QOS1
        ) { _: String?, bytesMessage: ByteArray? ->
            val message = String(bytesMessage!!, StandardCharsets.UTF_8)
            val jsonMessage = JSONObject(message)
            awsMqttEventListener?.subscribe(jsonMessage)
        }
    }

    fun publish(gatewayUuid: String?, command: String, params: JSONObject?) {
        val newParams = JSONObject()
        if (params == null) {
            newParams.put("EVENT", command)
            if (this.awsMqttConnected) {
                Log.d(TAG, "publish: $newParams")
                this.awsMqttManager!!.publishString(
                    "$newParams",
                    "$gatewayUuid/subscribe",
                    AWSIotMqttQos.QOS1
                )
            }
        }
    }
}