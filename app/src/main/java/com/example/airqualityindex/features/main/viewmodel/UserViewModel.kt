package com.example.airqualityindex.features.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.model.CityWithDistricts
import com.example.airqualityindex.shared.model.District
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

    fun saveLocationName(location: String) {
        this.repository.saveLocationName(location)
    }

    fun getSiteName(): String? {
        return this.repository.getSiteName()
    }

    fun saveSiteName(siteName: String) {
        this.repository.saveSiteName(siteName)
    }

    fun getCityDistList(): List<CityWithDistricts> {
        return this.repository.getCityDistList()
    }

    fun getCityList(): List<String> {
        return this.repository.getCityList()
    }

    fun getDistList(distList: List<District>): List<String> {
        return this.repository.getDistList(distList)
    }
}