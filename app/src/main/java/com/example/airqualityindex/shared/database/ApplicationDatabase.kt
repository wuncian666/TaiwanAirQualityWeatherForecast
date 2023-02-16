package com.example.airqualityindex.shared.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.airqualityindex.shared.database.dao.DailyAirQualityDao
import com.example.airqualityindex.shared.database.dao.PerHourAirQualityDao
import com.example.airqualityindex.shared.database.dao.WeatherForecastDao
import com.example.airqualityindex.shared.database.entity.DailyAirQualityEntity
import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity
import com.example.airqualityindex.shared.database.entity.WeatherForecastEntity

@Database(
    entities = [DailyAirQualityEntity::class, PerHourAirQualityEntity::class, WeatherForecastEntity::class],
    version = 9,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    companion object {
        private var instance: ApplicationDatabase? = null
        private var databaseName = "AQI_Database"

        fun getInstance(context: Context): ApplicationDatabase {
            return this.instance ?: Room.databaseBuilder(
                context, ApplicationDatabase::class.java, this.databaseName
            )
                .fallbackToDestructiveMigration()
                .build().also { this.instance = it }
        }
    }

    abstract fun dailyRecord(): DailyAirQualityDao
    abstract fun perHourRecordDao(): PerHourAirQualityDao
    abstract fun weatherForecastDao(): WeatherForecastDao
}