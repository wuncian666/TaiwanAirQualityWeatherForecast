package com.example.airqualityindex.api

import com.example.airqualityindex.shared.models.WeatherForecastResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {
    @GET("rest/datastore/F-C0032-001?Authorization=" + ApiKeys.WEATHER_KEY)
    fun getWeatherForecast(@Query("date") date: String): Single<WeatherForecastResult>
}