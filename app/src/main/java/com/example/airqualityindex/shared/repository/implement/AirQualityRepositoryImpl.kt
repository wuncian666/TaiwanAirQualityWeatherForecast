package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.database.ApplicationDatabase
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.repository.IAirQualityRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AirQualityRepositoryImpl(
    private val context: Context
) : IAirQualityRepository {
    private val dao = ApplicationDatabase.getInstance(this.context).getPerHourRecordDao()

    override fun requestApi(): Observable<List<PerHourAirQualityEntity>> {
        return ApiInstances.getRetrofitInstance().getAirQualityPerHour()
            .map { it.records }
    }

    override fun insert(records: List<PerHourAirQualityEntity>): Observable<Boolean> {
        return Observable.fromCallable { this.dao.insert(records) }
            .flatMap { Observable.just(true) }
    }

    override fun getDistinctSiteName(): Observable<List<String>> {
        return Observable.fromCallable {
            this.dao.getDistinctSiteName()
        }
    }

    override fun getDistinctCounties(): Observable<List<String>> {
        return Observable.fromCallable {
            this.dao.getDistinctCounties()
        }
    }

    override fun getSiteNameByCounty(county: String): Observable<List<String>> {
        return Observable.fromCallable {
            this.dao.getSiteNameByCounty(county)
        }
    }

    override fun getRecordBySiteName(siteName: String?): Observable<PerHourAirQualityEntity> {
        return Observable.fromCallable {
            this.dao.getRecordBySiteName(siteName)
        }
    }

    override fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourAirQualityEntity> {
        return this.dao.getRecordBySiteNameLiveData(siteName)
    }
}