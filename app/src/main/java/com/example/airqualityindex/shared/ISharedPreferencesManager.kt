package com.example.airqualityindex.shared

import android.content.SharedPreferences

interface ISharedPreferencesManager {
    fun getSharedPreferences(group: String): SharedPreferences

    // for aqi application
    fun getUserName(): String?

    // for weather forecast
    fun getLocationName(): String?

    // for air quality
    fun getSiteName(): String?

    fun saveData(group: String, key: String, value:String)

    fun getData(group: String, key: String): String?

    fun removeData(group: String, key: String)

    fun clearGroup(group: String)
}