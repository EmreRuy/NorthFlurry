package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.example.wouple.R
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.SearchBar
import com.example.wouple.elements.rememberPhaseState
import com.example.wouple.model.api.TemperatureResponse


@Composable
fun MainSome(onSettingsClicked: (TemperatureResponse) -> Unit,
             temp: TemperatureResponse,
) {
    Box(modifier = Modifier.background(White)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(modifier = Modifier.height(16.dp), verticalArrangement = Arrangement.Center) {
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
        }
        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = { onSettingsClicked(temp) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.menuicon),
                modifier = Modifier.size(32.dp),
                contentDescription = null,
                tint = White
            )
        }
        }
