package com.example.wouple.activities.detailActivity.components

import androidx.compose.runtime.Composable
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.TemperatureResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun getFormattedSunset(temp: TemperatureResponse): String {
    val zoneId = DateFormatter.safeZoneId(temp.timezone)
    val now = LocalDate.now(zoneId)

    val todaySunset = temp.daily.sunset.firstOrNull {
        try {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate() == now
        } catch (_: Exception) {
            false
        }
    }

    return todaySunset?.let {
        try {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .format(DateTimeFormatter.ofPattern("HH:mm"))
        } catch (_: Exception) {
            ""
        }
    } ?: ""
}
