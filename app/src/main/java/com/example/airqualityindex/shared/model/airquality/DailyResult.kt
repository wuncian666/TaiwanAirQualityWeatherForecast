package com.example.airqualityindex.shared.model.airquality

import com.example.airqualityindex.shared.database.entity.DailyAirQualityEntity

data class DailyResult(
    val records: List<DailyAirQualityEntity>
)