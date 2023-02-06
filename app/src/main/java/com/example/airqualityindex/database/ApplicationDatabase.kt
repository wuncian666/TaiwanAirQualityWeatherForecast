package com.example.airqualityindex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.airqualityindex.model.DailyRecord
import com.example.airqualityindex.model.WeatherForecastStore
import com.example.airqualityindex.model.aqiPerHour.PerHourRecord

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