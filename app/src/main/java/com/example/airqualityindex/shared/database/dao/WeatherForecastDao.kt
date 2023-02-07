package com.example.airqualityindex.shared.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airqualityindex.shared.models.WeatherForecastStore

@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(records: List<WeatherForecastStore>)

    @Query("SELECT * FROM weather_forecast WHERE locationName =:locationName")
    fun getRecordByLocationName(locationName: String?): WeatherForecastStore

    @Query("SELECT * FROM weather_forecast WHERE locationName =:locationName")
    fun getRecordByLocationNameLiveData(locationName: String?): LiveData<WeatherForecastStore>
}