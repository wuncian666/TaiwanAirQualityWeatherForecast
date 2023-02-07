package com.example.airqualityindex.shared.repositories

import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.models.Location
import com.example.airqualityindex.shared.models.WeatherForecastStore
import io.reactivex.rxjava3.core.Single

interface IWeatherForecastRepository {
    fun getApiResponse(date: String): Single<List<Location>>

    fun insertWeatherForecastInDatabase(records: List<WeatherForecastStore>)

    fun getRecordByLocationName(locationName: String?): Single<WeatherForecastStore>

    fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastStore>

    fun turnStoreFormat(data: List<Location>): List<WeatherForecastStore>
}