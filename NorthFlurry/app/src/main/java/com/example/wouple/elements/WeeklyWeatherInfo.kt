package com.example.wouple.elements

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.activities.detailActivity.components.WeatherCondition
import com.example.wouple.model.api.TemperatureResponse
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun GetWeeklyForecast(temp: TemperatureResponse) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Center,
    ) {
        for (dayIndex in 0 until temp.daily.time.size.coerceAtMost(7)) {
            val dayOfWeek = LocalDate.parse(temp.daily.time[dayIndex]).dayOfWeek
            val localizedDayName = getLocalizedDayNames(dayOfWeek, context)
            val temperature = temp.daily.temperature_2m_max[dayIndex].toInt().toString()
            val weatherCondition = when (temp.daily.weathercode[dayIndex]) {
                0, 1 -> WeatherCondition.SUNNY
                2 -> WeatherCondition.PARTLY_CLOUDY
                3, 4 -> WeatherCondition.CLOUDY
                45, 48 -> WeatherCondition.FOGGY
                in listOf(
                    51,
                    53,
                    55,
                    56,
                    57,
                    61,
                    63,
                    65,
                    66,
                    67,
                    80,
                    81,
                    82
                ) -> WeatherCondition.RAINY
                in listOf(71, 73, 75, 77) -> WeatherCondition.SNOWY
                in listOf(95, 96, 99) -> WeatherCondition.THUNDERSTORM
                else -> WeatherCondition.RAINY
            }
            val imageResourceId = weatherCondition.imageResourceId

            WeeklyForecastItem(
                dayOfWeek = localizedDayName,
                temperature = temperature,
                imageResourceId = imageResourceId
            )
        }
    }
}

@Composable
fun WeeklyForecastItem(dayOfWeek: String, temperature: String, imageResourceId: Int) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .wrapContentWidth(align = Alignment.CenterHorizontally),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = dayOfWeek.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString() },
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Light
        )
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "$temperature°",
            color = Color.White.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}
@Composable
fun getLocalizedDayNames(dayOfWeek: DayOfWeek, context: Context): String {
    val currentLocale = context.resources.configuration.locales[0] // Get current locale
    val language = currentLocale.language
    val dayName = when (language) {
        "nb" -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("nb", "NO"))
        "es" -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES"))
        "fr" -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("fr", "FR"))
        "it" -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("it", "IT"))
        else -> dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) // Default to English
    }
    val cleanDayName = dayName.trimEnd { it == '.' }
    return cleanDayName.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(currentLocale) else it.toString()
    }
}