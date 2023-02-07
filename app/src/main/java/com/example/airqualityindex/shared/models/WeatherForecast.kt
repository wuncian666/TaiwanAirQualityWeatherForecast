package com.example.airqualityindex.shared.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WeatherForecastResult(
    val records: Records
)

data class Records(
    val success: String,
    val datasetDescription: String,
    val location: List<Location>
)

data class Location(
    val locationName: String,
    val weatherElement: List<WeatherElement>
)

data class WeatherElement(
    val elementName: String,
    val time: List<Time>
)

data class Time(
    val startTime: String,
    val endTime: String,
    val parameter: Parameter
)

data class Parameter(
    val parameterName: String,
    val parameterUnit: String
)

@Entity(tableName = "weather_forecast")
data class WeatherForecastStore(
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
