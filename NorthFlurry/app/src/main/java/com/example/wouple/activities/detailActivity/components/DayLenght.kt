package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Whitehis
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun GetDayLength(temp: TemperatureResponse) {
    val currentContext = LocalContext.current
    val now = LocalDate.now()
    val todaySunrise = temp.daily.sunrise
        .firstOrNull {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate() == now
        }
        ?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime() }
    val todaySunset = temp.daily.sunset
        .firstOrNull {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate() == now
        }
        ?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime() }
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedSunrise = todaySunrise?.format(formatter) ?: ""
    val formattedSunset = todaySunset?.format(formatter) ?: ""
    val lengthOfTheDay = if (formattedSunrise.isNotEmpty() && formattedSunset.isNotEmpty()) {
        val length = Duration.between(todaySunrise, todaySunset)
        val hours = length.toHours()
        val minutes = length.minusHours(hours).toMinutes()
        val dayLengthFormat = currentContext.getString(R.string.day_length_format)
        String.format(dayLengthFormat, hours, minutes)
    } else {
        "N/A"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Day_Length),
            fontWeight = FontWeight.Thin,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = lengthOfTheDay,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}