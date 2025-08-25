@file:Suppress("DEPRECATION")

package com.example.wouple.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos


@Composable
fun HorizontalWave(
    phase: MutableState<Float>,
    alpha: Float,
    amplitude: Float,
    frequency: Float
) {
    val color = MaterialTheme.colorScheme.tertiary
    Canvas(
        modifier = Modifier.fillMaxWidth(),
        onDraw = {
            val wavePath = Path()
            val centerY = size.height / 2f
            var x = 0
            wavePath.moveTo(0f, centerY + amplitude)
            while (x < size.width.toInt()) {
                val y =
                    centerY + amplitude * cos(2 * PI * frequency * x / size.width + phase.value)
                wavePath.lineTo(x.toFloat(), y.toFloat())
                x++
            }
            wavePath.lineTo(x.toFloat(), centerY + amplitude)
            drawPath(
                path = wavePath,
                brush = SolidColor(color),
                alpha = alpha,
                style = Fill
            )
        }
    )
}

@Composable
fun rememberPhaseState(startPosition: Float): MutableState<Float> {
    val step: Long = 100 //countdown seconds
    val phase = remember { mutableFloatStateOf(startPosition) }
    LaunchedEffect(phase, step) {
        while (NonCancellable.isActive) {
            phase.floatValue += 0.02f
            delay(step)
        }
    }
    return phase
}
