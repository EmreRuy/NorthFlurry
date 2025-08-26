package com.example.wouple.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.activities.detailActivity.components.WeatherCondition
import com.example.wouple.model.api.TemperatureResponse
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun GetHourlyWeatherInfo(temp: TemperatureResponse) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val timeZone = temp.timezone
        val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
        val currentHour = currentDateTime.hour

        // Loop through the next 7 hours
        for (index in currentHour..(currentHour + 6)) {
            val dateTimeString = temp.hourly.time[index]
            val hourOnly = dateTimeString.substring(11, 13)

            val temperature = temp.hourly.temperature_2m[index].toInt().toString()
            val isDaytime = temp.hourly.is_day.getOrNull(index) == 1

            // Determine weather condition based on code and day/night
            val hourlyWeatherCondition = when (temp.hourly.weathercode[index]) {
                0, 1 -> if (isDaytime) WeatherCondition.SUNNY else WeatherCondition.CLEAR_NIGHT
                2 -> if (isDaytime) WeatherCondition.PARTLY_CLOUDY else WeatherCondition.CLOUDY
                3 -> WeatherCondition.CLOUDY
                45, 48 -> WeatherCondition.FOGGY
                51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINY
                71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                95, 96, 99 -> WeatherCondition.THUNDERSTORM
                else -> WeatherCondition.RAINY
            }

            GetSixHours(hourOnly, temperature, hourlyWeatherCondition)
        }
    }
}

@Composable
fun GetSixHours(
    time: String,
    temperature: String,
    hourlyWeatherCondition: WeatherCondition
) {
    Column(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = time, // Hour only
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 15.sp,
            fontWeight = FontWeight.Light
        )
        Image(
            painter = painterResource(id = hourlyWeatherCondition.imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = "$temperature°",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}
