package com.example.airqualityindex.features.indoor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.models.Location
import com.example.airqualityindex.shared.models.WeatherForecastStore
import com.example.airqualityindex.shared.repositories.implement.WeatherForecastRepositoryImpl
import io.reactivex.rxjava3.core.Single

class WeatherForecastViewModel(private val repository: WeatherForecastRepositoryImpl) :
    ViewModel() {

    fun getApiResponse(date: String): Single<List<Location>> {
        return this.repository.getApiResponse(date)
    }

    fun insertWeatherForecastInDatabase(weatherForecastStores: List<WeatherForecastStore>) {
        this.repository.insertWeatherForecastInDatabase(weatherForecastStores)
    }

    fun getRecordByLocationName(locationName: String?): Single<WeatherForecastStore> {
        return this.repository.getRecordByLocationName(locationName)
    }

    fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastStore> {
        return this.repository.getRecordByLocationNameLiveData(locationName)
    }

    fun turnStoreFormat(data: List<Location>): List<WeatherForecastStore> {
        return this.repository.turnStoreFormat(data)
    }
}