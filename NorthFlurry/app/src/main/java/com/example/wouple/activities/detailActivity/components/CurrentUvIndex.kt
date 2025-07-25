package com.example.wouple.activities.detailActivity.components


import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.utils.getUvColor
import com.example.wouple.model.api.TemperatureResponse
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun CurrentUvIndexCardCompact(temp: TemperatureResponse) {
    val timeZone = temp.timezone
    val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
    val currentHour = currentDateTime.hour
    val uvIndexValue = temp.hourly.uv_index[currentHour].toFloat().coerceAtMost(11f)
    val uvIndexDescriptions = getUvIndexDescription(uvIndexValue.toInt())
    val uvIndexPercentage = uvIndexValue / 11f
    val color = getUvColor(uvIndexValue)
    val daylight = temp.current_weather.is_day
    val sunColor = Color(0xFFFFA000)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer    // surfaceContainerHigh
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = R.string.current_uv_index),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${uvIndexValue.toInt()} • $uvIndexDescriptions",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 19.sp
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
// here is my idea, if it is night at the location show moon icon if it is daylight show normal sun what you show here
            if (daylight == 1) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sun),
                    contentDescription = stringResource(id = R.string.current_uv_index),
                    tint = sunColor,
                    modifier = Modifier
                        .size(36.dp)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_moon),
                    contentDescription = stringResource(id = R.string.current_uv_index),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(34.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))
            CircularProgressBar(
                percentage = uvIndexPercentage,
                number = uvIndexValue.toInt(),
                radius = 40.dp,
                progressColor = color,
                strokeWidth = 8.dp,
                fontSize = 16.sp
            )

        }
    }
}