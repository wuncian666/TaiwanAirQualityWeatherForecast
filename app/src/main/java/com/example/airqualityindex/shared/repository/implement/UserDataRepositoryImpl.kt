package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import android.content.SharedPreferences
import com.example.airqualityindex.R
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.model.CityWithDistricts
import com.example.airqualityindex.shared.repository.IUserDataRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class UserDataRepositoryImpl(
    private val context: Context
) : IUserDataRepository {

    override fun getSharedPreferences(group: String): SharedPreferences {
        return this.context.getSharedPreferences(group, Context.MODE_PRIVATE)
    }

    override fun getUserName(): String? {
        return this.getSharedPreferences(UserData.GROUP).getString(
            UserData.USER_NAME,
            context.resources.getString(R.string.not_yet_login)
        )
    }

    override fun getLocationName(): String? {
        return this.getSharedPreferences(UserData.GROUP).getString(
            UserData.LOCATION,
            context.resources.getString(R.string.location_default)
        )
    }

    override fun getSiteName(): String? {
        return this.getSharedPreferences(UserData.GROUP).getString(
            UserData.SITE_NAME,
            context.resources.getString(R.string.site_name_default)
        )
    }

    override fun save(group: String, key: String, value: String) {
        this.getSharedPreferences(group).edit().putString(key, value).apply()
    }

    override fun getData(group: String, key: String): String? {
        return this.getSharedPreferences(group).getString(key, null)
    }

    override fun removeData(group: String, key: String) {
        return this.getSharedPreferences(group).edit().remove(key).apply()
    }

    override fun clearGroup(group: String) {
        this.getSharedPreferences(group).edit().clear().apply()
    }

    override fun getTaiwanDistricts(): List<CityWithDistricts> {
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
}