package com.example.airqualityindex.shared.api

import com.example.airqualityindex.shared.model.airquality.DailyResult
import com.example.airqualityindex.shared.model.airquality.PerHourResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface AQIService {
    //dataset, format, offset, limit, api_key
    @GET("aqx_p_434?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAirQualityDaily(): Observable<DailyResult>

    @GET("aqx_p_432?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAirQualityPerHour(): Observable<PerHourResult>
}