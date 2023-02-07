package com.example.airqualityindex.repository

import com.example.airqualityindex.model.DailyRecord
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IAQIRepository {
    fun getRecordDataResponse(): Observable<List<DailyRecord>>

    fun getRecordData(): Single<List<DailyRecord>>

    fun insertDataInDatabase(record: List<DailyRecord>)

    fun retrieveRecordFromDatabase(): Observable<List<DailyRecord>>

    fun retrieveCityFromDatabase(): Observable<List<String>>

    fun retrieveRecordFromDatabase(city: String, date: String): Observable<DailyRecord>
}