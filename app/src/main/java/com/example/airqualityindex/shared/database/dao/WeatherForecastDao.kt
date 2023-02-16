package com.example.airqualityindex.shared.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity

@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(records: List<WeatherForecastEntity>)

    @Query("SELECT * FROM weather_forecast WHERE locationName =:locationName")
    fun getRecordByLocation(locationName: String?): WeatherForecastEntity

    @Query("SELECT * FROM weather_forecast WHERE locationName =:locationName")
    fun getLiveRecordByLocation(locationName: String?): LiveData<WeatherForecastEntity>
}