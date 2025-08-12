package com.example.wouple.activities.detailActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import androidx.compose.ui.res.stringResource
import com.example.wouple.activities.detailActivity.components.ConfettiView
import com.example.wouple.activities.detailActivity.components.CurrentAirQualityCardCompact
import com.example.wouple.activities.detailActivity.components.CurrentUvIndexCardCompact
import com.example.wouple.activities.detailActivity.components.DayLightDuration
import com.example.wouple.activities.detailActivity.components.ExtraCards
import com.example.wouple.activities.detailActivity.components.HourlyForecast
import com.example.wouple.activities.detailActivity.components.LocationView
import com.example.wouple.activities.detailActivity.components.PagerIndicator
import com.example.wouple.activities.detailActivity.components.UvIndexChart
import com.example.wouple.activities.detailActivity.components.WeeklyForecast
import com.example.wouple.activities.detailActivity.components.WeeklyPrecipitationChart
import com.example.wouple.activities.detailActivity.components.getWeatherDetails
import com.example.wouple.activities.detailActivity.components.openMetActivity.GetAttributionForOpenMet
import com.example.wouple.model.api.AirQuality


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    temp: TemperatureResponse,
    searchedLocation: SearchedLocation,
    air: AirQuality?,
) {
    val (explodeConfetti, setExplodeConfetti) = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { 6 })
    val weatherDetails = getWeatherDetails(temp)
    Scaffold { innerPadding ->
        ConfettiView(
            explodeConfetti = explodeConfetti,
            explodeConfettiCallback = { setExplodeConfetti(true) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        //   Color(0xFF1F2B2F) // For the Dark Mode
                        MaterialTheme.colorScheme.surfaceContainer
                    )
                    .padding(innerPadding.let { innerPadding -> 0.dp }),
                horizontalAlignment = CenterHorizontally
            ) {
                item {
                    LocationView(temp = temp, searchedLocation = searchedLocation)
                }
                item {
                    CurrentAirQualityCardCompact(air = air)
                }
                item {
                    CurrentUvIndexCardCompact(temp = temp)
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
                    DayLightDuration(
                        temp = temp
                    )
                }
                item {
                    HorizontalPager(state = pagerState, modifier = Modifier)
                    { page ->
                        when (page) {
                            0 -> ExtraCards(
                                text = stringResource(id = R.string.FeelsLike),
                                numbers = temp.hourly.apparent_temperature.getOrNull(weatherDetails.feelsLike)
                                    ?.let {
                                        it.toInt()
                                            .toString() + temp.hourly_units.apparent_temperature
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
                    GetAttributionForOpenMet(searchedLocation = searchedLocation)
                }
            }
        }
    }
}
