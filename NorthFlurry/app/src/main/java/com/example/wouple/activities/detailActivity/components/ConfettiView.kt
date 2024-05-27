package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Composable
fun ConfettiView(
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
                    0xFF8BC34A.toInt(),
                    0xFF9C27B0.toInt(),
                    0xFFCDDC39.toInt(),
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