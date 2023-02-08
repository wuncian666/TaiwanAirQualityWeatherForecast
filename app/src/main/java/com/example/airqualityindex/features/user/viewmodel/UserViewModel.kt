package com.example.airqualityindex.features.user.viewmodel

import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.models.CityWithDistricts
import com.example.airqualityindex.shared.repositories.implement.UserDataRepositoryImpl

class UserViewModel(private val repository: UserDataRepositoryImpl) : ViewModel() {
    fun getUserName(): String? {
        return this.repository.getUserName()
    }

    fun getLocationName(): String? {
        return this.repository.getLocationName()
    }

    fun getSiteName(): String? {
        return this.repository.getSiteName()
    }

    fun save(group: String, key: String, value: String) {
        this.repository.save(group, key, value)
    }

    fun getTaiwanDistricts(): List<CityWithDistricts> {
        return this.repository.getTaiwanDistricts()
    }
}