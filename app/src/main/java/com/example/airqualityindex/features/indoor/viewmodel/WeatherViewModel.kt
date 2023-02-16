package com.example.airqualityindex.features.indoor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity
import com.example.airqualityindex.shared.model.Location
import com.example.airqualityindex.shared.repository.implement.WeatherRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class WeatherViewModel(
    private val repository: WeatherRepositoryImpl
) : ViewModel() {

    fun getApiResponse(date: String): Observable<List<Location>> {
        return this.repository.getApiResult(date)
    }

    fun insert(weatherForecast: List<WeatherForecastEntity>): Observable<Boolean> {
        return this.repository.insert(weatherForecast)
    }

    fun getRecordByLocation(locationName: String?): Observable<WeatherForecastEntity> {
        return this.repository.getRecordByLocation(locationName)
    }

    fun getLiveRecordByLocation(locationName: String?): LiveData<WeatherForecastEntity> {
        return this.repository.getLiveRecordByLocation(locationName)
    }

    fun turnStoreFormat(data: List<Location>): Observable<List<WeatherForecastEntity>> {
        return this.repository.turnStoreFormat(data)
    }
}