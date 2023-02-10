package com.example.airqualityindex.features.indoor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity
import com.example.airqualityindex.shared.model.Location
import com.example.airqualityindex.shared.repository.implement.WeatherForecastRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class WeatherForecastViewModel(
    private val repository: WeatherForecastRepositoryImpl
) : ViewModel() {

    fun getApiResponse(date: String): Observable<List<Location>> {
        return this.repository.getApiResponse(date)
    }

    fun insert(weatherForecast: List<WeatherForecastEntity>): Observable<Boolean> {
        return this.repository.insert(weatherForecast)
    }

    fun getRecordByLocationName(locationName: String?): Observable<WeatherForecastEntity> {
        return this.repository.getRecordByLocationName(locationName)
    }

    fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastEntity> {
        return this.repository.getRecordByLocationNameLiveData(locationName)
    }

    fun turnStoreFormat(data: List<Location>): List<WeatherForecastEntity> {
        return this.repository.turnStoreFormat(data)
    }
}