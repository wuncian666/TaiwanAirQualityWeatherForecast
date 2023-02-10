package com.example.airqualityindex.shared.constant

import com.example.airqualityindex.R

enum class AirQualityStatus(var statusDescription: Int, var statusImg: Int, var backgroundColor: Int) {
    GOOD(R.string.good, R.drawable.aqi_good, R.drawable.background_green),
    MODERATE(R.string.moderate, R.drawable.aqi_moderate, R.drawable.background_yellow),
    UNHEALTHY_SENSITIVE(
        R.string.unhealthy_for_sensitive_groups,
        R.drawable.aqi_unhealthy_sensitive_groups,
        R.drawable.background_orange
    ),
    UNHEALTHY(R.string.unhealthy, R.drawable.aqi_unhealthy, R.drawable.background_red),
    VERY_UNHEALTHY(
        R.string.very_unhealthy,
        R.drawable.aqi_very_unhealthy,
        R.drawable.background_purple
    )
}