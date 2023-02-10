package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.model.SensorThings
import com.example.airqualityindex.shared.repository.ISearchSensorThingRepository
import io.reactivex.rxjava3.core.Observable

class SearchSensorThingsRepositoryImpl(
    private val context: Context
) : ISearchSensorThingRepository {

    override fun getApiResponse(): Observable<List<SensorThings>> {
        return ApiInstances.getSensorThingsInstance().getThings()
            .map { it.value }
    }
}