package com.example.airqualityindex.shared.models.aqiDaily

import com.google.gson.annotations.SerializedName

data class Extras(
    @SerializedName("api_key")
    val apiKey: String
)