package com.example.airqualityindex.shared.util

import android.content.Context
import android.content.SharedPreferences

class UserDataManager(
    private val context: Context
) {

    fun sharedPreferences(group: String): SharedPreferences {
        return this.context.getSharedPreferences(group, Context.MODE_PRIVATE)
    }
}