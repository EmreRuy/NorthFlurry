package com.example.wouple.activities.detailActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.formatter.DateFormatter
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Corn
import com.example.wouple.ui.theme.PagerColor
import com.example.wouple.ui.theme.Spir
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.wouple.elements.CustomBarChart
import com.example.wouple.elements.CustomPrecipitationBarChart
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.preferences.PrecipitationUnitPref
import com.example.wouple.ui.theme.mocassin
import com.example.wouple.ui.theme.vintage
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailView(
    temp: TemperatureResponse,
    searchedLocation: SearchedLocation,
    air: AirQuality?,
    onBackPressed: () -> Unit
) {
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
    val (explodeConfetti, setExplodeConfetti) = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    ConfettiView(
        explodeConfetti = explodeConfetti,
        explodeConfettiCallback = { setExplodeConfetti(true) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(colors = background))
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            Icons.Default.ArrowBack, contentDescription = "Back",
                            modifier = Modifier.size(32.dp),
                            tint = White
                        )
                    }
                },
                backgroundColor = Transparent,
                elevation = 0.dp,
                title = { }
            )
            val index =
                temp.hourly.time.map { LocalDateTime.parse(it).hour }
                    .indexOf(LocalDateTime.now().hour)
            val feelsLike = index.let { temp.hourly.apparent_temperature[it].toInt() }
            val humidity = index.let { temp.hourly.relativehumidity_2m[it] }
            val dewPoint = index.let { temp.hourly.dewpoint_2m[it].toInt() }
            val rainIndex =
                temp.daily.time.map { LocalDate.parse(it).dayOfWeek }
                    .indexOf(LocalDate.now().dayOfWeek)
            val rainFall = rainIndex.let { temp.daily.rain_sum[rainIndex].toInt() }
            val visibilityInMeters = index.let { temp.hourly.visibility[it].toInt() }
            val windSpeed = index.let { temp.hourly.windspeed_10m[it].toInt() }
            LocationView(temp, searchedLocation)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            CurrentAirQualityCard(temp, air)
            CurrentUvIndex(temp)
            UvChartViewCard(temp)
            HourlyForecastView(temp)
            WeeklyForeCastView(temp)
            WeeklyChart(temp)
            DayLightDurationCard(temp, explodeConfettiCallback = { setExplodeConfetti(true) })
            val pagerState = rememberPagerState()
            HorizontalPager(state = pagerState, count = 6, modifier = Modifier)
            { page ->
                when (page) {
                    0 -> ExtraCards(
                        text = stringResource(id = R.string.FeelsLike),
                        numbers = temp.hourly.apparent_temperature.getOrNull(feelsLike)?.let {
                            it.toInt().toString() + temp.hourly_units.apparent_temperature
                        } ?: "N/D",
                        //  icon = painterResource(id = R.drawable.ic_term),
                        temp = temp
                    )

                    1 -> ExtraCards(
                        text = stringResource(id = R.string.Rainfall),
                        numbers = rainFall.toString() + temp.daily_units.precipitation_sum,
                        //  icon = painterResource(id = R.drawable.ic_drop),
                        temp = temp
                    )

                    2 -> ExtraCards(
                        text = stringResource(id = R.string.WindSpeed),
                        numbers = windSpeed.toString() + temp.hourly_units.windspeed_10m,
                        //  icon = painterResource(id = R.drawable.ic_wind),
                        temp = temp
                    )

                    3 -> ExtraCards(
                        text = stringResource(id = R.string.Visibility),
                        numbers = visibilityInMeters.toString() + temp.hourly_units.visibility,
                        //  icon = painterResource(id = R.drawable.ic_visibility),
                        temp = temp
                    )

                    4 -> ExtraCards(
                        text = stringResource(id = R.string.Humidity),
                        numbers = temp.hourly_units.relativehumidity_2m + humidity.toString(),
                        //   icon = painterResource(id = R.drawable.ic_humidity),
                        temp = temp
                    )

                    5 -> ExtraCards(
                        text = stringResource(id = R.string.DewPoint),
                        numbers = dewPoint.toString() + temp.hourly_units.temperature_2m,
                        //  icon = painterResource(id = R.drawable.ic_drop),
                        temp = temp
                    )
                }
            }
            HorizontalPagerIndicator(
                step = pagerState.currentPage,
                totalSteps = pagerState.pageCount
            )
        }
    }
}

