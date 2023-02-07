package com.example.airqualityindex.model

import com.google.gson.annotations.SerializedName

data class AWSCertification(
    val rootCAName: String,
    val publicKeyName: String,
    val privateKeyName: String,
    @SerializedName("certKeyName")
    val certificationKeyName: String,
    val rootCAUrl: String,
    val publicKeyUrl: String,
    val privateKeyUrl: String,
    @SerializedName("certKeyUrl")
    val certificationKeyUrl: String,
    val iotEndpoint: String
)