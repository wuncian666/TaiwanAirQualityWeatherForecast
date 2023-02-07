package com.example.airqualityindex.shared.repositories.implement

import android.content.Context
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.models.sensorThings.SensorThings
import com.example.airqualityindex.shared.repositories.ISearchSensorThingRepository
import io.reactivex.rxjava3.core.Single

class SearchSensorThingsRepositoryImpl(private val context: Context) :
    ISearchSensorThingRepository {

    override fun getApiResponse(): Single<List<SensorThings>> {
        return ApiInstances.getSensorThingsInstance().getThings()
            .map { it.value }
    }
}