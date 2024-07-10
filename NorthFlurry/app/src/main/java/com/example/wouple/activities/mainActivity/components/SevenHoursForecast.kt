package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wouple.elements.GetHourlyWeatherInfo
import com.example.wouple.elements.SevenHoursCardNotification
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay

@Composable
fun GetSevenHoursForecast(temp: TemperatureResponse) {
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(visible) {
        delay(500)
        visible = true
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(bottom = 18.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        val isDay = temp.current_weather.is_day == 1
        val backgroundColor: List<Color> = if (isDay) {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColor))
        ) {
            SevenHoursCardNotification(temp)
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp, end = 4.dp, start = 4.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GetHourlyWeatherInfo(temp)
            }
        }
    }
}
