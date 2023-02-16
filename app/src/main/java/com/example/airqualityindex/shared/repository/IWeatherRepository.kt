package com.example.airqualityindex.shared.repository

import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity
import com.example.airqualityindex.shared.model.Location
import io.reactivex.rxjava3.core.Observable

interface IWeatherRepository {
    fun getApiResult(date: String): Observable<List<Location>>

    fun insert(records: List<WeatherForecastEntity>): Observable<Boolean>

    fun getRecordByLocation(locationName: String?): Observable<WeatherForecastEntity>

    fun getLiveRecordByLocation(locationName: String?): LiveData<WeatherForecastEntity>

    fun turnStoreFormat(data: List<Location>): Observable<List<WeatherForecastEntity>>
}