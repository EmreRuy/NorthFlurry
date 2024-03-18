package com.example.wouple.activities.mainActivity

import android.content.Context
import android.content.Intent
import android.widget.ScrollView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.UvChartViewCard
import com.example.wouple.activities.lightningMap.LightningMapActivity
import com.example.wouple.activities.rainMap.WeatherRadarWebView
import com.example.wouple.activities.ui.theme.getBackgroundGradient
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.LightningCardNotification
import com.example.wouple.elements.SearchBar
import com.example.wouple.elements.WeatherCardNotification
import com.example.wouple.elements.getWeeklyForecast
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.beige
import com.example.wouple.ui.theme.getSecondaryGradients
import com.example.wouple.ui.theme.mocassin
import com.example.wouple.ui.theme.vintage
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay


/*@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val temperature = TemperatureResponse(
        elevation = 3.0,
        generationtime_ms = 3.0,
        hourly = Hourly(listOf(), listOf()),
        hourly_units = HourlyUnits("", ""),
        latitude = 3.0,
        longitude = 3.0,
        timezone = "yeet",
        timezone_abbreviation = "woop",
        utc_offset_seconds = 2
    )

    WoupleTheme {
        FirstCardView(temperature, temperature)
    }
}*/

@Composable
fun FirstCardView(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onClose: () -> Unit,
    // onTemperatureUnitChanged: (String) -> Unit,
    onSettingsClicked: (String) -> Unit,
) {
    val isSearchExpanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val colors = listOf(
            Color(0xFF1D244D),
            Color(0xFF25508C),
            Color(0xFF4180B3),
            Color(0xFF8ABFCC),
        )
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF3F54BE)//Color(0xFF4067DD)

            // Generate lighter shades
            val lighterShades = listOf(
                baseColor,
                baseColor.copy(alpha = 0.9f),
                baseColor.copy(alpha = 0.8f),
              //  baseColor.copy(alpha = 0.5f),
               // baseColor.copy(alpha = 0.6f),
              //  baseColor.copy(alpha = 0.5f),
               /* baseColor.copy(alpha = 0.4f),
                baseColor.copy(alpha = 0.3f),
                baseColor.copy(alpha = 0.2f),
                baseColor.copy(alpha = 0.1f) */
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
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = background
                    )
                )
                .padding(bottom = 18.dp),
        ) {
            Column(
                modifier = Modifier.padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(isSearchExpanded, onSearch, onClose)
                if (locations != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(locations) { location ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp, horizontal = 16.dp)
                                    .clip(RoundedCornerShape(30.dp))
                                    .clickable {
                                        isSearchExpanded.value = false
                                        searchedLocation.value = location
                                        onLocationButtonClicked(location)
                                    },
                                elevation = 4.dp,
                                backgroundColor = White
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_pin),
                                        contentDescription = null,
                                        tint = Unspecified,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Text(
                                        text = location.display_name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Black
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    text = getProperDisplayName(searchedLocation.value?.display_name) ?: "N/D",
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center,
                    fontSize = 50.sp,
                    color = White,
                )
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = temp.current_weather.temperature.toInt()
                        .toString() + temp.hourly_units.temperature_2m[0],
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Thin,
                    fontSize = 50.sp,
                    color = White,
                )
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                val isDay = temp.current_weather.is_day == 1
                when {
                    (temp.current_weather.weathercode in listOf(
                        0,
                        1
                    ) && !isDay) -> LottieAnimationClear()

                    (temp.current_weather.weathercode in listOf(
                        0,
                        1
                    ) && isDay) -> LottieAnimationSun()

                    (temp.current_weather.weathercode == 2 && isDay) -> LottieAnimationPartlyCloudy()
                    (temp.current_weather.weathercode == 2 && !isDay) -> LottieAnimationPartlyCloudyNight()
                    (temp.current_weather.weathercode == 3) -> LottieAnimationCloud()
                    (temp.current_weather.weathercode in listOf(
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
                    )) -> LottieAnimationRain()

                    (temp.current_weather.weathercode in listOf(85, 86)) -> LottieAnimationSnow()
                    else -> LottieAnimationSun()
                }
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    DropDownMenu(onSettingsClicked)
                    Spacer(modifier = Modifier.padding(28.dp))
                    DetailButton {
                        onDetailsButtonClicked(temp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
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
       Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .background(White)
                .padding(top = 8.dp)
        ) {
            TodayWeatherCard(temp)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .background(White)
                .padding(bottom = 8.dp),
            contentAlignment = Center
        ) {
            searchedLocation.value?.let { ClickableCardDemo(it, temp) }
        }
    }
}

@Composable
fun LottieAnimationClear() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.moonlottie))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(65.dp)
    )
}

