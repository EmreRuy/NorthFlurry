package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.wouple.elements.HorizontalWave
import com.example.wouple.elements.rememberPhaseState

@Composable
fun GetHorizontalWaveView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        HorizontalWave(
            phase = rememberPhaseState(0f),
            alpha = 1f,
            amplitude = 50f,
            frequency = 0.5f
        )
        HorizontalWave(
            phase = rememberPhaseState(15f),
            alpha = 0.5f,
            amplitude = 80f,
            frequency = 0.3f
        )
        HorizontalWave(
            phase = rememberPhaseState(10f),
            alpha = 0.2f,
            amplitude = 40f,
            frequency = 0.6f
        )
    }
}