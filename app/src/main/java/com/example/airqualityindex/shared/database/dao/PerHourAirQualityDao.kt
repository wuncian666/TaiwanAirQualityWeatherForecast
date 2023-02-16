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
    fun getRecordList(): List<PerHourAirQualityEntity>

    @Query("SELECT DISTINCT site_name FROM aqi_per_hour_record")
    fun geSiteList(): List<String>

    @Query("SELECT DISTINCT county FROM aqi_per_hour_record")
    fun getCountyList(): List<String>

    @Query("SELECT site_name FROM aqi_per_hour_record WHERE county =:county")
    fun getSiteListByCounty(county: String?): List<String>

    @Query("SELECT * FROM aqi_per_hour_record WHERE site_name =:siteName")
    fun getRecordBySite(siteName: String?): PerHourAirQualityEntity

    @Query("SELECT * FROM aqi_per_hour_record WHERE site_name =:siteName")
    fun getLiveRecordBySite(siteName: String?): LiveData<PerHourAirQualityEntity>
}