@Composable
fun LottieAnimationSun() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sunlottieanimation))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(50.dp)
    )
}

@Composable
fun LottieAnimationSnow() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottiesnowanimation))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(50.dp)
    )
}

@Composable
fun LottieAnimationPartlyCloudyNight() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloudynightlottie))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(50.dp)
    )
}

@Composable
fun LottieAnimationPartlyCloudy() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.partlycloudylottie))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(50.dp)
    )
}

@Composable
fun LottieAnimationRain() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottierain))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(55.dp)
    )
}

@Composable
fun LottieAnimationCloud() {
    val isPlaying by remember { mutableStateOf(true) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cloudlottie))
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying,
        iterations = LottieConstants.IterateForever // makes the animation keeps playing constantly
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier.size(60.dp)
    )
}

@Composable
fun DetailButton(onDetailsButtonClicked: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition()

    val colorTransition by infiniteTransition.animateColor(
        initialValue = vintage,
        targetValue = mocassin,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Button(
        modifier = Modifier.padding(bottom = 16.dp),
        shape = CircleShape,
        onClick = {
            isPressed = !isPressed
            onDetailsButtonClicked()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isPressed) {
                colorTransition
            } else {
               vintage
            }
        )
    ) {
        Text(text = "Forecast Details")
    }
}

@Composable
fun DropDownMenu(
    onSettingsClicked: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    isExpanded = rememberUpdatedState(isExpanded).value
    Box(
        modifier = Modifier
            .padding(start = 16.dp)
            .clip(RoundedCornerShape(32.dp))
    ) {
        IconButton(
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = White,
            )
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                offset = DpOffset(x = 0.dp, y = 40.dp)
            ) {
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onSettingsClicked("Settings")
                    }
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Settings",
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        isExpanded = false
                        onSettingsClicked("feedback")
                    }
                ) {
                    Text(
                        text = "Feed Back",
                        fontWeight = FontWeight.Light,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TodayWeatherCard(temp: TemperatureResponse) {
    var showDialog by remember { mutableStateOf(false) }
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
            .padding(16.dp)
            .clickable {
                showDialog = true
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        val colors = listOf(
            Color(0xFF1D244D),
            Color(0xFF25508C),
            Color(0xFF4180B3),
            Color(0xFF8ABFCC),
        )
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF4067DD)

            // Generate lighter shades
            val lighterShades = listOf(
                baseColor,
                baseColor.copy(alpha = 0.9f),
                baseColor.copy(alpha = 0.8f),
                baseColor.copy(alpha = 0.3f),
                // baseColor.copy(alpha = 0.6f),
                //  baseColor.copy(alpha = 0.5f),
                /* baseColor.copy(alpha = 0.4f),
                 baseColor.copy(alpha = 0.3f),
                 baseColor.copy(alpha = 0.2f),
                 baseColor.copy(alpha = 0.1f) */
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
                    .padding(top = 42.dp, end = 4.dp, start = 4.dp)
                    .align(Alignment.Center)
            ) {
                getWeeklyForecast(temp)
               /* Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Global Weather Forecast",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(2.dp)) // Adds some space between the two texts
                Text(
                    text = "Check weather forecast around the world with the interactive map",
                    fontWeight = FontWeight.Light,
                    color = Dark20,
                    fontSize = 14.sp
                ) */
            }
        }
        WeatherCardNotification(temp)
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                WeatherRadarWebView(url = "https://map.worldweatheronline.com/temperature?lat=36.884804&lng=30.704044")
            }
        }
    }
}

@Composable
fun ClickableCardDemo(searchedLocation: SearchedLocation, temp: TemperatureResponse) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                context.startActivity(LightningMapActivity.newIntent(context, searchedLocation))
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
    ) {
        val colors = listOf(
            Color(0xFF1D244D),
            Color(0xFF25508C),
            Color(0xFF4180B3),
            Color(0xFF8ABFCC),
        )
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF4067DD)

            // Generate lighter shades
            val lighterShades = listOf(
                baseColor,
                baseColor.copy(alpha = 0.9f),
                baseColor.copy(alpha = 0.8f),
                baseColor.copy(alpha = 0.3f),
                // baseColor.copy(alpha = 0.6f),
                //  baseColor.copy(alpha = 0.5f),
                /* baseColor.copy(alpha = 0.4f),
                 baseColor.copy(alpha = 0.3f),
                 baseColor.copy(alpha = 0.2f),
                 baseColor.copy(alpha = 0.1f) */
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
                    .align(CenterStart)
            ) {
                getHourlyWeatherInfo(temp)
            }
        }
        LightningCardNotification(temp)
    }
}