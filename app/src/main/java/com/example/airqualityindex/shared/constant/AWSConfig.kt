package com.example.airqualityindex.shared.constant

import com.amazonaws.regions.Regions

object AWSConfig {
    const val GATEWAY_UUID = "9A6C2AB3AA"
    const val ROOT_CA = ""
    const val CLIENT_CRT =""

    const val POLICY_NAME = "ma930118_policy"
    const val COGNITO_POOL_ID =
        "us-east-1:373934462524:cert/5420b64f31df89968f78fd53d2ccf55f3d371b37757ad985305d393af6942ed8"
    const val ENDPOINT = "ajkpjvwhfm2tr-ats.iot.us-east-1.amazonaws.com"
    const val KEY_STORE_NAME = "aws_iot_keystore"
    const val KEY_STORE_PASSWORD = "password"
    const val CERTIFICATE_ID = "default"
}