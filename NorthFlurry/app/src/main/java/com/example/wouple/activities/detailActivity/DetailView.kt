package com.example.wouple.activities.detailActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.example.wouple.activities.detailActivity.components.ConfettiView
import com.example.wouple.activities.detailActivity.components.CurrentAirQualityCard
import com.example.wouple.activities.detailActivity.components.CurrentUvIndex
import com.example.wouple.activities.detailActivity.components.DayLightDuration
import com.example.wouple.activities.detailActivity.components.ExtraCards
import com.example.wouple.activities.detailActivity.components.HourlyForecast
import com.example.wouple.activities.detailActivity.components.LocationView
import com.example.wouple.activities.detailActivity.components.PagerIndicator
import com.example.wouple.activities.detailActivity.components.TopBar
import com.example.wouple.activities.detailActivity.components.UvIndexChart
import com.example.wouple.activities.detailActivity.components.WeeklyForecast
import com.example.wouple.activities.detailActivity.components.WeeklyPrecipitationChart
import com.example.wouple.activities.detailActivity.components.getWeatherDetails
import com.example.wouple.activities.detailActivity.components.openMetActivity.GetAttributionForOpenMeteo
import com.example.wouple.model.api.AirQuality


@Composable
fun DetailView(
    temp: TemperatureResponse,
    searchedLocation: SearchedLocation,
    air: AirQuality?,
    onBackPressed: () -> Unit
) {
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF3F54BE)
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
    val (explodeConfetti, setExplodeConfetti) = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val weatherDetails = getWeatherDetails(temp)

    ConfettiView(
        explodeConfetti = explodeConfetti,
        explodeConfettiCallback = { setExplodeConfetti(true) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(colors = background)),
            horizontalAlignment = CenterHorizontally
        ) {
            item {
                TopBar { onBackPressed() }
            }
            item {
                LocationView(temp = temp, searchedLocation = searchedLocation)
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
            item {
                CurrentAirQualityCard(temp = temp, air = air)
            }
            item {
                CurrentUvIndex(temp = temp)
            }
            item {
                UvIndexChart(temp = temp)
            }
            item {
                HourlyForecast(temp = temp)
            }
            item {
                WeeklyForecast(temp = temp)
            }
            item {
                WeeklyPrecipitationChart(temp = temp)
            }
            item {
                DayLightDuration(temp = temp, explodeConfettiCallback = { setExplodeConfetti(true) })
            }
            item {
                HorizontalPager(state = pagerState, count = 6, modifier = Modifier)
                { page ->
                    when (page) {
                        0 -> ExtraCards(
                            text = stringResource(id = R.string.FeelsLike),
                            numbers = temp.hourly.apparent_temperature.getOrNull(weatherDetails.feelsLike)
                                ?.let {
                                    it.toInt().toString() + temp.hourly_units.apparent_temperature
                                } ?: "N/D",
                            temp = temp
                        )

                        1 -> ExtraCards(
                            text = stringResource(id = R.string.Rainfall),
                            numbers = weatherDetails.rainFall.toString() + temp.daily_units.precipitation_sum,
                            temp = temp
                        )

                        2 -> ExtraCards(
                            text = stringResource(id = R.string.WindSpeed),
                            numbers = weatherDetails.windSpeed.toString() + temp.hourly_units.windspeed_10m,
                            temp = temp
                        )

                        3 -> ExtraCards(
                            text = stringResource(id = R.string.Visibility),
                            numbers = weatherDetails.visibilityInMeters.toString() + temp.hourly_units.visibility,
                            temp = temp
                        )

                        4 -> ExtraCards(
                            text = stringResource(id = R.string.Humidity),
                            numbers = temp.hourly_units.relativehumidity_2m + weatherDetails.humidity.toString(),
                            temp = temp
                        )

                        5 -> ExtraCards(
                            text = stringResource(id = R.string.DewPoint),
                            numbers = weatherDetails.dewPoint.toString() + temp.hourly_units.temperature_2m,
                            temp = temp
                        )
                    }
                }
                PagerIndicator(
                    step = pagerState.currentPage,
                    totalSteps = pagerState.pageCount
                )
                GetAttributionForOpenMeteo()
            }
        }
    }
}
