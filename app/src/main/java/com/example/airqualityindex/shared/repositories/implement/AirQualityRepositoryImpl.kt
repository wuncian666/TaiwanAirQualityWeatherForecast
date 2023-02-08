package com.example.airqualityindex.shared.repositories.implement

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.database.ApplicationDatabase
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord
import com.example.airqualityindex.shared.repositories.IAirQualityRepository
import io.reactivex.rxjava3.core.Single

class AirQualityRepositoryImpl(private val context: Context) : IAirQualityRepository {
    private val dao = ApplicationDatabase.getInstance(this.context).getPerHourRecordDao()
    override fun requestApi(): Single<List<PerHourRecord>> {
        return ApiInstances.getRetrofitInstance().getAirQualityPerHour()
            .map { it.records }
    }

    override fun insert(records: List<PerHourRecord>) {
        this.dao.insert(records)
    }

    override fun getDistinctSiteName(): Single<List<String>> {
        return Single.fromCallable {
            this.dao.getDistinctSiteName()
        }
    }

    override fun getDistinctCounties(): Single<List<String>> {
        return Single.fromCallable {
            this.dao.getDistinctCounties()
        }
    }

    override fun getSiteNameByCounty(county: String): Single<List<String>> {
        return Single.fromCallable {
            this.dao.getSiteNameByCounty(county)
        }
    }

    override fun getRecordBySiteName(siteName: String?): Single<PerHourRecord> {
        return Single.fromCallable {
            this.dao.getRecordBySiteName(siteName)
        }
    }

    override fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourRecord> {
        return this.dao.getRecordBySiteNameLiveData(siteName)
    }
}