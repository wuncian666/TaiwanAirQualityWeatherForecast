package com.example.airqualityindex.shared.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SystemTimeManager {

    fun getCurrentTime(): String {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val split = currentDate.format(formatter).split(" ")
        return split[0] + "T" + split[1]
    }

    fun getPeriodTimeNextHour(): Long {
        val startDateTime = LocalDateTime.now()
        val endDateTime = startDateTime.plusHours(1).truncatedTo(ChronoUnit.HOURS)
        val duration = Duration.between(startDateTime, endDateTime)
        return duration.seconds
    }
}