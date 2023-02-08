package com.example.airqualityindex.shared.api

import com.example.airqualityindex.shared.models.aqi.daily.DailyAQI
import com.example.airqualityindex.shared.models.aqi.hour.PerHourResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface AQIService {
    //dataset, format, offset, limit, api_key
    @GET("aqx_p_434?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAirQualityDaily(): Single<DailyAQI>

    @GET("aqx_p_432?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAirQualityPerHour(): Single<PerHourResult>
}