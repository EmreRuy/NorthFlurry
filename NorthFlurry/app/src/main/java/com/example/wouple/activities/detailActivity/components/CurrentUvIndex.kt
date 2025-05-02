package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(12.dp),
        contentAlignment = Alignment.TopStart
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.current_uv_index)
                .replaceFirstChar { it.uppercaseChar() },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        CircularProgressBar(
            percentage = uvIndexValue / 11f, // max UV index scale is  0–11+
            number = uvIndexValue.toInt(),
            radius = 40.dp,
            strokeWidth = 6.dp,
            fontSize = 18.sp
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
}
