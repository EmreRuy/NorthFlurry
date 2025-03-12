package com.example.wouple.elements

import android.content.Context
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun SevenHoursCardNotification(temp: TemperatureResponse) {
    val context = LocalContext.current
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
    val timeZone = temp.timezone
    val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
    val currentHour = currentDateTime.hour
    val nextHourIndex = (currentHour + 1) % 24
    //
    val precipitationPr = temp.hourly.precipitation_probability[nextHourIndex]
    val currentWindSpeed = temp.current_weather.windspeed
    val currentWindSpeedUnit = temp.hourly_units.windspeed_10m
    val surfacePressure = temp.hourly.surface_pressure[currentHour].toInt()
    val totalCloudCover = temp.hourly.cloud_cover[currentHour].toInt()
    val windDegreesCurrent = temp.current_weather.winddirection.toInt()
    val windDirection = getLocalizedWindDirection(windDegreesCurrent.toDouble(), context)
    val tempUnit = temp.hourly_units.apparent_temperature
    val feelsLike = temp.hourly.apparent_temperature[currentHour].toInt()
    //
    val texts = listOf(
        context.getString(R.string.precipitation_probability, precipitationPr),
        context.getString(R.string.feels_like, feelsLike, tempUnit),
        context.getString(R.string.total_cloud_cover, totalCloudCover),
        context.getString(R.string.surface_pressure, surfacePressure),
        context.getString(R.string.wind_direction, windDirection),
        context.getString(R.string.wind_speed, currentWindSpeed, currentWindSpeedUnit),
    )

    var visible by remember { mutableStateOf(true) }
    val currentTextIndex = remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (isActive) {
                delay(400)
                currentTextIndex.intValue = (currentTextIndex.intValue + 1) % texts.size
                visible = true
                delay(8000)
                visible = false
            }
        }
    }

    val currentText = texts[currentTextIndex.intValue]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary),
        contentAlignment = CenterStart,
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.thebell),
                contentDescription = "notification",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            AnimatedVisibility(
                visible = visible,
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

@Composable
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
