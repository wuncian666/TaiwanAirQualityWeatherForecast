package com.example.airqualityindex.features.outdoor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import com.example.airqualityindex.shared.repositories.implement.PerHourAirQualityRepositoryImpl
import io.reactivex.rxjava3.core.Single

class AirQualityViewModel(
    private val repository: PerHourAirQualityRepositoryImpl
) : ViewModel() {
    fun getRecordResponse(): Single<List<PerHourRecord>> {
        return this.repository.getRecordResponse()
    }

    fun insertRecordsInDatabase(records: List<PerHourRecord>) {
        this.repository.insertRecordsInDatabase(records)
    }

    fun getDistinctCounties(): Single<List<String>> {
        return this.repository.getDistinctCounties()
    }

    fun getSiteNameByCounty(county: String): Single<List<String>> {
        return this.repository.getSiteNameByCounty(county)
    }

    fun getDataBySiteName(siteName: String?): Single<PerHourRecord> {
        return this.repository.getRecordBySiteName(siteName)
    }

    fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourRecord> {
        return this.repository.getRecordBySiteNameLiveData(siteName)
    }
}