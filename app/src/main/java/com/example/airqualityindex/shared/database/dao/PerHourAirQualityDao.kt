package com.example.airqualityindex.shared.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity

@Dao
interface PerHourAirQualityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: List<PerHourAirQualityEntity>)

    @Delete
    fun delete(record: PerHourAirQualityEntity)

    @Query("SELECT * FROM aqi_per_hour_record")
    fun getAllRecord(): List<PerHourAirQualityEntity>

    @Query("SELECT DISTINCT site_name FROM aqi_per_hour_record")
    fun getDistinctSiteName(): List<String>

    @Query("SELECT DISTINCT county FROM aqi_per_hour_record")
    fun getDistinctCounties(): List<String>

    @Query("SELECT site_name FROM aqi_per_hour_record WHERE site_name LIKE '%' || :county || '%'")
    fun getSiteNameByCounty(county: String): List<String>

    @Query("SELECT * FROM aqi_per_hour_record WHERE site_name =:siteName")
    fun getRecordBySiteName(siteName: String?): PerHourAirQualityEntity

    @Query("SELECT * FROM aqi_per_hour_record WHERE site_name =:siteName")
    fun getRecordBySiteNameLiveData(siteName: String?): LiveData<PerHourAirQualityEntity>
}