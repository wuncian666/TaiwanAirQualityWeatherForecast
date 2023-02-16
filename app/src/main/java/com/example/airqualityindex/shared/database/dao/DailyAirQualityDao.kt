package com.example.airqualityindex.shared.database.dao

import androidx.room.*
import com.example.airqualityindex.shared.database.entity.DailyAirQualityEntity

@Dao
interface DailyAirQualityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: List<DailyAirQualityEntity>)

    @Update
    fun update(record: DailyAirQualityEntity)

    @Delete
    fun delete(record: DailyAirQualityEntity)

    @Query("SELECT * FROM aqi_record")
    fun getRecordList(): List<DailyAirQualityEntity>

    @Query("SELECT DISTINCT site_name FROM aqi_record")
    fun getSiteNameList(): List<String>

    @Query("SELECT * FROM aqi_record WHERE site_name =:city AND date =:date")
    fun getRecordByCityAndDate(city: String, date: String): DailyAirQualityEntity
}