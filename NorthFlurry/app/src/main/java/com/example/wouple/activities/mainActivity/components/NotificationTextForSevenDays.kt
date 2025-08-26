package com.example.wouple.activities.mainActivity.components

import android.content.Context
import androidx.compose.runtime.Composable
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.components.getUvIndexDescription
import com.example.wouple.elements.formatDayList
import com.example.wouple.elements.getExtremeDayName
import com.example.wouple.elements.getWeatherEvents
import com.example.wouple.model.api.TemperatureResponse
import java.time.LocalDate

@Composable
fun buildNotificationMessages(temp: TemperatureResponse, context: Context): List<String> {
    val messages = mutableListOf<String>()

    // Warmest and coldest
    val warmestDay =
        getExtremeDayName(temp.daily.temperature_2m_max, temp.daily.time, context, max = true)
    val coldestDay =
        getExtremeDayName(temp.daily.temperature_2m_max, temp.daily.time, context, max = false)

    messages += context.getString(R.string.warmest_day, warmestDay)
    messages += context.getString(R.string.coldest_day, coldestDay)

    // Sunshine duration today
    val todayIndex = temp.daily.time.indexOfFirst {
        LocalDate.parse(it).dayOfWeek == LocalDate.now().dayOfWeek
    }
    if (todayIndex != -1) {
        val sunHours = temp.daily.sunshine_duration[todayIndex].toInt() / 3600
        messages += context.getString(R.string.sunshine_duration, sunHours)
    }

    // UV Index
    val uvDesc = getUvIndexDescription(temp.daily.uv_index_max[0].toInt())
        .replaceFirstChar { it.lowercase() }
    messages += context.getString(R.string.uv_index_today, uvDesc)

    // Weather codes
    val (thunderDays, precipitationDays) = getWeatherEvents(temp, context)

    if (thunderDays.isNotEmpty()) {
        messages += if (thunderDays.size >= 4)
            context.getString(R.string.thunderstorms_week)
        else
            context.getString(R.string.thunderstorms_days, formatDayList(thunderDays))
    }

    if (precipitationDays.isNotEmpty()) {
        messages += if (precipitationDays.size >= 4)
            context.getString(R.string.precipitation_week)
        else
            context.getString(R.string.precipitation_days, formatDayList(precipitationDays))
    }

    return messages
}