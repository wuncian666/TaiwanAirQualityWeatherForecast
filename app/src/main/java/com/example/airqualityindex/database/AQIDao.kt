package com.example.airqualityindex.database

import androidx.room.*
import com.example.airqualityindex.model.DailyRecord

@Dao
interface AQIDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: List<DailyRecord>)

    @Update
    fun update(record: DailyRecord)

    @Delete
    fun delete(record: DailyRecord)

    @Query("SELECT * FROM aqi_record")
    fun getAllRecord(): List<DailyRecord>

    @Query("SELECT DISTINCT site_name FROM aqi_record")
    fun getDistinctCity(): List<String>

    @Query("SELECT * FROM aqi_record WHERE site_name =:city AND date =:date")
    fun getRecordByCityAndDate(city: String, date: String): DailyRecord
}