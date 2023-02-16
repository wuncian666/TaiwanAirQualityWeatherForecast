package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import com.example.airqualityindex.R
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.model.CityWithDistricts
import com.example.airqualityindex.shared.model.District
import com.example.airqualityindex.shared.repository.IUserDataRepository
import com.example.airqualityindex.shared.util.UserDataManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class UserDataRepositoryImpl(
    private val context: Context,
    private val manager: UserDataManager
) : IUserDataRepository {

    override fun getUserName(): String? {
        return this.manager.sharedPreferences(UserData.GROUP).getString(
            UserData.USER_NAME,
            context.resources.getString(R.string.not_yet_login)
        )
    }

    override fun getLocationName(): String? {
        return this.manager.sharedPreferences(UserData.GROUP).getString(
            UserData.LOCATION,
            context.resources.getString(R.string.location_default)
        )
    }

    override fun saveLocationName(value: String) {
        this.manager.sharedPreferences(UserData.GROUP).edit().putString(UserData.LOCATION, value)
            .apply()
    }

    override fun getSiteName(): String? {
        return this.manager.sharedPreferences(UserData.GROUP).getString(
            UserData.SITE_NAME,
            context.resources.getString(R.string.site_name_default)
        )
    }

    override fun saveSiteName(value: String) {
        this.manager.sharedPreferences(UserData.GROUP).edit().putString(UserData.SITE_NAME, value)
            .apply()
    }

    override fun getDeviceUuid(): String? {
        return this.manager.sharedPreferences(UserData.GROUP).getString(UserData.DEVICE_UUID, null)
    }

    override fun removeData(group: String, key: String) {
        return this.manager.sharedPreferences(group).edit().remove(key).apply()
    }

    override fun clearGroup(group: String) {
        this.manager.sharedPreferences(group).edit().clear().apply()
    }

    override fun getCityDistList(): List<CityWithDistricts> {
        val taiwanDistricts: String =
            this.context.assets.open("taiwan_districts.json").bufferedReader().use {
                it.readText()
            }
        val gson = GsonBuilder().create()
        return gson.fromJson<ArrayList<CityWithDistricts>>(
            taiwanDistricts,
            object : TypeToken<ArrayList<CityWithDistricts>>() {}.type
        )
    }

    override fun getCityList(): List<String> {
        val nameList = arrayListOf<String>()
        for (city in this.getCityDistList()) {
            nameList.add(city.name)
        }
        return nameList
    }

    override fun getDistList(distList: List<District>): List<String> {
        val nameList = arrayListOf<String>()
        for (dist in distList) {
            nameList.add(dist.name)
        }
        return nameList
    }
}