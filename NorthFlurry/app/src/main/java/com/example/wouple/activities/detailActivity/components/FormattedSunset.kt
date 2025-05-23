package com.example.wouple.activities.detailActivity.components

import androidx.compose.runtime.Composable
import com.example.wouple.model.api.TemperatureResponse
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun getFormattedSunset(temp: TemperatureResponse): String {
    val zoneId = ZoneId.of(temp.timezone)
    val now = LocalDate.now(zoneId)

    val todaySunset = temp.daily.sunset.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate() == now
    }

    return todaySunset?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
}
