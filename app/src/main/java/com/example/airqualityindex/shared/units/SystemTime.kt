package com.example.airqualityindex.shared.units

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SystemTime {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTime(): String {
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val split = currentDate.format(formatter).split(" ")
        return split[0] + "T" + split[1]
    }
}