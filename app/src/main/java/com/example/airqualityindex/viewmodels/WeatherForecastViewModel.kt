package com.example.airqualityindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.model.Location
import com.example.airqualityindex.model.WeatherForecastStore
import com.example.airqualityindex.repository.WeatherForecastRepositoryImpl
import io.reactivex.rxjava3.core.Single

class WeatherForecastViewModel(private val repository: WeatherForecastRepositoryImpl) :
    ViewModel() {

    fun getApiResponse(date: String): Single<List<Location>> {
        return repository.getApiResponse(date)
    }

    fun insertWeatherForecastInDatabase(weatherForecastStores: List<WeatherForecastStore>) {
        repository.insertWeatherForecastInDatabase(weatherForecastStores)
    }

    fun getRecordByLocationName(locationName: String?): Single<WeatherForecastStore> {
        return repository.getRecordByLocationName(locationName)
    }

    fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastStore> {
        return repository.getRecordByLocationNameLiveData(locationName)
    }

    fun turnStoreFormat(data: List<Location>): List<WeatherForecastStore> {
        return repository.turnStoreFormat(data)
    }
}