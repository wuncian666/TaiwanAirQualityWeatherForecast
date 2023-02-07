package com.example.airqualityindex.shared.models.aqiDaily
import com.example.airqualityindex.models.DailyRecord
import com.google.gson.annotations.SerializedName

data class DailyAQI(
    val fields: List<Fields>,
    @SerializedName("resource_id")
    val resourceId: String,
    @SerializedName("__extras")
    val extras: Extras,
    @SerializedName("include_total")
    val includeTotal: Boolean,
    val total: Int,
    @SerializedName("resource_format")
    val resourceFormat: String,
    val limit: Int,
    val offset: Int,
    @SerializedName("_links")
    val links: Links,
    val records: List<DailyRecord>
)