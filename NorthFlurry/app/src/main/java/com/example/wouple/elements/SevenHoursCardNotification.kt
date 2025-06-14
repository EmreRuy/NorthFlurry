package com.example.wouple.elements

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun SevenHoursCardNotification(temp: TemperatureResponse) {
    val context = LocalContext.current
    val texts = remember { generateWeatherInfoTexts(temp, context) }

    val currentTextIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(8000)
            currentTextIndex.intValue = (currentTextIndex.intValue + 1) % texts.size
        }
    }

    val currentText = texts[currentTextIndex.intValue]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurfaceVariant),
        contentAlignment = CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.thebell),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Crossfade(
                targetState = currentText,
                animationSpec = tween(durationMillis = 600, easing = EaseOut),
                modifier = Modifier.animateContentSize()
            ) { text ->
                Text(
                    text = text,
                    fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 15.sp
                )
            }
        }
    }
}

private fun generateWeatherInfoTexts(temp: TemperatureResponse, context: Context): List<String> {
    val timeZone = temp.timezone
    val now = ZonedDateTime.now(ZoneId.of(timeZone))
    val hour = now.hour
    val nextHour = (hour + 1) % 24

    val precipitation = temp.hourly.precipitation_probability[nextHour]
    val windSpeed = temp.current_weather.windspeed
    val windSpeedUnit = temp.hourly_units.windspeed_10m
    val pressure = temp.hourly.surface_pressure[hour].toInt()
    val cloudCover = temp.hourly.cloud_cover[hour].toInt()
    val windDir = getLocalizedWindDirection(temp.current_weather.winddirection.toDouble(), context)
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
