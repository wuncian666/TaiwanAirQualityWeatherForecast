package com.example.airqualityindex.shared.repository

import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import io.reactivex.rxjava3.core.Observable

interface IAirQualityRepository {
    fun requestApi(): Observable<List<PerHourAirQualityEntity>>

    fun insert(records: List<PerHourAirQualityEntity>): Observable<Boolean>

    fun getDistinctSiteName(): Observable<List<String>>

    fun getDistinctCounties(): Observable<List<String>>

    fun getSiteNameByCounty(county: String?): Observable<List<String>>

    fun getRecordBySiteName(siteName: String?): Observable<PerHourAirQualityEntity>

    fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourAirQualityEntity>
}