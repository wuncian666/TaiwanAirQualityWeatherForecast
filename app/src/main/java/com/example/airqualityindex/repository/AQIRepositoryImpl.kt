package com.example.airqualityindex.repository

import android.content.Context
import com.example.airqualityindex.api.ApiInstances
import com.example.airqualityindex.database.ApplicationDatabase
import com.example.airqualityindex.model.DailyRecord
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class AQIRepositoryImpl(private val context: Context) : IAQIRepository {
    companion object {
        private val TAG = AQIRepositoryImpl::class.java.simpleName
    }

    private val dao = ApplicationDatabase.getInstance(this.context).getDailyRecord()

    // use flat map to turn aqi data to list<record>
    override fun getRecordDataResponse(): Observable<List<DailyRecord>> {
        return ApiInstances.getRetrofitInstance().getAQIData()
//            .concatMap {
//                Observable.fromCallable { it.records }
            .map {
                it.records
            }
    }

    override fun getRecordData(): Single<List<DailyRecord>> {
        return ApiInstances.getRetrofitInstance().getAQIDataSingle()
            .map {
                it.records
            }
    }

    override fun insertDataInDatabase(record: List<DailyRecord>) {
        dao.insert(record)
    }

    override fun retrieveRecordFromDatabase(): Observable<List<DailyRecord>> {
        return Observable.fromCallable {
            dao.getAllRecord()
        }
    }

    override fun retrieveRecordFromDatabase(city: String, date: String): Observable<DailyRecord> {
        return Observable.fromCallable {
            dao.getRecordByCityAndDate(city, date)
        }
    }

    override fun retrieveCityFromDatabase(): Observable<List<String>> {
        return Observable.fromCallable {
            dao.getDistinctCity()
        }
    }
}