@Composable
private fun ConfettiView(
    explodeConfetti: Boolean,
    explodeConfettiCallback: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box {
        content()
        if (explodeConfetti) {
            explodeConfettiCallback()
            val party = Party(
                speed = 0f,
                maxSpeed = 40f,
                damping = 0.9f,
                spread = 720,
                colors = listOf(
                    0xFF4CAF50.toInt(),
                    0xFF2196F3.toInt(),
                    0xFFFFC107.toInt(),
                    0xFF9C27B0.toInt(),
                    0xFFFF5722.toInt(),
                    0xFF8BC34A.toInt(),  // Light Green
                    0xFF9C27B0.toInt(),  // Deep Purple
                    0xFFCDDC39.toInt(),  // Lime
                    0xFFFCD00,
                    0xF1D71F2,
                    0xF1D244D,
                    0xF3F5066,
                    0xFFFFA500.toInt()
                ),

                emitter = Emitter(duration = 200, TimeUnit.MILLISECONDS).max(200),
                position = Position.Relative(0.5, 0.3)
            )
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = listOf(party),
            )
        }
    }
}

@Composable
private fun HorizontalPagerIndicator(step: Int, totalSteps: Int) {

    @Composable
    fun Dot(isSelected: Boolean) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
                .clip(CircleShape)
                .background(
                    brush = if (isSelected) {
                        Brush.linearGradient(
                            colors = listOf(Color(0xFFADD8E6), Color(0xFF87CEEB))
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(PagerColor, Transparent)
                        )
                    }
                )
                .width(if (isSelected) 14.dp else 8.dp)
                .height(8.dp)
        )
    }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(totalSteps) {
            if (it == step) {
                Dot(true)
            } else {
                Dot(false)
            }
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@Composable
fun LocationView(
    temp: TemperatureResponse,
    searchedLocation: SearchedLocation,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(horizontal = 16.dp),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isDay = temp.current_weather.is_day == 1
        val color = if (isDay) Whitehis else White
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            text = getProperDisplayName(searchedLocation.display_name) ?: "N/D",
            fontWeight = FontWeight.Thin,
            fontSize = 50.sp,
            color = color,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "${temp.current_weather.temperature.toInt()}°",
            color = color,
            modifier = Modifier.padding(start = 4.dp),
            fontWeight = FontWeight.Thin,
            fontSize = 64.sp,
            textAlign = TextAlign.Center
        )
        val weatherDescriptions = mapOf(
            0 to "Clear Sky",
            1 to "Mainly Clear",
            2 to "Partly Cloudy",
            3 to "Overcast",
            45 to "Foggy",
            48 to "Rime Fog",
            51 to "Light Drizzle",
            53 to "Moderate Drizzle",
            55 to "Heavy Drizzle",
            56 to "Light Freezing Drizzle",
            57 to "Heavy Freezing Drizzle",
            61 to "Slight Rain",
            63 to "Moderate Rain",
            65 to "Heavy Rain",
            66 to "Light Freezing Rain",
            67 to "Heavy Freezing Rain",
            71 to "Light Snowfall",
            73 to "Moderate Snowfall",
            75 to "Heavy Snowfall",
            77 to "Snow Grains",
            80 to "Slight Rain Showers",
            81 to "Moderate Rain Showers",
            82 to "Heavy Rain Showers",
            85 to "Slight Snow Showers",
            86 to "Heavy Snow Showers",
            95 to "Thunderstorm"
        )
        Spacer(modifier = Modifier.padding(8.dp))
        val weatherCode = temp.current_weather.weathercode
        val weatherDescription = weatherDescriptions[weatherCode] ?: "Unknown"
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val day = 0
            val forecastMini = temp.daily.temperature_2m_min[day].toInt().toString()
            val maximumDegree = temp.daily.temperature_2m_max[day].toInt().toString()
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.arrowdropdown),
                contentDescription = null,
                tint = color
            )
            Text(
                text = forecastMini + temp.hourly_units.temperature_2m.firstOrNull(),
                color = color,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weatherDescription,
                color = color,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = maximumDegree + temp.hourly_units.temperature_2m.firstOrNull(),
                color = color,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light
            )
            Icon(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = R.drawable.arrowdropup),
                contentDescription = null,
                tint = color
            )
        }
    }
}

