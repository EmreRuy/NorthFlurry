package com.example.wouple.activities.detailActivity.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun DayLightDuration(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = if (isDay) R.drawable.ic_sun else R.drawable.ic_moon),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Text(
                text = stringResource(id = R.string.Daylight_Duration),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            DaylightInfoSection(temp = temp)
        }
    }
}

@Composable
private fun DaylightInfoSection(temp: TemperatureResponse) {
    val sunrise = getFormattedSunrise(temp)
    val sunset = getFormattedSunset(temp)

    if (sunrise.isNotEmpty() && sunset.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedInfoCard("Sunrise", sunrise, isSunrise = true)
            AnimatedInfoCard("Sunset", sunset, isSunrise = false)
        }
        Spacer(modifier = Modifier.height(4.dp))
        DayLengthIndicator(temp)
    } else ErrorCard()
}

@Composable
fun AnimatedInfoCard(title: String, value: String, isSunrise: Boolean) {
    val offsetY by rememberInfiniteTransition(label = title)
        .animateFloat(
            initialValue = if (isSunrise) 10f else -10f,
            targetValue = if (isSunrise) -10f else 10f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "offsetY"
        )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(150.dp)
            .background(
                color = Color.White.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(id = if (isSunrise) R.drawable.ic_sun else R.drawable.ic_moon),
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(28.dp)
                .graphicsLayer(translationY = offsetY)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 15.sp
            )
        )
    }
}

@Composable
fun DayLengthIndicator(temp: TemperatureResponse) {
    val dayLength = getDayLengthString(temp)
    Text(
        text = buildAnnotatedString {
            append("Day Length: ")
            withStyle(
                style = MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            ) {
                append(dayLength)
            }
        },
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
fun ErrorCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(top = 8.dp)
            .background(Color.Red.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_error_outline_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.NoData),
            fontSize = 14.sp,
            color = Color.White
        )
    }
}

@Composable
fun getDayLengthString(temp: TemperatureResponse): String {
    val sunrise = getFormattedSunrise(temp)
    val sunset = getFormattedSunset(temp)

    return try {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val sunriseTime = LocalTime.parse(sunrise, timeFormatter)
        val sunsetTime = LocalTime.parse(sunset, timeFormatter)
        val duration = Duration.between(sunriseTime, sunsetTime)

        val hours = duration.toHours()
        val minutes = duration.toMinutes() % 60
        "${hours}h ${minutes}m"
    } catch (_: Exception) {
        "N/A"
    }
}

