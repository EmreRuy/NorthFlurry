package com.example.wouple.activities.detailActivity.components

import com.example.wouple.model.api.TemperatureResponse
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.abs

data class WeatherDetails(
    val feelsLike: Int,
    val humidity: Int,
    val dewPoint: Int,
    val rainFall: Int,
    val visibilityInMeters: Int,
    val windSpeed: Int
)

fun getWeatherDetails(temp: TemperatureResponse): WeatherDetails {
    val now = LocalDateTime.now()
    var bestIndex = 0
    var minDiffSeconds = Long.MAX_VALUE

    temp.hourly.time.forEachIndexed { i, timeStr ->
        try {
            val parsed = LocalDateTime.parse(timeStr)
            val diff = abs(Duration.between(parsed, now).seconds)
            if (diff < minDiffSeconds) {
                minDiffSeconds = diff
                bestIndex = i
            }
        } catch (_: Exception) { }
    }

    val feelsLike = temp.hourly.apparent_temperature.getOrNull(bestIndex)?.toInt() ?: 0
    val humidity = temp.hourly.relativehumidity_2m.getOrNull(bestIndex) ?: 0
    val dewPoint = temp.hourly.dewpoint_2m.getOrNull(bestIndex)?.toInt() ?: 0

    val today = LocalDate.now()
    var bestDayIndex = 0
    var minDayDiff = Long.MAX_VALUE
    temp.daily.time.forEachIndexed { i, dateStr ->
        try {
            val parsedDate = LocalDate.parse(dateStr)
            val diff = abs(parsedDate.toEpochDay() - today.toEpochDay())
            if (diff < minDayDiff) {
                minDayDiff = diff
                bestDayIndex = i
            }
        } catch (_: Exception) { }
    }
    val rainFall = temp.daily.rain_sum.getOrNull(bestDayIndex)?.toInt() ?: 0

    val visibilityInMeters = temp.hourly.visibility.getOrNull(bestIndex)?.toInt() ?: 0
    val windSpeed = temp.hourly.windspeed_10m.getOrNull(bestIndex)?.toInt() ?: 0

    return WeatherDetails(feelsLike, humidity, dewPoint, rainFall, visibilityInMeters, windSpeed)
}