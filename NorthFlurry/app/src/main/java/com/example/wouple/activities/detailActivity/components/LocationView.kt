package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
    val locationName = getProperDisplayName(searchedLocation.display_name) ?: "N/D"
    val fontSize = if (locationName.length > 15) 36.sp else 50.sp
    val lineHeight = if (locationName.length > 15) 34.sp else 50.sp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
    ) {
        val primaryColor = MaterialTheme.colorScheme.primary
        // Background curved shape
        Canvas(
            modifier = Modifier
                .fillMaxSize()
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
                color = primaryColor
            )
        }

        // Foreground content (centered inside the Box)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-25).dp)
                .padding(WindowInsets.statusBars.asPaddingValues()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Location Name
            Text(
                text = locationName,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center,
                lineHeight = lineHeight,
                fontSize = fontSize,
                fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Temperature
            Text(
                text = "${temp.current_weather.temperature.toInt()}°",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Thin,
                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                fontSize = 64.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Weather info row
            val weatherCode = temp.current_weather.weathercode
            val weatherDescriptionResId = getWeatherDescriptionResId(weatherCode)
            val weatherDescription = stringResource(id = weatherDescriptionResId)
            val day = 0
            val forecastMini = temp.daily.temperature_2m_min[day].toInt().toString()
            val maximumDegree = temp.daily.temperature_2m_max[day].toInt().toString()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.arrowdropdown),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = forecastMini + temp.hourly_units.temperature_2m.firstOrNull(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = weatherDescription,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 19.sp
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = maximumDegree + temp.hourly_units.temperature_2m.firstOrNull(),
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
