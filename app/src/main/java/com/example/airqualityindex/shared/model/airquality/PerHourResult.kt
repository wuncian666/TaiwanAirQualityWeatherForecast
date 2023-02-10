package com.example.airqualityindex.shared.model.airquality

import com.example.airqualityindex.shared.database.entity.PerHourAirQualityEntity

data class PerHourResult(
    val records: List<PerHourAirQualityEntity>
)