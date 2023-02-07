package com.example.airqualityindex.shared.repositories

import com.example.airqualityindex.shared.models.sensorThings.SensorThings
import io.reactivex.rxjava3.core.Single

interface ISearchSensorThingRepository {

    fun getApiResponse(): Single<List<SensorThings>>
}