package com.example.airqualityindex.features.outdoor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.repository.implement.AirQualityRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class AirQualityViewModel(
    private val repository: AirQualityRepositoryImpl
) : ViewModel() {

    fun requestApi(): Observable<List<PerHourAirQualityEntity>> {
        return this.repository.getApiResult()
    }

    fun insert(records: List<PerHourAirQualityEntity>): Observable<Boolean> {
        return this.repository.insert(records)
    }

    fun getCountyList(): Observable<List<String>> {
        return this.repository.getCountyList()
    }

    fun getSiteListByCounty(county: String?): Observable<List<String>> {
        return this.repository.getSiteListByCounty(county)
    }

    fun getDataBySite(siteName: String?): Observable<PerHourAirQualityEntity> {
        return this.repository.getRecordBySite(siteName)
    }

    fun getLiveRecordBySiteName(siteName: String?): LiveData<PerHourAirQualityEntity> {
        return this.repository.getLiveRecordBySite(siteName)
    }
}