package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            SevenHoursCardNotification(temp)
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 32.dp, end = 4.dp, start = 4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GetHourlyWeatherInfo(temp)
            }
        }
    }
}
