package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun CurrentAirQualityCardCompact(air: AirQuality?, temp: TemperatureResponse) {
    val airQualityValue = air?.current?.european_aqi ?: 0
    val airQualityPercentage = airQualityValue / 100f
    val descriptionResId = getAirQualityDescriptionResId(airQualityValue)
    val description = stringResource(id = descriptionResId)

    Box(
        modifier = Modifier
            .size(180.dp)
            .clip(RoundedCornerShape(16.dp))
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
                modifier = Modifier.padding(4.dp),
                text = stringResource(id = R.string.air_quality_index)
                    .replaceFirstChar { it.uppercaseChar() },
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
            )
            CircularProgressBar(
                percentage = airQualityPercentage.coerceIn(0f, 1f),
                number = airQualityValue,
                radius = 40.dp,
                strokeWidth = 6.dp,
                fontSize = 18.sp
            )
            Text(
                text = description,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_airquality),
            contentDescription = stringResource(id = R.string.air_quality_index),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(16.dp)
        )
    }
}
