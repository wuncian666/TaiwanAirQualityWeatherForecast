package com.example.airqualityindex.features.user.viewmodel

import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.constant.UserData
import com.example.airqualityindex.shared.model.CityWithDistricts
import com.example.airqualityindex.shared.repository.implement.UserDataRepositoryImpl

class UserViewModel(
    private val repository: UserDataRepositoryImpl
) : ViewModel() {

    fun getUserName(): String? {
        return this.repository.getUserName()
    }

    fun getLocationName(): String? {
        return this.repository.getLocationName()
    }

    fun getSiteName(): String? {
        return this.repository.getSiteName()
    }

    fun saveLocation(location: String) {
        this.repository.save(UserData.GROUP, UserData.LOCATION, location)
    }

    fun getTaiwanDistricts(): List<CityWithDistricts> {
        return this.repository.getTaiwanDistricts()
    }
}