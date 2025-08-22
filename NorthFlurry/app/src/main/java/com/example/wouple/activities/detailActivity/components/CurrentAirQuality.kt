package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.utils.getAirQualityInfo
import com.example.wouple.model.api.AirQuality

@Composable
fun CurrentAirQualityCardCompact(
    air: AirQuality?
) {
    val airQualityValue = air?.current?.european_aqi ?: 0
    val airQualityPercentage = (airQualityValue.coerceIn(0, 300)) / 300f
    val airQualityInfo = getAirQualityInfo(airQualityValue)
    val description = stringResource(id = airQualityInfo.descriptionResId)
    val color = airQualityInfo.color
    val leafColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
    Spacer(modifier = Modifier.padding(top = 4.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.air_quality_index),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = buildAnnotatedString {
                        append("$airQualityValue • ")
                        withStyle(style = SpanStyle(color = color)) {
                            append(description)
                        }
                    },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_airquality),
                contentDescription = stringResource(id = R.string.air_quality_index),
                tint = leafColor,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.weight(0.5f))

            CircularProgressBar(
                percentage = airQualityPercentage.coerceIn(0f, 1f),
                number = airQualityValue,
                radius = 40.dp,
                progressColor = color,
                strokeWidth = 8.dp,
                fontSize = 16.sp
            )
        }
    }
}