@Composable
private fun WeeklyChart(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            // Color(0xFF4067DD),
            // Color(0xFF4067DD),
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(16.dp)
    ) {
        WeeklyShowersChartView(temp = temp)
    }
}

@Composable
fun WeeklyShowersChartView(temp: TemperatureResponse) {
    val context = LocalContext.current
    val precipitationSum = temp.daily.precipitation_sum.take(7)
    val maxRainSum = precipitationSum.maxOrNull()?.toFloat() ?: return
    val daysOfWeek = (0 until 7).map {
        LocalDate.now().plusDays(it.toLong()).dayOfWeek.name.substring(0, 3)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Precipitation_For_Upcoming_Days),
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            color = White
        )
        Spacer(modifier = Modifier.weight(1f))
        PopUpViewForPrecipitation(temp)
        val minSumForShowingGraph =
            if (PrecipitationUnitPref.getPrecipitationUnit(context) == PrecipitationUnit.MM) 0.1 else 0.01
        if (maxRainSum <= minSumForShowingGraph) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.No_Precipitation_expected_for_the_week),
                fontWeight = FontWeight.Light,
                fontSize = 15.sp,
                color = Spiro
            )
        } else {
            Row(
                modifier = Modifier
                    .height(170.dp)
                    .drawBehind {
                        // draws X-Axis
                        drawLine(
                            color = White,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height)
                        )
                    },
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                precipitationSum.forEach { value ->
                    CustomPrecipitationBarChart(size = value.toFloat(), max = maxRainSum)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                daysOfWeek.forEach { label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .width(10.dp), contentAlignment = Center
                    ) {
                        Text(
                            text = label,
                            color = White,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopUpViewForPrecipitation(temp: TemperatureResponse) {
    var popupVisible by remember { mutableStateOf(false) }
    if (popupVisible) {
        PopUpContent(
            title = stringResource(id = R.string.Weekly_precipitation_chart),
            text = stringResource(id = R.string.Explainer_For_Precipitation),
            onDismiss = { popupVisible = false },
            temp = temp
        )
    }
    IconButton(onClick = { popupVisible = true }) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Show Popup",
            tint = White
        )
    }
}

@Composable
fun UvChartViewCard(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            // Color(0xFF4067DD),
            // Color(0xFF4067DD),
            Color(0xFF3D52BB),
            Color(0xFF3D52BB)
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(12.dp),
    ) {
        UvChartView(temp = temp)
    }
}

@Composable
fun UvChartView(temp: TemperatureResponse) {
    val dailyUv =
        temp.daily.uv_index_max.take(7) // Assuming temp.daily.uv_index_max contains the UV index data as List<Double>
    val maxUv = dailyUv.maxOrNull()?.toFloat() ?: 0f // Compute the maximum UV index value
    val daysOfWeek = (0 until 7).map {
        LocalDate.now().plusDays(it.toLong()).dayOfWeek.name.substring(0, 3)
    }
    Column(
        modifier = Modifier
            .background(Transparent)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.UV_Index_For_Upcoming_Days),
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
            color = White
        )
        Spacer(modifier = Modifier.weight(1f))
        PopUpView(temp)
        Row(
            modifier = Modifier
                .height(170.dp)
                .drawBehind {
                    // draw X-Axis
                    drawLine(
                        color = White,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height)
                    )
                },
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            dailyUv.forEach { value ->
                CustomBarChart(size = value.toFloat(), max = maxUv)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            daysOfWeek.forEach { label ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(10.dp), contentAlignment = Center
                ) {
                    Text(
                        text = label,
                        color = White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun PopUpView(temp: TemperatureResponse) {
    var popupVisible by remember { mutableStateOf(false) }
    if (popupVisible) {
        PopUpContent(
            title = stringResource(id = R.string.The_Ultraviolet_Index),
            text = stringResource(id = R.string.Explainer_Of_Uv_Index),
            onDismiss = { popupVisible = false },
            temp = temp
        )
    }
    IconButton(onClick = { popupVisible = true }) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Show Popup",
            tint = White
        )
    }
}

@Composable
fun PopUpContent(title: String, text: String, onDismiss: () -> Unit, temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Surface(
                modifier = Modifier
                    .padding(24.dp),
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                color = if (isDay) Color(0xFF586FCE) else Color(0xFF3F5066) //Color(0xFF2E3A59)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        color = mocassin
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.body1,
                        fontSize = 16.sp,
                        color = Color(0xFFE0E0E0)
                    )
                }
            }
        }
    )
}

