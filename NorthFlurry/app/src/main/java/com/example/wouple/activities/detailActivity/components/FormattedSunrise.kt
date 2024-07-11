package com.example.wouple.activities.detailActivity.components

import androidx.compose.runtime.Composable
import com.example.wouple.model.api.TemperatureResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun getFormattedSunrise(temp: TemperatureResponse): String {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunSet = temp.daily.sunrise.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }
    return todaySunSet?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
}