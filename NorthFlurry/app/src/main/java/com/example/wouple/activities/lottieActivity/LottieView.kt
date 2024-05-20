package com.example.wouple.activities.lottieActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Dark20

@Composable
fun LottieView(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF3F54BE) // Color(0xFF494CC6)
        val lighterShades = listOf(
            baseColor,
            baseColor.copy(alpha = 0.9f),
            baseColor.copy(alpha = 0.8f),
            baseColor.copy(alpha = 0.5f),
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
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(brush = Brush.verticalGradient(background)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = com.example.wouple.R.string.LottieFilesCopyright),
            fontWeight = FontWeight.Light,
            color = Dark20,
            fontSize = 14.sp
        )
    }
}