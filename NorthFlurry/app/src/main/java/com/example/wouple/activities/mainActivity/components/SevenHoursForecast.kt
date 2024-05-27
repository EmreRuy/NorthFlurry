package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wouple.elements.GetHourlyWeatherInfo
import com.example.wouple.elements.SevenHoursCardNotification
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun GetSevenHoursForecast(temp: TemperatureResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp,
    ) {
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF494CC6)
            val lighterShades = listOf(
                baseColor,
                baseColor.copy(alpha = 0.9f),
                baseColor.copy(alpha = 0.8f),
            )

            lighterShades
        } else {
            listOf(
                Color(0xFF1D244D),
                Color(0xFF2E3A59),
                Color(0xFF3F5066),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = Brush.verticalGradient(background))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {
                GetHourlyWeatherInfo(temp)
            }
        }
        SevenHoursCardNotification(temp)
    }
}