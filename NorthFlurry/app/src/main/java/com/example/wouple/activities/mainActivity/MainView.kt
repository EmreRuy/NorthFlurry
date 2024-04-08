package com.example.wouple.activities.mainActivity

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wouple.R
import com.example.wouple.activities.detailActivity.DayLength
import com.example.wouple.activities.detailActivity.SunRise
import com.example.wouple.activities.detailActivity.SunSet
import com.example.wouple.activities.lightningMap.LightningMapActivity
import com.example.wouple.activities.rainMap.WeatherRadarWebView
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.LightningCardNotification
import com.example.wouple.elements.SearchBar
import com.example.wouple.elements.WeatherCardNotification
import com.example.wouple.elements.getWeeklyForecast
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.mocassin
import com.example.wouple.ui.theme.vintage
import kotlinx.coroutines.delay

@Composable
fun MainView(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onClose: () -> Unit,
    // onTemperatureUnitChanged: (String) -> Unit,
    onSettingsClicked: (TemperatureResponse) -> Unit,
) {
    val isSearchExpanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF3F54BE)//Color(0xFF4067DD)
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

        //Top view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = background
                    )
                )
                .padding(bottom = 18.dp),
            contentAlignment = BottomCenter
        ) {
            // Location name with degrees
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
                    color = White,
                    modifier = Modifier.padding(horizontal = 16.dp)
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
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DropDownMenu { onSettingsClicked(temp) }
                    DetailButton {
                        onDetailsButtonClicked(temp)
                    }
                    Spacer(modifier = Modifier.width(58.dp))
                }

                // Horizontal waves
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = BottomCenter
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


            // locations search
            Column(modifier = Modifier
                .padding(4.dp)
                .align(Alignment.TopStart),) {
                SearchBar(isSearchExpanded, onSearch, onClose)
                if (locations != null) {
                    LazyColumn(
                        Modifier.height(360.dp),
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
            }
        }


        //Bottom view
        Column(
            modifier = Modifier
                .background(White)
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            SunsetSunriseColumnCard(temp)
            TodayWeatherCard(temp)
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
    onSettingsClicked: () -> Unit,
) {
    var isPressed by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    isExpanded = rememberUpdatedState(isExpanded).value
    Box(
        modifier = Modifier
            .padding(start = 16.dp)
            .clip(CircleShape)
    ) {
        IconButton(
            onClick = {
                isPressed = !isPressed
                onSettingsClicked()
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = White,
            )
        }
        /*  AnimatedVisibility(
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
                          isPressed = !isPressed
                      }
                  ) {
                      Text(
                          modifier = Modifier,
                          text = "Settings",
                          fontWeight = FontWeight.Light,
                          color = Black
                      )
                  }
                  DropdownMenuItem(
                      onClick = {
                          isExpanded = false
                          onSettingsClicked("feedback")
                          isPressed = !isPressed
                      }
                  ) {
                      Text(
                          text = "Feed Back",
                          fontWeight = FontWeight.Light,
                          color = Black
                      )
                  }
              }
          } */
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
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF3F54BE)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = Brush.verticalGradient(background))
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 42.dp, end = 4.dp, start = 4.dp)
                    .align(Center)
            ) {
                getWeeklyForecast(temp)
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
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF3F54BE)

            // Generate lighter shades
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(brush = Brush.verticalGradient(background))
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Center)
            ) {
                getHourlyWeatherInfo(temp)
            }
        }
        LightningCardNotification(temp)
    }
}

@Composable
private fun SunsetSunriseColumnCard(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF3F54BE)

        // Generate lighter shades
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
  /*  Column(
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(background))
            .padding(16.dp)
    ) {
        SunsetSunriseColumn(temp = temp)
    } */
    SunsetSunriseColumn(temp)
}

@Composable
private fun SunsetSunriseColumn(temp: TemperatureResponse) {
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF3F54BE)

        // Generate lighter shades
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
            .padding(8.dp)
            .padding(vertical = 16.dp, horizontal = 12.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(brush = Brush.verticalGradient(background)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.Sunrise_Sunset),
            color = White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                SunRise(temp)
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
                SunSet(temp)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                com.example.wouple.activities.detailActivity.LottieAnimationSun()
            }
            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                DayLength(temp)
            }
        }
    }
}