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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.components.LottieAnimationShootingStar
import com.example.wouple.activities.settingsActivity.components.CustomTab
import com.example.wouple.activities.settingsActivity.components.IdeasSettings
import com.example.wouple.activities.settingsActivity.components.LottieFilesAndTerms
import com.example.wouple.activities.settingsActivity.components.OpenMetAttribution
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsView(
    onBackPressed: () -> Unit,
    onFeedbackClicked: (Boolean) -> Unit,
    temp: TemperatureResponse,
    onLottieClicked: () -> Unit,
    onMetClicked: () -> Unit,
) {
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
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.padding(start = 24.dp))
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = R.drawable.logo2),
                            contentDescription = null,
                            tint = Unspecified
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            modifier = Modifier.padding(start = 40.dp, top = 2.dp),
                            text = stringResource(id = R.string.app_name),
                            fontWeight = FontWeight.Thin,
                        )
                    }
                },
              /*  navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }, */
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.verticalGradient(background))
            )
        },
        content = {
            GetSurface(
                onFeedbackClicked = onFeedbackClicked,
                onLottieClicked = { onLottieClicked() },
                onMetClicked = { onMetClicked() },
                temp = temp,
                paddingValues = it
            )
        }
    )
}
@Composable
fun GetSurface(
    onFeedbackClicked: (Boolean) -> Unit,
    onLottieClicked: () -> Unit,
    onMetClicked: () -> Unit,
    temp: TemperatureResponse,
    paddingValues: PaddingValues,
) {
    val (selected, setSelected) = remember {
        mutableIntStateOf(0)
    }
    var cardsVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(600)
        cardsVisible = true
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        val isDayLight = temp.current_weather.is_day == 1
        val backgroundColors: List<Color> = if (isDayLight) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(backgroundColors)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 32.dp))
            }

            item {
                CustomTab(
                    selectedItemIndex = selected,
                    items = listOf(
                        stringResource(id = R.string.General),
                        stringResource(id = R.string.Units)
                    ),
                    onClick = { setSelected(it) },
                    temp = temp
                )
            }

            if (selected == 0) {
                item {
                    AnimatedVisibility(
                        visible = cardsVisible,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = LinearEasing
                            )
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SettingsCardOne()
                            OpenMetAttribution (onMetClicked = onMetClicked)
                            LottieFilesAndTerms(onLottieClicked = onLottieClicked)
                            TroubleOnAppSettings { onFeedbackClicked(true) }
                            IdeasSettings { onFeedbackClicked(false) }
                            ShareTheAppSettings()
                            RateUsSettings()
                        }
                    }
                }
            } else {
                item {
                    SettingsCardTwo()
                    TemperatureUnitSettings(temp)
                    PrecipitationUnitSettings(temp)
                    WindUnitSettings(temp)
                }
            }

            item {
                LottieAnimationShootingStar()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
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
