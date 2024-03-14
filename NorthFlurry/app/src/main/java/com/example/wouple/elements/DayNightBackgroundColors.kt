package com.example.wouple.elements

import androidx.compose.ui.graphics.Color
import com.example.wouple.model.api.TemperatureResponse

fun getBackgroundColorsForDayAndNight(temp : TemperatureResponse){
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            /* Color(0xFF25508C),
             Color(0xFF87CEEB),
             Color(0xFFB0E0E6),
             Color(0xFFE0FFFF),
             Color(0xFFFFE4B5), */
            /* Color(0xFF25508C),
             Color(0xFF4180B3),
             Color(0xFF8ABFCC),
             Color(0xFFFFE4B5) */
            Color(0xFF6495ED),
            Color(0xFF4169E1),
        )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
            Color(0xFF50767D),
        )
    }
}