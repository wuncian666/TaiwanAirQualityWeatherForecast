package com.example.airqualityindex.model.aqiDaily

import com.google.gson.annotations.SerializedName

data class Extras(
    @SerializedName("api_key")
    val apiKey: String
)