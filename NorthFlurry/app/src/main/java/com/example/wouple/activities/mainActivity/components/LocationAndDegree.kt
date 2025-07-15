package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun GetLocationAndDegree(
    temp: TemperatureResponse,
    searchedLocation: MutableState<SearchedLocation?>,
    modifier: Modifier = Modifier
) {
    val isDay = temp.current_weather.is_day == 1

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = getProperDisplayName(searchedLocation.value?.display_name) ?: "N/D",
            fontWeight = FontWeight.Thin,
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            fontFamily = MaterialTheme.typography.headlineLarge.fontFamily,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = temp.current_weather.temperature.toInt()
                .toString() + temp.hourly_units.temperature_2m[0],
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Thin,
            fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
            fontSize = 64.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.padding(top = 12.dp))

        WeatherAnimation(weatherCode = temp.current_weather.weathercode, isDay = isDay)

        Spacer(modifier = Modifier.height(30.dp))
    }
}


private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@Composable
fun WeatherAnimation(weatherCode: Int, isDay: Boolean) {
    Box(modifier = Modifier.padding(end = 8.dp)) {
        when {
            weatherCode in listOf(0, 1) && !isDay -> LottieAnimationClear()
            weatherCode in listOf(0, 1) && isDay -> LottieAnimationSun()
            weatherCode == 2 && isDay -> LottieAnimationPartlyCloudy()
            weatherCode == 2 && !isDay -> LottieAnimationPartlyCloudyNight()
            weatherCode == 3 -> LottieAnimationCloud()
            weatherCode in listOf(45, 48) -> LottieAnimationFoggy()
            weatherCode in listOf(
                51,
                53,
                55,
                61,
                63,
                65,
                66,
                67,
                80,
                81,
                82
            ) && isDay -> LottieAnimationRainDayLight()

            weatherCode in listOf(
                51,
                53,
                55,
                61,
                63,
                65,
                66,
                67,
                80,
                81,
                82
            ) && !isDay -> LottieAnimationRainNight()

            weatherCode in listOf(85, 86) && isDay -> LottieAnimationSnowDaylight()
            weatherCode in listOf(85, 86) && !isDay -> LottieAnimationSnowNight()
            weatherCode in listOf(95, 96) -> LottieAnimationThunderStorm()
            else -> if (isDay) LottieAnimationRainDayLight() else LottieAnimationRainNight()
        }
    }
}