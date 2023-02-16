package com.example.airqualityindex.shared.repository

import com.example.airqualityindex.shared.model.CityWithDistricts
import com.example.airqualityindex.shared.model.District

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

    fun getCityDistList(): List<CityWithDistricts>

    fun getCityList(): List<String>

    fun getDistList(distList: List<District>): List<String>
}