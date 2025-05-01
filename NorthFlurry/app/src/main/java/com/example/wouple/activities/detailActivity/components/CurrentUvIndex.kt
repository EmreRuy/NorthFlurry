package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun CurrentUvIndex(temp: TemperatureResponse) {
    val timeZone = temp.timezone
    val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
    val currentHour = currentDateTime.hour
    val uvIndexValue = temp.hourly.uv_index[currentHour].toFloat().coerceAtMost(11f)

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.current_uv_index).uppercase(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CircularProgressBar(
            percentage = uvIndexValue / 11f, // max UV index scale is  0–11+
            number = uvIndexValue.toInt(),
            fontSize = 24.sp,
            radius = 60.dp,
            color = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
            strokeWidth = 10.dp
        )

        val uvIndexDescriptions = getUvIndexDescription(uvIndexValue.toInt())

        Text(
            text = uvIndexDescriptions,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
