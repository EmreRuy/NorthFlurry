package com.example.wouple.activities.mainActivity.components

import android.content.Context
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import java.time.ZoneId
import java.time.ZonedDateTime


fun generateWeatherInfoTexts(temp: TemperatureResponse, context: Context): List<String> {
    val timeZone = temp.timezone
    val now = ZonedDateTime.now(ZoneId.of(timeZone))
    val hour = now.hour
    val nextHour = (hour + 1) % 24

    val precipitation = temp.hourly.precipitation_probability[nextHour]
    val windSpeed = temp.current_weather.windspeed
    val windSpeedUnit = temp.hourly_units.windspeed_10m
    val pressure = temp.hourly.surface_pressure[hour].toInt()
    val cloudCover = temp.hourly.cloud_cover[hour].toInt()
    val windDir = getLocalizedWindDirection(temp.current_weather.winddirection, context)
    val feelsLike = temp.hourly.apparent_temperature[hour].toInt()
    val tempUnit = temp.hourly_units.apparent_temperature

    return listOf(
        context.getString(R.string.precipitation_probability, precipitation),
        context.getString(R.string.feels_like, feelsLike, tempUnit),
        context.getString(R.string.total_cloud_cover, cloudCover),
        context.getString(R.string.surface_pressure, pressure),
        context.getString(R.string.wind_direction, windDir),
        context.getString(R.string.wind_speed, windSpeed, windSpeedUnit),
    )
}

private fun getLocalizedWindDirection(degrees: Double, context: Context): String {
    val directions = context.resources.getStringArray(R.array.wind_directions)
    return when (degrees) {
        in 0.0..22.5, in 337.5..360.0 -> directions[0]
        in 22.5..67.5 -> directions[1]
        in 67.5..112.5 -> directions[2]
        in 112.5..157.5 -> directions[3]
        in 157.5..202.5 -> directions[4]
        in 202.5..247.5 -> directions[5]
        in 247.5..292.5 -> directions[6]
        in 292.5..337.5 -> directions[7]
        else -> directions[8]
    }
}