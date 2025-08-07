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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import com.example.wouple.elements.SettingsViewModel
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsView(
    onFeedbackClicked: (Boolean) -> Unit,
    temp: TemperatureResponse,
    onLottieClicked: () -> Unit,
    onMetClicked: () -> Unit,
    viewModel: SettingsViewModel,
    onUnitSettingsChanged: () -> Unit,
) {
    Scaffold(
        content = {
            GetSurface(
                onFeedbackClicked = onFeedbackClicked,
                onLottieClicked = { onLottieClicked() },
                onMetClicked = { onMetClicked() },
                temp = temp,
                viewModel = viewModel,
                onUnitSettingsChanged = onUnitSettingsChanged
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
    viewModel: SettingsViewModel,
    onUnitSettingsChanged: () -> Unit
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
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer), // Color(0xFF1F2B2F) // For the Dark Mode  Color(0xFF1F2B2F)
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
                            OpenMetAttribution(onMetClicked = onMetClicked)
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
                    TemperatureUnitSettings(temp, viewModel = viewModel, onUnitSettingsChanged)
                    PrecipitationUnitSettings(temp, onUnitSettingsChanged)
                    WindUnitSettings(temp, onUnitSettingsChanged)
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
