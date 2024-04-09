package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.activities.detailActivity.Hours
import com.example.wouple.activities.detailActivity.WeatherCondition
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.vintage
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun getHourlyWeatherInfo(temp: TemperatureResponse){
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Center
    ) {
        val timeZone = temp.timezone
        val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
        val currentHour = currentDateTime.hour
        for (index in currentHour..(currentHour + 6)) {
            val time = DateFormatter.formatDate(temp.hourly.time[index])
            val temperature = temp.hourly.temperature_2m[index].toInt().toString()
            val isDaytime = temp.hourly.is_day.getOrNull(index) == 1
            if (isDaytime) {
                val hourlyWeatherCondition = when (temp.hourly.weathercode[index]) {
                    0, 1 -> WeatherCondition.SUNNY
                    2 -> WeatherCondition.PARTLYCLOUDY
                    3 -> WeatherCondition.CLOUDY
                    51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82, 95, 96, 99 -> WeatherCondition.RAINY
                    71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                    else -> WeatherCondition.SUNNY
                }
                getSixHours(time, temperature, hourlyWeatherCondition)
            }
            if (!isDaytime) {
                val hourlyWeatherConditionNight = when (temp.hourly.weathercode[index]) {
                    0, 1 -> WeatherCondition.CLEARNIGHT
                    2, 3 -> WeatherCondition.CLOUDY
                    51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINY
                    else -> {
                        WeatherCondition.CLEARNIGHT
                    }
                }
                getSixHours(time, temperature, hourlyWeatherConditionNight)
            }
        }

    }
}
@Composable
fun getSixHours(
    time: String,
    temperature: String,
    hourlyWeatherCondition: WeatherCondition
) {
    Column(
        modifier = Modifier.padding(top = 32.dp, start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = time,
            color = Spiro,
            fontSize = 15.sp
        )
        Image(
            painter = painterResource(id = hourlyWeatherCondition.imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(26.dp)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp,),
            text = "$temperature°",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}