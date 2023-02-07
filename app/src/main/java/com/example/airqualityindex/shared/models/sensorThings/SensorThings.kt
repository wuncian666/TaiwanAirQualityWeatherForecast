package com.example.airqualityindex.model.sensorThings

import com.google.gson.annotations.SerializedName

data class SensorThingsResult(
    val value: List<SensorThings>
)

data class SensorThings(
    val name: String,
    val properties: Properties,
    @SerializedName("Locations")
    val locations:Locations
)

data class Properties(
    val city: String,
    val areaType: String,
    val isMobile: Boolean,
    val township: String,
    val authority: String,
    val isDisplay: Boolean,
    val isOutdoor: Boolean,
    val stationID: String,
    val locationId: String,
    @SerializedName("Description")
    val description: String,
    val projectName: String,
    val stationName: String,
    val areaDescription: String
)

data class Locations(
    val coordinates: List<Double>
)
