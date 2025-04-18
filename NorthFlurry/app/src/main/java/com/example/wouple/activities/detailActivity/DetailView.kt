package com.example.wouple.activities.detailActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.wouple.activities.detailActivity.components.ConfettiView
import com.example.wouple.activities.detailActivity.components.CurrentAirQualityCard
import com.example.wouple.activities.detailActivity.components.CurrentUvIndex
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
    val pagerState = rememberPagerState()
    val weatherDetails = getWeatherDetails(temp)
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CustomTopAppBar(
                // onBackPressed = onBackPressed,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        ConfettiView(
            explodeConfetti = explodeConfetti,
            explodeConfettiCallback = { setExplodeConfetti(true) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceDim)
                    .padding(innerPadding),
                horizontalAlignment = CenterHorizontally
            ) {
                item {
                    LocationView(temp = temp, searchedLocation = searchedLocation)
                    Spacer(modifier = Modifier.padding(top = 8.dp))
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
                    DayLightDuration(
                        temp = temp,
                        explodeConfettiCallback = { setExplodeConfetti(true) })
                }
                item {
                    HorizontalPager(state = pagerState, count = 6, modifier = Modifier)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    val collapsed = 22
    val expanded = 0
    val topAppBarTextSize =
        (collapsed + (expanded - collapsed) * (1 - scrollBehavior.state.collapsedFraction)).sp
    val iconCollapsed = 32
    val iconExpanded = 0
    val appBarIconSize =
        (iconCollapsed + (iconExpanded - iconCollapsed) * (1 - scrollBehavior.state.collapsedFraction)).dp

    MediumTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(appBarIconSize),
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    modifier = Modifier.padding(start = 13.dp),
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Light,
                    fontSize = topAppBarTextSize,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior
    )
}