@Composable
fun AirQualityIndex(air: AirQuality?, temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(Transparent),
        horizontalAlignment = CenterHorizontally
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val isDay = temp.current_weather.is_day == 1
            val textColor = if (isDay) Whitehis else White
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_airquality),
                contentDescription = null,
                tint = White
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(id = R.string.air_quality_index).uppercase(),
                textAlign = TextAlign.Start,
                color = textColor,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(1f))
            val aqiValue = air?.current?.european_aqi.toString()
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = aqiValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
            val airQualityDescriptions = mapOf(
                0..20 to "Good",
                20..40 to "Fair",
                40..60 to "Moderate",
                60..80 to "Poor",
                80..100 to "Very Poor",
                100..Int.MAX_VALUE to "Hazardous"
            )
            val airCode = air?.current?.european_aqi ?: 0
            val airQualityDescription = airQualityDescriptions.entries.find { airCode in it.key }
            val descriptionText = airQualityDescription?.value ?: "Unknown"
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = descriptionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
        }
    }
}

@Composable
fun CurrentAirQualityCard(temp: TemperatureResponse, air: AirQuality?) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            Color(0xFF3D52BB),
            //Color(0xFF3D52BB)
            Color(0xFF3954C4),

            )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
        )
    }
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(12.dp),
    ) {
        AirQualityIndex(air, temp)
    }
}

@Composable
fun CurrentUvIndex(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            // Color(0xFF4067DD),
            // Color(0xFF4067DD),
            Color(0xFF3D52BB),
            Color(0xFF3954C4),

            )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            // Color(0xFF3F5066),
        )
    }
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(12.dp),
    ) {
        UvIndex(temp)
    }
}

@Composable
fun UvIndex(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .background(Transparent),
        horizontalAlignment = CenterHorizontally
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val isDay = temp.current_weather.is_day == 1
            val textColor = if (isDay) Whitehis else White
            Icon(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_sun),
                contentDescription = null,
                tint = textColor
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(id = R.string.current_uv_index).uppercase(),
                textAlign = TextAlign.Start,
                color = textColor,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.weight(0.5f))
            val timeZone = temp.timezone
            val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
            val currentHour = currentDateTime.hour
            val aqiValue = temp.hourly.uv_index[currentHour].toInt().toString()
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = aqiValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
            val uvIndexDescriptions = when (temp.hourly.uv_index[currentHour].toInt()) {
                in 0..2 -> "Low"
                in 3..5 -> "Moderate"
                in 6..7 -> "High"
                in 8..10 -> "Very High"
                in 11..Int.MAX_VALUE -> "Extreme"

                else -> {
                    "Unknown"
                }
            }
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = uvIndexDescriptions,
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = textColor
            )
        }
    }
}

@Composable
fun HourlyForecastView(temp: TemperatureResponse) {
    val tabItem = listOf(
        TabItem(
            title = stringResource(id = R.string.Temperature),
        ),
        TabItem(
            title = stringResource(id = R.string.Precipitation),
        )
    )
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
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(16.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.Next_24_Hours),
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        var selectedTabIndex by remember {
            mutableStateOf(0)
        }
        Column {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Transparent,
                contentColor = Whitehis,

                ) {
                tabItem.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp
                            )
                        }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> {
                    TemperatureContent(temp)
                }

                1 -> {
                    PrecipitationContent(temp)
                }
            }
        }
    }
}

data class TabItem(val title: String)

