package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun GetLocationAndDegree(
    temp: TemperatureResponse,
    searchedLocation: MutableState<SearchedLocation?>,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onSettingsClicked: (TemperatureResponse) -> Unit
) {
    val isDay = temp.current_weather.is_day == 1
    Column(
        Modifier.padding(top = 70.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getProperDisplayName(searchedLocation.value?.display_name) ?: "N/D",
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = temp.current_weather.temperature.toInt()
                .toString() + temp.hourly_units.temperature_2m[0],
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Thin,
            fontSize = 50.sp,
            color = Color.White,
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        when {
            (temp.current_weather.weathercode in listOf(0, 1) && !isDay) -> LottieAnimationClear()
            (temp.current_weather.weathercode in listOf(0, 1) && isDay) -> LottieAnimationSun()
            (temp.current_weather.weathercode == 2 && isDay) -> LottieAnimationPartlyCloudy()
            (temp.current_weather.weathercode == 2 && !isDay) -> LottieAnimationPartlyCloudyNight()
            (temp.current_weather.weathercode == 3) -> LottieAnimationCloud()
            (temp.current_weather.weathercode in listOf(51, 53, 55, 61, 63, 65, 66, 67, 80, 81, 82)) && isDay-> LottieAnimationRainDayLight()
            (temp.current_weather.weathercode in listOf(51, 53, 55, 61, 63, 65, 66, 67, 80, 81, 82)) && !isDay-> LottieAnimationRainNight()
            (temp.current_weather.weathercode in listOf(85, 86)) && isDay -> LottieAnimationSnowDaylight()
            (temp.current_weather.weathercode in listOf(85, 86)) && !isDay -> LottieAnimationSnowNight()
            (temp.current_weather.weathercode in listOf(95,96, 96)) -> LottieAnimationThunderStorm()
            else -> LottieAnimationSun()
        }
        Row(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //For Settings Button
            SettingsButton { onSettingsClicked(temp) }
            // For Forecast Detail Button
            DetailButton {
                onDetailsButtonClicked(temp)
            }
            Spacer(modifier = Modifier.width(58.dp))
        }
        // Horizontal waves
        Spacer(modifier = Modifier.height(30.dp))
        GetHorizontalWaveView()
    }
}
private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@Composable
private fun GetHorizontalWaveView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalWave(
            phase = rememberPhaseState(0f),
            alpha = 1f,
            amplitude = 50f,
            frequency = 0.5f,
            gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
        )
        HorizontalWave(
            phase = rememberPhaseState(15f),
            alpha = 0.5f,
            amplitude = 80f,
            frequency = 0.3f,
            gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.2f,
            amplitude = 40f,
            frequency = 0.6f,
            gradientColors = listOf(Color(0xFFFFFFFF), Color(0xFFFFFFFF))
        )
    }
}
