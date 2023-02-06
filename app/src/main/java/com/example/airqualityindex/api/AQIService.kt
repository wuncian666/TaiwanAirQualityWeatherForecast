package com.example.airqualityindex.api

import com.example.airqualityindex.model.aqiDaily.DailyAQI
import com.example.airqualityindex.model.aqiPerHour.PerHourResult
import com.example.airqualityindex.shared.constant.UserData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface AQIService {
    //dataset, format, offset, limit, api_key
    @GET("aqx_p_434?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAQIData(): Observable<DailyAQI>

    @GET("aqx_p_434?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAQIDataSingle(): Single<DailyAQI>

    @GET("aqx_p_432?offset=0&api_key=" + ApiKeys.AIR_QUALITY_KEY)
    fun getAQIPerHour(): Single<PerHourResult>
}