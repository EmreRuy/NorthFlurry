package com.example.wouple.elements

import androidx.compose.runtime.Composable
import com.example.wouple.model.api.TemperatureResponse
@Composable
fun GetWeatherCodes(temp: TemperatureResponse): String{
val weatherDescriptions = mapOf(
    0 to "Clear Sky",
    1 to "Mainly Clear",
    2 to "Partly Cloudy",
    3 to "Overcast",
    45 to "Foggy",
    48 to "Rime Fog",
    51 to "Light Drizzle",
    53 to "Moderate Drizzle",
    55 to "Heavy Drizzle",
    56 to "Light Freezing Drizzle",
    57 to "Heavy Freezing Drizzle",
    61 to "Slight Rain",
    63 to "Moderate Rain",
    65 to "Heavy Rain",
    66 to "Light Freezing Rain",
    67 to "Heavy Freezing Rain",
    71 to "Light Snowfall",
    73 to "Moderate Snowfall",
    75 to "Heavy Snowfall",
    77 to "Snow Grains",
    80 to "Slight Rain Showers",
    81 to "Moderate Rain Showers",
    82 to "Heavy Rain Showers",
    85 to "Slight Snow Showers",
    86 to "Heavy Snow Showers",
    95 to "Thunderstorm"
)
val weatherCode = temp.current_weather.weathercode
val weatherDescription = weatherDescriptions[weatherCode] ?: "Unknown"
    return weatherDescription
}