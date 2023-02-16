package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.database.ApplicationDatabase
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity
import com.example.airqualityindex.shared.model.Location
import com.example.airqualityindex.shared.repository.IWeatherRepository
import io.reactivex.rxjava3.core.Observable

class WeatherRepositoryImpl(
    private val context: Context
) : IWeatherRepository {
    private val dao = ApplicationDatabase.getInstance(this.context).weatherForecastDao()

    override fun getApiResult(date: String): Observable<List<Location>> {
        return ApiInstances.getWeatherInstance().getWeatherForecast(date)
            .map {
                it.records.location
            }
    }

    override fun insert(records: List<WeatherForecastEntity>): Observable<Boolean> {
        return Observable.fromCallable {
            this.dao.insert(records)
            return@fromCallable true
        }
    }

    override fun getRecordByLocation(locationName: String?): Observable<WeatherForecastEntity> {
        return Observable.fromCallable {
            return@fromCallable this.dao.getRecordByLocation(locationName)
        }
    }

    override fun getLiveRecordByLocation(locationName: String?): LiveData<WeatherForecastEntity> {
        return this.dao.getLiveRecordByLocation(locationName)
    }

    override fun turnStoreFormat(data: List<Location>): Observable<List<WeatherForecastEntity>> {
        return Observable.fromCallable {
            val weatherArray = arrayListOf<WeatherForecastEntity>()

            var phenomenon: String? = null
            var pop: String? = null
            var comfort: String? = null
            var tempMax: String? = null
            var tempMin: String? = null

            for (location in data) {
                for (weatherElement in location.weatherElement) {
                    weatherElement.elementName.let { elementName ->
                        when (elementName) {
                            "Wx" -> phenomenon = weatherElement.time[0].parameter.parameterName
                            "PoP" -> pop = weatherElement.time[0].parameter.parameterName
                            "CI" -> comfort = weatherElement.time[0].parameter.parameterName
                            "MaxT" -> tempMax = weatherElement.time[0].parameter.parameterName
                            "MinT" -> tempMin = weatherElement.time[0].parameter.parameterName
                        }
                    }
                }

                val weather = WeatherForecastEntity(
                    location.locationName,
                    location.weatherElement[0].time[0].startTime,
                    location.weatherElement[0].time[0].endTime,
                    phenomenon,
                    tempMax,
                    tempMin,
                    comfort,
                    pop
                )

                weatherArray.add(weather)
            }
            return@fromCallable weatherArray
        }
    }
}