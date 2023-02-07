package com.example.airqualityindex.api

import com.example.airqualityindex.model.sensorThings.SensorThingsResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SensorThingsService {
    @GET("Things")
    fun getThings(): Single<SensorThingsResult>
}