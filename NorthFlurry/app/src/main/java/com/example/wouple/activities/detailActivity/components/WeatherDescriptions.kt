package com.example.wouple.activities.detailActivity.components

import androidx.annotation.StringRes
import com.example.wouple.R

@StringRes
fun getWeatherDescriptionResId(code: Int): Int {
    return when (code) {
        0 -> R.string.Clear_Sky
        1 -> R.string.Mainly_Clear
        2 -> R.string.Partly_Cloudy
        3 -> R.string.Overcast
        45 -> R.string.Foggy
        48 -> R.string.Rime_Fog
        51 -> R.string.Light_Drizzle
        53 -> R.string.Moderate_Drizzle
        55 -> R.string.Heavy_Drizzle
        56 -> R.string.Light_Freezing_Drizzle
        57 -> R.string.Heavy_Freezing_Drizzle
        61 -> R.string.Slight_Rain
        63 -> R.string.Moderate_Rain
        65 -> R.string.Heavy_Rain
        66 -> R.string.Light_Freezing_Rain
        67 -> R.string.Heavy_Freezing_Rain
        71 -> R.string.Light_Snowfall
        73 -> R.string.Moderate_Snowfall
        75 -> R.string.Heavy_Snowfall
        77 -> R.string.Snow_Grains
        80 -> R.string.Slight_Rain_Showers
        81 -> R.string.Moderate_Rain_Showers
        82 -> R.string.Heavy_Rain_Showers
        85 -> R.string.Slight_Snow_Showers
        86 -> R.string.Heavy_Snow_Showers
        95 -> R.string.Thunderstorm
        96 -> R.string.Thunderstorm
        99 -> R.string.Thunderstorm
        else -> R.string.ND
    }
}