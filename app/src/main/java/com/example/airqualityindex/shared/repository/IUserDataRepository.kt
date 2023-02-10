package com.example.airqualityindex.shared.repository

import com.example.airqualityindex.shared.model.CityWithDistricts

interface IUserDataRepository {
    // for aqi application
    fun getUserName(): String?

    // for weather forecast
    fun getLocationName(): String?

    fun saveLocationName(value: String)

    // for air quality
    fun getSiteName(): String?

    fun saveSiteName(value: String)

    fun getDeviceUuid(): String?

    fun removeData(group: String, key: String)

    fun clearGroup(group: String)

    fun getTaiwanDistricts(): List<CityWithDistricts>
}