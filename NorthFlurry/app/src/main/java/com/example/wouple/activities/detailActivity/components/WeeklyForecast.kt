package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Spir
import com.example.wouple.ui.theme.Whitehis
import java.time.LocalDate
import java.util.Locale


@Composable
fun WeeklyForecast(
    temp: TemperatureResponse
) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            Color(0xFF3F54BE),
            Color(0xFF3F54BE)
        )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
        )
    }
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 14.dp)
            .fillMaxWidth()
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(brush = Brush.verticalGradient(background))
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 3.dp),
                text = stringResource(id = R.string.Upcoming_Days),
                color = Whitehis,
                fontWeight = FontWeight.Light,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Icon(
                painter = painterResource(id = R.drawable.arrowdropdown),
                contentDescription = null,
                tint = Spir.copy(alpha = 0.9f),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(30.dp)
            )

            Icon(
                painter = painterResource(id = R.drawable.arrowdropup),
                contentDescription = null,
                tint = Spir,
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .size(30.dp)
            )
        }
        for (days in 0 until temp.daily.time.size) {
            val daysOfWeek = temp.daily.time[days]
            val localDay = LocalDate.parse(daysOfWeek).dayOfWeek
            val localizedDayName = getLocalizedDayName(localDay)
            val forecastMin = temp.daily.temperature_2m_min[days].toInt()
            val forecastMax = temp.daily.temperature_2m_max[days].toInt()
            val weatherCondition = when (temp.daily.weathercode[days]) {
                0, 1 -> WeatherCondition.SUNNY
                2 -> WeatherCondition.PARTLY_CLOUDY
                3, 4 -> WeatherCondition.CLOUDY
                in listOf(
                    51,
                    53,
                    55,
                    56,
                    57,
                    61,
                    63,
                    65,
                    66,
                    67,
                    80,
                    81,
                    82
                ) -> WeatherCondition.RAINY

                in listOf(71, 73, 75, 77) -> WeatherCondition.SNOWY
                in listOf(95, 96, 99) -> WeatherCondition.THUNDERSTORM
                else -> WeatherCondition.SUNNY // default weather condition in case of an unknown code
            }
            val imageResource = weatherCondition.imageResourceId
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = localizedDayName.lowercase()
                        .replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(locale = Locale.ENGLISH) else it.toString()
                        },
                    fontSize = 16.sp,
                    color = Whitehis.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = 24.dp))
                Text(
                    modifier = Modifier
                        .alignByBaseline()
                        .width(35.dp),
                    text = "$forecastMin°",
                    textAlign = TextAlign.Right,
                    color = Whitehis.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline()
                        .width(35.dp),
                    text = "$forecastMax°",
                    textAlign = TextAlign.Right,
                    color = Whitehis.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
            }
        }
    }
}