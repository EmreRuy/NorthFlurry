package com.example.wouple.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Spiro
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun SunRiseAndSetProgress(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalTime()
    val sunriseTime = temp.daily.sunrise.firstOrNull()?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime()
    } ?: LocalTime.MIDNIGHT

    val sunsetTime = temp.daily.sunset.firstOrNull()?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime()
    } ?: LocalTime.MAX

    val progress = calculateProgress(now, sunriseTime, sunsetTime)
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 6.dp, horizontal = 50.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ... (your existing Text and Icon elements)

        DrawHalfCircleProgress(progress)

    }
}

@Composable
private fun DrawHalfCircleProgress(progress: Float) {
    Canvas(
        modifier = Modifier
            .size(80.dp)
            .padding(2.dp)
            .fillMaxSize(1f)
    ) {
        // Draw the background half-circle
        drawArc(
            color = Color.Gray,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(8.dp.toPx())
        )

        // Draw the progress half-circle
        drawArc(
            color = Spiro,
            startAngle = 180f,
            sweepAngle = 180f * progress,
            useCenter = false,
            style = Stroke(8.dp.toPx())
        )
    }
}

@Composable
private fun calculateProgress(
    currentTime: LocalTime,
    sunriseTime: LocalTime,
    sunsetTime: LocalTime
): Float {
    val totalMinutes = sunsetTime.toSecondOfDay().toFloat() - sunriseTime.toSecondOfDay().toFloat()
    val elapsedMinutes =
        currentTime.toSecondOfDay().toFloat() - sunriseTime.toSecondOfDay().toFloat()
    return (elapsedMinutes / totalMinutes).coerceIn(0f, 1f)
}
