package com.example.airqualityindex.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "aqi_record", primaryKeys = ["date", "site_name"])
data class DailyRecord(
    @ColumnInfo(name = "date")
    @SerializedName("monitordate")
    val date: String,
    @ColumnInfo(name = "site_id")
    @SerializedName("siteid")
    val id: String,
    @ColumnInfo(name = "site_name")
    @SerializedName("sitename")
    val name: String,
    @ColumnInfo(name = "aqi")
    val aqi: String,
    @ColumnInfo(name = "so2")
    @SerializedName("so2subindex")
    val so2: String,
    @ColumnInfo(name = "co")
    @SerializedName("cosubindex")
    val co:String,
    @ColumnInfo(name = "o3")
    @SerializedName("o3subindex")
    val o3:String,
    @ColumnInfo(name = "pm10")
    @SerializedName("pm10subindex")
    val pm10:String,
    @ColumnInfo(name = "no2")
    @SerializedName("no2subindex")
    val no2: String,
    @ColumnInfo(name = "o38")
    @SerializedName("o38subindex")
    val o38:String,
    @ColumnInfo(name = "pm25")
    @SerializedName("pm25subindex")
    val pm25:String
)