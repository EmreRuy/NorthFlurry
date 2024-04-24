package com.example.wouple.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.activities.detailActivity.WeatherCondition
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.vintage
import java.time.LocalDate
import java.util.Locale

@Composable
fun getWeeklyForecast(temp: TemperatureResponse) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (dayIndex in 0 until temp.daily.time.size.coerceAtMost(7)) {
            val dayOfWeek = LocalDate.parse(temp.daily.time[dayIndex]).dayOfWeek.toString().take(3)
            val temperature = temp.daily.temperature_2m_max[dayIndex].toInt().toString()
            val weatherCode = temp.daily.weathercode[dayIndex]
            val weatherCondition = when (weatherCode) {
                0, 1 -> WeatherCondition.SUNNY
                2 -> WeatherCondition.PARTLYCLOUDY
                3, 4 -> WeatherCondition.CLOUDY
                in listOf(51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82) -> WeatherCondition.RAINY
                in listOf(71, 73, 75, 77, 85, 86) -> WeatherCondition.SNOWY
                in listOf(95, 96, 99) -> WeatherCondition.THUNDERSTORM
                else -> WeatherCondition.SUNNY
            }
            val imageResourceId = weatherCondition.imageResourceId

            WeeklyForecastItem(
                dayOfWeek = dayOfWeek,
                temperature = temperature,
                imageResourceId = imageResourceId
            )
        }
    }
}

@Composable
fun WeeklyForecastItem(dayOfWeek: String, temperature: String, imageResourceId: Int) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayOfWeek.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() },
            color = vintage,
            fontSize = 15.sp
        )
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
            Text(
                text = "$temperature°",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
    }
}