@Composable
fun TemperatureContent(temp: TemperatureResponse) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 0.dp)
            .horizontalScroll(scrollState),
    ) {
        val timeZone = temp.timezone
        val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
        val currentHour = currentDateTime.hour
        for (index in currentHour..(currentHour + 23)) {
            val time = DateFormatter.formatDate(temp.hourly.time[index])
            val temperature = temp.hourly.temperature_2m[index].toInt().toString()
            val isDaytime = temp.hourly.is_day.getOrNull(index) == 1
            if (isDaytime) {
                val hourlyWeatherCondition = when (temp.hourly.weathercode[index]) {
                    0, 1 -> WeatherCondition.SUNNY
                    2 -> WeatherCondition.PARTLY_CLOUDY
                    3 -> WeatherCondition.CLOUDY
                    51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINY
                    71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                    95, 96, 99 -> WeatherCondition.THUNDERSTORM
                    else -> WeatherCondition.SUNNY
                }
                Hours(time, temperature, hourlyWeatherCondition)
            }
            if (!isDaytime) {
                val hourlyWeatherConditionNight = when (temp.hourly.weathercode[index]) {
                    0, 1 -> WeatherCondition.CLEAR_NIGHT
                    2, 3 -> WeatherCondition.CLOUDY
                    51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82 -> WeatherCondition.RAINY
                    71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                    95, 96, 99 -> WeatherCondition.THUNDERSTORM
                    else -> {
                        WeatherCondition.CLEAR_NIGHT
                    }
                }
                Hours(time, temperature, hourlyWeatherConditionNight)
            }
        }

    }
}

@Composable
fun PrecipitationContent(temp: TemperatureResponse) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 0.dp)
            .horizontalScroll(scrollState)
    ) {
        val timeZone = temp.timezone
        val currentDateTime = ZonedDateTime.now(ZoneId.of(timeZone))
        val currentHour = currentDateTime.hour
        for (index in currentHour..(currentHour + 23)) {
            val time = DateFormatter.formatDate(temp.hourly.time[index])
            val precipitationPr = temp.hourly.precipitation_probability[index]
            val hourlyWeatherCondition = when (temp.hourly.weathercode[index]) {
                0, 1 -> WeatherCondition.SUNNY
                2 -> WeatherCondition.PARTLY_CLOUDY
                3 -> WeatherCondition.CLOUDY
                51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82, 95, 96, 99 -> WeatherCondition.RAINY
                71, 73, 75, 77, 85, 86 -> WeatherCondition.SNOWY
                else -> WeatherCondition.SUNNY
            }
            PrecipitationHours(time, precipitationPr, hourlyWeatherCondition)
        }
    }
}

@Composable
fun GetSunRise(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunrise = temp.daily.sunrise.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }
    val formattedSunrise = todaySunrise?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = formattedSunrise,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 30.sp,
            color = Corn.copy(alpha = 0.7f),
        )
    }
}

@Composable
fun GetSunSet(temp: TemperatureResponse) {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunSet = temp.daily.sunset.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }
    val formattedSunset = todaySunSet?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = formattedSunset,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 30.sp,
            color = Corn.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun getFormattedSunset(temp: TemperatureResponse): String {
    val now = LocalDateTime.now().toLocalDate()
    val todaySunSet = temp.daily.sunset.firstOrNull {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .toLocalDate()
            .isEqual(now)
    }
    return todaySunSet?.let {
        LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .format(DateTimeFormatter.ofPattern("HH:mm"))
    } ?: ""
}

@Composable
fun GetDayLength(temp: TemperatureResponse) {
    val now = LocalDate.now()
    val todaySunrise = temp.daily.sunrise
        .firstOrNull {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate() == now
        }
        ?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime() }

    val todaySunset = temp.daily.sunset
        .firstOrNull {
            LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalDate() == now
        }
        ?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME).toLocalTime() }

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val formattedSunrise = todaySunrise?.format(formatter) ?: ""
    val formattedSunset = todaySunset?.format(formatter) ?: ""

    val lengthOfTheDay = if (formattedSunrise.isNotEmpty() && formattedSunset.isNotEmpty()) {
        val length = Duration.between(todaySunrise, todaySunset)
        val hours = length.toHours()
        val minutes = length.minusHours(hours).toMinutes()
        "$hours hours $minutes minutes"
    } else {
        "N/A"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Day_Length),
            fontWeight = FontWeight.Thin,
            fontSize = 28.sp,
            color = White,
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = lengthOfTheDay,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = Whitehis,
        )
    }
}

@Composable
private fun DayLightDurationCard(temp: TemperatureResponse, explodeConfettiCallback: () -> Unit) {
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
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(
                shape = RoundedCornerShape(20.dp),
                brush = Brush.verticalGradient(background)
            )
            .padding(8.dp)
    ) {
        GetDaylightDuration(temp = temp, explodeConfettiCallback)
    }
}


