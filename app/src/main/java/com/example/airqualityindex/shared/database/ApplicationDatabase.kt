package com.example.airqualityindex.shared.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.airqualityindex.models.DailyRecord
import com.example.airqualityindex.shared.database.dao.AQIDao
import com.example.airqualityindex.shared.database.dao.PerHourRecordDao
import com.example.airqualityindex.shared.database.dao.WeatherForecastDao
import com.example.airqualityindex.shared.models.WeatherForecastStore
import com.example.airqualityindex.shared.models.aqi.hour.PerHourRecord

@Database(entities = [DailyRecord::class, PerHourRecord::class, WeatherForecastStore::class], version = 8)
abstract class ApplicationDatabase : RoomDatabase() {

    companion object {
        private var instance: ApplicationDatabase? = null
        private var databaseName = "AQI_Database"

        fun getInstance(context: Context): ApplicationDatabase {
            return instance ?: Room.databaseBuilder(
                context, ApplicationDatabase::class.java, databaseName
            )
                .fallbackToDestructiveMigration()
                .build().also { instance = it }
        }
    }

    abstract fun getDailyRecord(): AQIDao
    abstract fun getPerHourRecordDao(): PerHourRecordDao
    abstract fun getWeatherForecastDao(): WeatherForecastDao
}