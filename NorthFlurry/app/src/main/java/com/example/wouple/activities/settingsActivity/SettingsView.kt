package com.example.wouple.activities.settingsActivity

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.components.LottieAnimationShootingStar
import com.example.wouple.activities.settingsActivity.components.CustomTab
import com.example.wouple.activities.settingsActivity.components.IdeasSettings
import com.example.wouple.activities.settingsActivity.components.LottieFilesAndTerms
import com.example.wouple.activities.settingsActivity.components.PrecipitationUnitSettings
import com.example.wouple.activities.settingsActivity.components.RateUsSettings
import com.example.wouple.activities.settingsActivity.components.SettingsCardOne
import com.example.wouple.activities.settingsActivity.components.SettingsCardTwo
import com.example.wouple.activities.settingsActivity.components.ShareTheAppSettings
import com.example.wouple.activities.settingsActivity.components.TemperatureUnitSettings
import com.example.wouple.activities.settingsActivity.components.TroubleOnAppSettings
import com.example.wouple.activities.settingsActivity.components.WindUnitSettings
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.Whitehis
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsView(
    onBackPressed: () -> Unit,
    onFeedbackClicked: (Boolean) -> Unit,
    temp: TemperatureResponse,
    onLottieClicked: () -> Unit
) {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }
    var cardsVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(600)
        cardsVisible = true
    }
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        listOf(
            Color(0xFF3954C4),
            Color(0xFF384BB4)
        )
    } else {
        listOf(
            Color(0xFF1C2249),
            Color(0xFF1C2249)
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(id = R.string.Settings),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = Whitehis,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                contentColor = Whitehis,
                backgroundColor = Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.verticalGradient(background))
            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val isDayLight = temp.current_weather.is_day == 1
                val backgroundColors: List<Color> = if (isDayLight) {
                    val baseColor = Color(0xFF3F54BE)//Color(0xFF4067DD)
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
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .background(brush = Brush.verticalGradient(backgroundColors)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 32.dp))
                    CustomTab(
                        selectedItemIndex = selected,
                        items = listOf(stringResource(id = R.string.General), stringResource(id = R.string.Units)),
                        onClick = {
                            setSelected(it)
                        },
                        temp = temp
                    )
                    when (selected) {
                        0 -> {
                            AnimatedVisibility(
                                visible = cardsVisible,
                                enter = fadeIn(
                                    animationSpec = tween(
                                        durationMillis = 2000,
                                        easing = LinearEasing
                                    )
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    SettingsCardOne()
                                    TroubleOnAppSettings { onFeedbackClicked(true) }
                                    IdeasSettings { onFeedbackClicked(false) }
                                    ShareTheAppSettings()
                                    RateUsSettings()
                                    LottieFilesAndTerms(onLottieClicked = onLottieClicked)
                                }
                            }
                        }

                        1 -> {
                            SettingsCardTwo()
                            TemperatureUnitSettings(temp)
                            PrecipitationUnitSettings(temp)
                            WindUnitSettings(temp)
                        }
                    }
                    LottieAnimationShootingStar()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = BottomCenter
                ) {
                    HorizontalWave(
                        phase = rememberPhaseState(startPosition = 0f),
                        alpha = 0.5f,
                        amplitude = 60f,
                        frequency = 0.5f
                    )
                    HorizontalWave(
                        phase = rememberPhaseState(startPosition = 15f),
                        alpha = 0.5f,
                        amplitude = 90f,
                        frequency = 0.4f
                    )
                    HorizontalWave(
                        phase = rememberPhaseState(10f),
                        alpha = 0.2f,
                        amplitude = 80f,
                        frequency = 0.4f
                    )
                }
            }
        }
    )
}
