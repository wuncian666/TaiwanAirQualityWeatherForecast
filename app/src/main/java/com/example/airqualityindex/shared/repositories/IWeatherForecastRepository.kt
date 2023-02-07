package com.example.airqualityindex.repository

import androidx.lifecycle.LiveData
import com.example.airqualityindex.model.Location
import com.example.airqualityindex.model.WeatherForecastStore
import io.reactivex.rxjava3.core.Single

interface IWeatherForecastRepository {
    fun getApiResponse(date: String): Single<List<Location>>

    fun insertWeatherForecastInDatabase(records: List<WeatherForecastStore>)

    fun getRecordByLocationName(locationName: String?): Single<WeatherForecastStore>

    fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastStore>

    fun turnStoreFormat(data: List<Location>): List<WeatherForecastStore>
}