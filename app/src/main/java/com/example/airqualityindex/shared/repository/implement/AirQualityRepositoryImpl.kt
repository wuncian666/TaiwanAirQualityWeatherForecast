package com.example.airqualityindex.shared.repository.implement

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.airqualityindex.shared.api.ApiInstances
import com.example.airqualityindex.shared.database.ApplicationDatabase
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.repository.IAirQualityRepository
import io.reactivex.rxjava3.core.Observable

class AirQualityRepositoryImpl(
    private val context: Context
) : IAirQualityRepository {
    private val dao = ApplicationDatabase.getInstance(this.context).perHourRecordDao()

    override fun getApiResult(): Observable<List<PerHourAirQualityEntity>> {
        return ApiInstances.getRetrofitInstance().getAirQualityPerHour()
            .map {
                it.records
            }
    }

    override fun insert(records: List<PerHourAirQualityEntity>): Observable<Boolean> {
        return Observable.fromCallable { this.dao.insert(records) }
            .flatMap {
                Observable.just(true)
            }
    }

    override fun getSiteList(): Observable<List<String>> {
        return Observable.fromCallable {
            this.dao.geSiteList()
        }
    }

    override fun getCountyList(): Observable<List<String>> {
        return Observable.fromCallable {
            this.dao.getCountyList()
        }
    }

    override fun getSiteListByCounty(county: String?): Observable<List<String>> {
        return Observable.fromCallable {
            this.dao.getSiteListByCounty(county)
        }
    }

    override fun getRecordBySite(siteName: String?): Observable<PerHourAirQualityEntity> {
        return Observable.fromCallable {
            this.dao.getRecordBySite(siteName)
        }
    }

    override fun getLiveRecordBySite(siteName: String?): LiveData<PerHourAirQualityEntity> {
        return this.dao.getLiveRecordBySite(siteName)
    }
}