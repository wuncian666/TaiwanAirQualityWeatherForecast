package com.example.airqualityindex.repository

import com.example.airqualityindex.model.sensorThings.SensorThings
import io.reactivex.rxjava3.core.Single

interface ISearchSensorThingRepository {

    fun getApiResponse(): Single<List<SensorThings>>
}