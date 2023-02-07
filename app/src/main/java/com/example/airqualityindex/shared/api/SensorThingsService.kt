package com.example.airqualityindex.shared.api

import com.example.airqualityindex.shared.models.sensorThings.SensorThingsResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface SensorThingsService {
    @GET("Things")
    fun getThings(): Single<SensorThingsResult>
}