package com.example.wouple.activities.detailActivity.components

import com.example.wouple.model.api.TemperatureResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class WeatherDetails(
    val feelsLike: Int,
    val humidity: Int,
    val dewPoint: Int,
    val rainFall: Int,
    val visibilityInMeters: Int,
    val windSpeed: Int
)

fun getWeatherDetails(temp: TemperatureResponse): WeatherDetails {
    val currentHour = LocalDateTime.now().hour
    val index = temp.hourly.time.map { LocalDateTime.parse(it).hour }.indexOf(currentHour)

    val feelsLike = temp.hourly.apparent_temperature.getOrNull(index)?.toInt() ?: 0
    val humidity = temp.hourly.relativehumidity_2m.getOrNull(index) ?: 0
    val dewPoint = temp.hourly.dewpoint_2m.getOrNull(index)?.toInt() ?: 0

    val currentDay = LocalDate.now().dayOfWeek
    val rainIndex = temp.daily.time.map { LocalDate.parse(it).dayOfWeek }.indexOf(currentDay)
    val rainFall = temp.daily.rain_sum.getOrNull(rainIndex)?.toInt() ?: 0

    val visibilityInMeters = temp.hourly.visibility.getOrNull(index)?.toInt() ?: 0
    val windSpeed = temp.hourly.windspeed_10m.getOrNull(index)?.toInt() ?: 0

    return WeatherDetails(feelsLike, humidity, dewPoint, rainFall, visibilityInMeters, windSpeed)
}