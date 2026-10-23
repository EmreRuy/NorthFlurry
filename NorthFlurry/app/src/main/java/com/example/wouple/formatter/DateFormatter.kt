package com.example.wouple.formatter

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatter {
    fun formatDate(value: String): String {
        return try {
            val localDateTime = LocalDateTime.parse(value)
            val dateTimeFormatter = DateTimeFormatter.ofPattern("HH")
            localDateTime.format(dateTimeFormatter)
        } catch (_: Exception) {
            ""
        }
    }

    fun safeZoneId(timeZone: String?): ZoneId {
        if (timeZone.isNullOrBlank() || timeZone.equals("auto", ignoreCase = true)) {
            return ZoneId.systemDefault()
        }
        return try {
            ZoneId.of(timeZone)
        } catch (_: Exception) {
            ZoneId.systemDefault()
        }
    }
}