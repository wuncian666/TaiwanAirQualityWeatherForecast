package com.example.airqualityindex.shared.repositories.implement

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.database.ApplicationDatabase
import com.example.airqualityindex.shared.models.Location
import com.example.airqualityindex.shared.models.WeatherForecastStore
import com.example.airqualityindex.shared.repositories.IWeatherForecastRepository
import io.reactivex.rxjava3.core.Single

class WeatherForecastRepositoryImpl(private val context: Context) : IWeatherForecastRepository {
    private val dao = ApplicationDatabase.getInstance(this.context).getWeatherForecastDao()

    override fun getApiResponse(date: String): Single<List<Location>> {
        return ApiInstances.getWeatherInstance().getWeatherForecast(date)
            .map { it.records.location }
    }

    override fun insertWeatherForecastInDatabase(records: List<WeatherForecastStore>) {
        this.dao.insert(records)
    }

    override fun getRecordByLocationName(locationName: String?): Single<WeatherForecastStore> {
        return Single.fromCallable {
            this.dao.getRecordByLocationName(locationName)
        }
    }

    override fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastStore> {
        return this.dao.getRecordByLocationNameLiveData(locationName)
    }

    override fun turnStoreFormat(data: List<Location>): List<WeatherForecastStore> {
        val weatherForecastStores = arrayListOf<WeatherForecastStore>()
        for (location in data) {
            val weatherForecastStore = WeatherForecastStore()
            for (weatherElement in location.weatherElement) {
                weatherElement.elementName.let { elementName ->
                    when (elementName) {
                        "Wx" -> weatherForecastStore.weatherPhenomenon =
                            weatherElement.time[0].parameter.parameterName
                        "PoP" -> weatherForecastStore.probabilityOfPrecipitation =
                            weatherElement.time[0].parameter.parameterName
                        "CI" -> weatherForecastStore.comfort =
                            weatherElement.time[0].parameter.parameterName
                        "MaxT" -> weatherForecastStore.temperatureMax =
                            weatherElement.time[0].parameter.parameterName
                        "MinT" -> weatherForecastStore.temperatureMin =
                            weatherElement.time[0].parameter.parameterName
                    }
                }
            }
            weatherForecastStore.startTime = location.weatherElement[0].time[0].startTime
            weatherForecastStore.endTime = location.weatherElement[0].time[0].endTime
            weatherForecastStore.locationName = location.locationName

            weatherForecastStores.add(weatherForecastStore)
        }
        return weatherForecastStores
    }
}