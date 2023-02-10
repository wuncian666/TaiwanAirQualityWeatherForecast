package com.example.airqualityindex.shared.api

import com.example.airqualityindex.shared.model.SensorThingsResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface SensorThingsService {
    @GET("Things")
    fun getThings(): Observable<SensorThingsResult>
}