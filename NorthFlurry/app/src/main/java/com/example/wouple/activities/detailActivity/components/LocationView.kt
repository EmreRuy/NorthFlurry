package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Whitehis

@Composable
fun LocationView(
    temp: TemperatureResponse,
    searchedLocation: SearchedLocation,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isDay = temp.current_weather.is_day == 1
        val color = if (isDay) Whitehis else Color.White
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            text = getProperDisplayName(searchedLocation.display_name) ?: "N/D",
            fontWeight = FontWeight.Thin,
            fontSize = 50.sp,
            color = color,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "${temp.current_weather.temperature.toInt()}°",
            color = color,
            modifier = Modifier.padding(start = 4.dp),
            fontWeight = FontWeight.Thin,
            fontSize = 64.sp,
            textAlign = TextAlign.Center
        )
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
                tint = color
            )
            Text(
                text = forecastMini + temp.hourly_units.temperature_2m.firstOrNull(),
                color = color,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weatherDescription,
                color = color,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = maximumDegree + temp.hourly_units.temperature_2m.firstOrNull(),
                color = color,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.arrowdropup),
                contentDescription = null,
                tint = color
            )
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()