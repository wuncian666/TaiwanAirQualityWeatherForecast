package com.example.airqualityindex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airqualityindex.model.aqiPerHour.PerHourRecord
import com.example.airqualityindex.repository.PerHourAirQualityRepositoryImpl
import io.reactivex.rxjava3.core.Single

class PerHourAirQualityViewModel(
    private val repository: PerHourAirQualityRepositoryImpl
) : ViewModel() {
    fun getRecordResponse(): Single<List<PerHourRecord>> {
        return repository.getRecordResponse()
    }

    fun insertRecordsInDatabase(records: List<PerHourRecord>) {
        repository.insertRecordsInDatabase(records)
    }

    fun getDistinctCounties(): Single<List<String>> {
        return repository.getDistinctCounties()
    }

    fun getSiteNameByCounty(county: String): Single<List<String>> {
        return repository.getSiteNameByCounty(county)
    }

    fun getDataBySiteName(siteName: String?): Single<PerHourRecord> {
        return repository.getRecordBySiteName(siteName)
    }

    fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourRecord> {
        return repository.getRecordBySiteNameLiveData(siteName)
    }
}