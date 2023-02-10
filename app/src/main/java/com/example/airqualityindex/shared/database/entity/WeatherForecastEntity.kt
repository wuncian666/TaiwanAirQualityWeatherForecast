package com.example.airqualityindex.shared.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_forecast")
data class WeatherForecastEntity(
    @PrimaryKey
    var locationName: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var weatherPhenomenon: String = "",
    var temperatureMax: String = "",
    var temperatureMin: String = "",
    var comfort: String = "",
    var probabilityOfPrecipitation: String = ""
)