@Composable
private fun GetDaylightDuration(temp: TemperatureResponse, explodeConfettiCallback: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.Daylight_Duration),
            color = Whitehis,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        val formattedSunset = getFormattedSunset(temp)
        if (formattedSunset.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = CenterHorizontally
                ) {
                    GetSunRise(temp)
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.arrowdropup),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            tint = Whitehis.copy(alpha = 0.9f),
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrowdropdown),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            tint = Whitehis.copy(alpha = 0.8f),
                        )
                    }
                    GetSunSet(temp)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = CenterHorizontally
                ) {
                    // LottieAnimationSun()
                    Image(
                        modifier = Modifier
                            .size(70.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    explodeConfettiCallback()
                                }),
                        painter = painterResource(id = R.drawable.logo2),
                        contentDescription = null
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = CenterHorizontally
                ) {
                    GetDayLength(temp)
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(id = R.drawable.baseline_error_outline_24),
                    contentDescription = "error",
                    tint = mocassin
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "No Data Available for Display",
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    color = mocassin,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Hours(
    time: String,
    temperature: String,
    hourlyWeatherCondition: WeatherCondition
) {
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = time,
            color = vintage,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        Image(
            painter = painterResource(id = hourlyWeatherCondition.imageResourceId),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "$temperature°",
            color = Whitehis,
            fontWeight = FontWeight.Medium,
            fontSize = 17.sp
        )
    }
}

@Composable
fun PrecipitationHours(
    time: String,
    precipitationPr: Int,
    hourlyWeatherCondition: WeatherCondition
) {
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = time,
            color = vintage,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
        if (hourlyWeatherCondition == WeatherCondition.SNOWY) {
            Image(
                painter = painterResource(id = R.drawable.myicon),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_drop_hollow),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        Text(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            text = "%$precipitationPr",
            color = Spiro,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

enum class WeatherCondition(val imageResourceId: Int) {
    SUNNY(R.drawable.ic_sun),
    RAINY(R.drawable.rainyday),
    CLOUDY(R.drawable.ic_clouds),
    PARTLY_CLOUDY(R.drawable.ic_sun_cloudy),
    SNOWY(R.drawable.myicon),
    CLEAR_NIGHT(R.drawable.baseline_nights_stay_24),
    THUNDERSTORM(R.drawable.baseline_thunderstorm_24)
}

@Composable
fun WeeklyForeCastView(
    temp: TemperatureResponse
) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) {
        listOf(
            //  Color(0xFF4067DD),
            //  Color(0xFF4067DD),
            Color(0xFF3F54BE),
            Color(0xFF3F54BE)
        )
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
            // Color(0xFF50767D),
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
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(start = 3.dp),
                text = stringResource(id = R.string.Upcoming_Days),
                color = Corn,
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
            val localDay = LocalDate.parse(daysOfWeek).dayOfWeek.toString()
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
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = localDay.lowercase()
                        .replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(locale = Locale.ENGLISH) else it.toString()
                        },
                    fontSize = 16.sp,
                    color = Whitehis
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
                    color = Whitehis,
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
                    color = Whitehis,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
            }
        }
    }
}

@Composable
fun ExtraCards(
    text: String,
    numbers: String,
    // icon: Painter,
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
            Color(0xFF50767D),
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(120.dp)
            .padding(vertical = 8.dp)
            .padding(horizontal = 16.dp)
            .shadow(1.dp, RoundedCornerShape(21.dp))
            .background(brush = Brush.verticalGradient(background)),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text = text,
                color = Spiro,
                fontSize = 22.sp,

                )

            Text(
                modifier = Modifier.padding(8.dp),
                text = numbers,
                color = Whitehis,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Expected_Today),
            color = White,
            fontSize = 16.sp
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.3f,
            amplitude = 80f,
            frequency = 0.6f,
            gradientColors = listOf(Whitehis, Whitehis)
        )
        HorizontalWave(
            phase = rememberPhaseState(startPosition = 15f),
            alpha = 0.2f,
            amplitude = 60f,
            frequency = 0.4f,
            gradientColors = listOf(Whitehis, Whitehis)
        )

    }
}
