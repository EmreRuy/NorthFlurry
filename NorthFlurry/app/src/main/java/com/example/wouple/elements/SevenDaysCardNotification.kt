package com.example.wouple.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.Locale

@Composable
fun SevenDaysCardNotification(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF4C49C6)
        val lighterShades = listOf(
            baseColor.copy(alpha = 0.7f),
            baseColor.copy(alpha = 0.8f),
            baseColor.copy(alpha = 0.9f),
        )
        lighterShades
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
        )
    }
    // this code finds the warmest day in a list of daily temperatures
    val warmestDayIndex = temp.daily.temperature_2m_max
        .mapIndexed { index, temperature -> index to temperature.toInt() }
        .maxByOrNull { it.second }
        ?.first ?: 0
    val warmestDate = LocalDate.parse(temp.daily.time[warmestDayIndex])
    val warmestDayOfWeek = warmestDate.dayOfWeek.toString().lowercase()
        .replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    // this code finds the coolest day in the list of daily temperatures
    val coolestDayIndex = temp.daily.temperature_2m_max
        .mapIndexed { index, temperature -> index to temperature.toInt() }
        .minByOrNull { it.second }
        ?.first ?: 0
    val coolestDate = LocalDate.parse(temp.daily.time[coolestDayIndex])
    val coldestDayOfWeek = coolestDate.dayOfWeek.toString().lowercase()
        .replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    //
    val sunIndex =
        temp.daily.time.map { LocalDate.parse(it).dayOfWeek }.indexOf(LocalDate.now().dayOfWeek)
    val sunShine = sunIndex.let { temp.daily.sunshine_duration[sunIndex].toInt() }
    val sunDurationAsHours = sunShine / 3600
    //
    val thunderstormDays = mutableListOf<String>()
    val precipitationDays = mutableListOf<String>()
    // this code retrieves the thunderstorm days
    for (dayIndex in 0 until temp.daily.time.size.coerceAtMost(7)) {
        val dayOfWeek = LocalDate.parse(temp.daily.time[dayIndex]).dayOfWeek.toString().take(3)
        val weatherCode = temp.daily.weathercode[dayIndex]
        // Checks for thunderstorm weather code
        if (weatherCode in listOf(95, 96, 99)) {
            thunderstormDays.add(dayOfWeek)
        }
        //gives the precipitation days
        if (weatherCode in listOf(
                51, 53, 55, 56, 57,
                61, 63, 65, 66, 67,
                80, 81, 82, 71, 73, 75, 77,
                95, 96, 99
            )
        ) {
            precipitationDays.add(dayOfWeek)
        }
    }
    //

    val texts = mutableListOf(
        "Warmest Day expected: $warmestDayOfWeek",
        "Coldest Day expected: $coldestDayOfWeek",
        "Sunshine Duration: $sunDurationAsHours hours today"
    )
    // adds thunderstorm information to notification if location has thunderstorm in the week
    if (thunderstormDays.isNotEmpty()) {
        val thunderstormInfo = if (thunderstormDays.size >= 4) {
            "Thunderstorms expected during the week"
        } else {
            "Thunderstorms expected on: ${
                thunderstormDays.joinToString(", ") { day ->
                    day.lowercase(Locale.getDefault()).replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                    }
                }
            }"
        }
        texts += thunderstormInfo
    }
    //adds precipitation to notification if location has precipitation in the week
    if (precipitationDays.isNotEmpty()) {
        val thunderstormInfo = if (precipitationDays.size >= 4) {
            "Precipitation expected during the week"
        } else {
            "Precipitation expected on: ${
                precipitationDays.joinToString(", ") { day ->
                    day.lowercase(Locale.getDefault()).replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                    }
                }
            }"
        }
        texts += thunderstormInfo
    }
    var apparent by remember { mutableStateOf(true) }
    val currentTextIndex = remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTextIndex.value = (currentTextIndex.value + 1) % texts.size
            apparent = true
            delay(6000)
            apparent = false
        }
    }
    val currentText = if (currentTextIndex.value < texts.size) {
        texts[currentTextIndex.value]
    } else {
        ""
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(brush = Brush.verticalGradient(background)),
        contentAlignment = CenterStart
    ) {
        Row(modifier = Modifier.padding(start = 12.dp)) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.thebell),
                contentDescription = "notification",
                tint = Color.White
            )
            Spacer(modifier = Modifier.padding(4.dp))
            AnimatedVisibility(
                visible = apparent,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(
                    animationSpec = tween(
                        200,
                        easing = EaseOut
                    )
                )
            ) {
                Text(
                    text = currentText,
                    fontWeight = FontWeight.Light,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 15.sp
                )
            }
        }
    }
}
