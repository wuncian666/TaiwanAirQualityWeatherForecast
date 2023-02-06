package com.example.airqualityindex.model.aqiPerHour

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PerHourResult(
    val records: List<PerHourRecord>
)

@Parcelize
@Entity(tableName = "aqi_per_hour_record")
data class PerHourRecord(
    @PrimaryKey
    @ColumnInfo(name = "site_name")
    @SerializedName("sitename")
    val siteName: String,
    @ColumnInfo(name = "county")
    val county: String,
    @ColumnInfo(name = "aqi")
    val aqi: String,
    @ColumnInfo(name = "pollutant")
    val pollutant: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "so2")
    val so2: String,
    @ColumnInfo(name = "co")
    val co: String,
    @ColumnInfo(name = "o3")
    val o3: String,
    @ColumnInfo(name = "o3_8hr")
    val o3_8hr: String,
    @ColumnInfo(name = "pm10")
    val pm10: String,
    @ColumnInfo(name = "pm25")
    @SerializedName("pm2.5")
    val pm25: String,
    @ColumnInfo(name = "no2")
    val no2: String,
    @ColumnInfo(name = "nox")
    val nox: String,
    @ColumnInfo(name = "no")
    val no: String,
    @ColumnInfo(name = "wind_speed")
    @SerializedName("wind_speed")
    val windSpeed: String,
    @ColumnInfo(name = "wind_direction")
    @SerializedName("wind_direc")
    val windDirection: String,
    @ColumnInfo(name = "publish_time")
    @SerializedName("publishtime")
    val publishTime: String,
    @ColumnInfo(name = "co_8hr")
    val co_8hr: String,
    @ColumnInfo(name = "pm25_avg")
    @SerializedName("pm2.5_avg")
    val pm25avg: String,
    @ColumnInfo(name = "pm10_avg")
    @SerializedName("pm10_avg")
    val pm10avg: String,
    @ColumnInfo(name = "so2_avg")
    @SerializedName("so2_avg")
    val so2avg: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "site_id")
    @SerializedName("siteid")
    val siteId: String
) : Parcelable