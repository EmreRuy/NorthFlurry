package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun LocationView(
    temp: TemperatureResponse,
    searchedLocation: SearchedLocation

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        val color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(320.dp)
                .align(Alignment.Center)
        ) {
            val width = size.width
            val height = size.height
            val curveHeight = 150f
            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(0f, height - curveHeight)
                    quadraticTo(
                        x1 = width / 2f,
                        y1 = height + curveHeight,
                        x2 = width,
                        y2 = height - curveHeight
                    )
                    lineTo(width, 0f)
                    close()
                },
                color = color
            )
        }
        Column(
            modifier = Modifier
                .padding(WindowInsets.statusBars.asPaddingValues())
                .fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(top = 32.dp))
            Text(
                text = getProperDisplayName(searchedLocation.display_name) ?: "N/D",
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Thin,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 50.sp
                )
            )
            Spacer(modifier = Modifier.padding(top = 24.dp))
            Text(
                text = "${temp.current_weather.temperature.toInt()}°",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(start = 4.dp),
                fontWeight = FontWeight.Thin,
                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                fontSize = 64.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 24.dp))
            val weatherCode = temp.current_weather.weathercode
            val weatherDescriptionResId = getWeatherDescriptionResId(weatherCode)
            val weatherDescription = stringResource(id = weatherDescriptionResId)
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val day = 0
                val forecastMini = temp.daily.temperature_2m_min[day].toInt().toString()
                val maximumDegree = temp.daily.temperature_2m_max[day].toInt().toString()
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.arrowdropdown),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = forecastMini + temp.hourly_units.temperature_2m.firstOrNull(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.weight(0.9f))
                Text(
                    text = weatherDescription,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 19.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = maximumDegree + temp.hourly_units.temperature_2m.firstOrNull(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                )
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.arrowdropup),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()
