package com.example.airqualityindex.shared.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.database.ApplicationDatabase
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import io.reactivex.rxjava3.core.Single

class PerHourAirQualityRepositoryImpl(private val context: Context) : IPerHourRepository {
    companion object {
        private val TAG = PerHourAirQualityRepositoryImpl::class.java.simpleName
    }

    private val dao = ApplicationDatabase.getInstance(this.context).getPerHourRecordDao()
    override fun getDistinctSiteName(): Single<List<String>> {
        return Single.fromCallable {
            dao.getDistinctSiteName()
        }
    }

    override fun getDistinctCounties(): Single<List<String>> {
        return Single.fromCallable {
            dao.getDistinctCounties()
        }
    }

    override fun getRecordResponse(): Single<List<PerHourRecord>> {
        return ApiInstances.getRetrofitInstance().getAQIPerHour()
            .map { it.records }
    }

    override fun insertRecordsInDatabase(records: List<PerHourRecord>) {
        dao.insert(records)
    }

    override fun getSiteNameByCounty(county: String): Single<List<String>> {
        return Single.fromCallable {
            dao.getSiteNameByCounty(county)
        }
    }

    override fun getRecordBySiteName(siteName: String?): Single<PerHourRecord> {
        return Single.fromCallable{
            dao.getRecordBySiteName(siteName)
        }
    }

    override fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourRecord> {
        return dao.getRecordBySiteNameLiveData(siteName)
    }
}