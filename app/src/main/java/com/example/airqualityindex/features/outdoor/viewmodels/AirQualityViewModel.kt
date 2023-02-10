package com.example.airqualityindex.features.outdoor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.repository.implement.AirQualityRepositoryImpl
import io.reactivex.rxjava3.core.Observable

class AirQualityViewModel(
    private val repository: AirQualityRepositoryImpl
) : ViewModel() {

    fun requestApi(): Observable<List<PerHourAirQualityEntity>> {
        return this.repository.requestApi()
    }

    fun insert(records: List<PerHourAirQualityEntity>): Observable<Boolean> {
        return this.repository.insert(records)
    }

    fun getDistinctCounties(): Observable<List<String>> {
        return this.repository.getDistinctCounties()
    }

    fun getSiteNameByCounty(county: String): Observable<List<String>> {
        return this.repository.getSiteNameByCounty(county)
    }

    fun getDataBySiteName(siteName: String?): Observable<PerHourAirQualityEntity> {
        return this.repository.getRecordBySiteName(siteName)
    }

    fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourAirQualityEntity> {
        return this.repository.getRecordBySiteNameLiveData(siteName)
    }
}