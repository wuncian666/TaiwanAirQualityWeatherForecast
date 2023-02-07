package com.example.airqualityindex.shared.repositories

import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import io.reactivex.rxjava3.core.Single

interface IPerHourRepository {
    fun getDistinctSiteName(): Single<List<String>>

    fun getDistinctCounties(): Single<List<String>>

    fun getRecordResponse(): Single<List<PerHourRecord>>

    fun insertRecordsInDatabase(records: List<PerHourRecord>)

    fun getSiteNameByCounty(county: String): Single<List<String>>

    fun getRecordBySiteName(siteName: String?): Single<PerHourRecord>

    fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourRecord>
}