package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.model.SensorThings
import com.example.airqualityindex.shared.repository.IAirSensorRepository
import io.reactivex.rxjava3.core.Observable

class SensorRepositoryImpl(
    private val context: Context
) : IAirSensorRepository {

    override fun getApiResult(): Observable<List<SensorThings>> {
        return ApiInstances.getSensorThingsInstance().getThings()
            .map {
                it.value
            }
    }
}