package com.example.wouple.activities.mainActivity

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.wouple.R
import com.example.wouple.activities.lightningMap.LightningMapActivity
import com.example.wouple.activities.rainMap.WeatherRadarWebView
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Dark20
import com.example.wouple.ui.theme.Spiro
import com.example.wouple.ui.theme.Whitehis
import com.example.wouple.ui.theme.beige
import com.example.wouple.ui.theme.mocassin


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
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = colors
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
                .padding(top = 16.dp)
        ) {
            TodayWeatherCard()
        }
        Box(
            modifier = Modifier
                .fillMaxHeight(1f)
                .background(White)
                .padding(bottom = 16.dp),
            contentAlignment = Center
        ) {
            searchedLocation.value?.let { ClickableCardDemo(it) }
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
        initialValue = Color(0xFF2F80ED),
        targetValue = Color(0xFF56CCF2),
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
                Spiro
            }
        )
    ) {
        Text(text = "Forecast Details")
    }
}

@Composable
fun DropDownMenu(
    onSettingsClicked: (String) -> Unit,
    // onTemperatureUnitChanged: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    //  var selectedTemperatureUnit by remember { mutableStateOf("celsius") }
    // var selectedPrecipitationUnit by remember { mutableStateOf("mm") }
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
        /*  CustomRadioButton(
              text = "Fahrenheit",
              isChecked = selectedTemperatureUnit == "fahrenheit"
          ) {
              selectedTemperatureUnit = "fahrenheit"
              onTemperatureUnitChanged("fahrenheit")
              isExpanded = false
          }
          CustomRadioButton(
              text = "Celsius",
              isChecked = selectedTemperatureUnit == "celsius"
          ) {
              selectedTemperatureUnit = "celsius"
              onTemperatureUnitChanged("celsius")
              isExpanded = false
          } */
    }
}

@Composable
fun CustomRadioButton(
    text: String,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = isChecked,
                onClick = onClick
            )
            .padding(4.dp)
            .padding(horizontal = 4.dp)
            .background(White),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .selectable(
                    selected = isChecked,
                    onClick = onClick
                )
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
            RadioButton(
                selected = isChecked,
                colors = RadioButtonDefaults.colors(selectedColor = Spiro),
                onClick = onClick,
                modifier = Modifier
                    .size(16.dp)
                    .padding(top = 5.dp)
            )
        }
    }
}

private fun getProperDisplayName(displayName: String?) = displayName?.split(",")?.firstOrNull()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun TodayWeatherCard() {

    var showDialog by remember { mutableStateOf(false) }

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(LightGray.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(CenterStart)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Global Weather Forecast",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp)) // Add some space between the two texts
                Text(
                    text = "Check weather forecast around the world with the interactive map",
                    fontWeight = FontWeight.Light,
                    color = Dark20,
                    fontSize = 14.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(mocassin.copy(alpha = 0.3f))
        ) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
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
fun ClickableCardDemo(searchedLocation: SearchedLocation) {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(LightGray.copy(alpha = 0.3f))
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(CenterStart)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier,
                    text = "Realtime Lightning Map",
                    fontWeight = FontWeight.Medium,
                    color = Dark20,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp)) // Add some space between the two texts
                Text(
                    text = "Check where lightnings occurs around the world",
                    fontWeight = FontWeight.Light,
                    color = Dark20,
                    fontSize = 14.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.map),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun SearchBar(
    isSearchExpanded: MutableState<Boolean>,
    onSearch: (String) -> Unit,
    onClose: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var query by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = if (isSearchExpanded.value) listOf(White, Color(0xFF56CCF2))
        else listOf(Transparent, Transparent)
    )

    LaunchedEffect(isSearchExpanded.value) {
        query = ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .background(
                brush = gradient,
                shape = RoundedCornerShape(28.dp)
            ),
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        AnimatedVisibility(
            visible = isSearchExpanded.value,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            TextField(
                value = query,
                maxLines = 1,
                onValueChange = {
                    query = it
                    if (query.length >= 3) {
                        onSearch(query)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 18.dp),
                textStyle = TextStyle(
                    color = Black,
                    fontSize = 18.sp,
                ),
                placeholder = {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Search a city or airport",
                        color = Black.copy(alpha = 0.7f)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    capitalization = KeyboardCapitalization.Characters,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus(true)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Transparent,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    cursorColor = Spiro,
                )
            )
        }
        IconButton(
            onClick = {
                isSearchExpanded.value = !isSearchExpanded.value
                if (!isSearchExpanded.value) {
                    query = ""
                }
                onClose()
            },
            modifier = Modifier
                .padding(end = 16.dp, bottom = if (isSearchExpanded.value) 0.dp else 8.dp)
                .rotate(if (isSearchExpanded.value) 1f else 360f)
        ) {
            Icon(
                imageVector = if (isSearchExpanded.value) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = "Search",
                tint = if (isSearchExpanded.value) Black else White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}