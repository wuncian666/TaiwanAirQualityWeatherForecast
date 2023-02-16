package com.example.airqualityindex.shared.repository

import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import io.reactivex.rxjava3.core.Observable

interface IAirQualityRepository {
    fun getApiResult(): Observable<List<PerHourAirQualityEntity>>

    fun insert(records: List<PerHourAirQualityEntity>): Observable<Boolean>

    fun getSiteList(): Observable<List<String>>

    fun getCountyList(): Observable<List<String>>

    fun getSiteListByCounty(county: String?): Observable<List<String>>

    fun getRecordBySite(siteName: String?): Observable<PerHourAirQualityEntity>

    fun getLiveRecordBySite(siteName: String?): LiveData<PerHourAirQualityEntity>
}