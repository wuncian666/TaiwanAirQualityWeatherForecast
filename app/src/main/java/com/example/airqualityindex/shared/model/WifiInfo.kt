package com.example.airqualityindex.shared.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WifiInfo(
    val ssid: String,
    val encrypt: String,
    var password: String
): Parcelable