package com.example.airqualityindex.viewmodels

import androidx.lifecycle.ViewModel
import com.example.airqualityindex.model.sensorThings.SensorThings
import com.example.airqualityindex.repository.SearchSensorThingsRepositoryImpl
import io.reactivex.rxjava3.core.Single

class SearchSensorThingsViewModel(private val repository: SearchSensorThingsRepositoryImpl) :
    ViewModel() {
        fun getApiResponse(): Single<List<SensorThings>> {
            return repository.getApiResponse()
        }
}