package com.example.airqualityindex.shared.repositories.implement

import android.content.Context
import android.content.SharedPreferences
import com.example.airqualityindex.R
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.repositories.IUserDataRepository

class UserDataRepositoryImpl(private val context: Context) :
    IUserDataRepository {

    override fun getSharedPreferences(group: String): SharedPreferences {
        return this.context.getSharedPreferences(group, Context.MODE_PRIVATE)
    }

    override fun getUserName(): String? {
        return getSharedPreferences(UserData.GROUP).getString(
            UserData.USER_NAME,
            context.resources.getString(R.string.not_yet_login)
        )
    }

    override fun getLocationName(): String? {
        return getSharedPreferences(UserData.GROUP).getString(
            UserData.LOCATION,
            context.resources.getString(R.string.location_default)
        )
    }

    override fun getSiteName(): String? {
        return getSharedPreferences(UserData.GROUP).getString(
            UserData.SITE_NAME,
            context.resources.getString(R.string.site_name_default)
        )
    }

    override fun saveData(group: String, key: String, value: String) {
        getSharedPreferences(group).edit().putString(key, value).apply()
    }

    override fun getData(group: String, key: String): String? {
        return getSharedPreferences(group).getString(key, null)
    }

    override fun removeData(group: String, key: String) {
        return getSharedPreferences(group).edit().remove(key).apply()
    }

    override fun clearGroup(group: String) {
        getSharedPreferences(group).edit().clear().apply()
    }
}