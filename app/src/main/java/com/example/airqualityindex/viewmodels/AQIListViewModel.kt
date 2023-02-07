package com.example.airqualityindex.viewmodels

import androidx.lifecycle.ViewModel
import com.example.airqualityindex.model.DailyRecord
import com.example.airqualityindex.repository.AQIRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class AQIListViewModel(
    private val repository: AQIRepositoryImpl
) : ViewModel() {
    private val TAG = AQIListViewModel::class.java.simpleName

    fun getApiResponse(): Observable<List<DailyRecord>> {
        return repository.getRecordDataResponse()
    }

    fun getCity(): Observable<List<String>> {
        return repository.retrieveCityFromDatabase()
    }

    fun getRecordByCityAndDate(city: String, date: String): Observable<DailyRecord> {
        return repository.retrieveRecordFromDatabase(city, date)
    }

    fun insertDataInDatabase(data: List<DailyRecord>) {
        return repository.insertDataInDatabase(data)
    }
}