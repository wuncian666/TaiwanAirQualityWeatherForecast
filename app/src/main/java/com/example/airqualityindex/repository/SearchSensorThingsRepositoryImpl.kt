package com.example.airqualityindex.repository

import android.content.Context
import com.example.airqualityindex.api.ApiInstances
import com.example.airqualityindex.model.sensorThings.SensorThings
import io.reactivex.rxjava3.core.Single

class SearchSensorThingsRepositoryImpl(private val context: Context) :
    ISearchSensorThingRepository {

    override fun getApiResponse(): Single<List<SensorThings>> {
        return ApiInstances.getSensorThingsInstance().getThings()
            .map { it.value }
    }
}