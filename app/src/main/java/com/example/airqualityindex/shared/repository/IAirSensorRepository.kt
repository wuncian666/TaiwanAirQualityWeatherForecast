package com.example.airqualityindex.shared.repository

import com.example.airqualityindex.shared.model.SensorThings
import io.reactivex.rxjava3.core.Observable

interface IAirSensorRepository {
    fun getApiResult(): Observable<List<SensorThings>>
}