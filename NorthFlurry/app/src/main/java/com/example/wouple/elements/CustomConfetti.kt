package com.example.wouple.elements

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Confetti(
    var x: Float,
    var y: Float,
    var size: Float,
    var color: androidx.compose.ui.graphics.Color,
    var speedX: Float,
    var speedY: Float
)

@Composable
fun ConfettiEffect() {
    val confetti = remember { List(100) { generateRandomConfetti() } }
    val offsetY = remember { Animatable(0f) }
    val colorAlpha = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        offsetY.animateTo(
            targetValue = 1000f,
            animationSpec = tween(durationMillis = 5_000, easing = LinearEasing)
        )
        colorAlpha.animateTo(
            0f,
            animationSpec = tween(durationMillis = 500, easing = LinearEasing)
        )
        delay(1_000)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Transparent)
    ) {
        confetti.forEach { piece ->
            drawConfetti(colorAlpha.value, piece, offsetY.value % size.height)
        }
    }
}

fun generateRandomConfetti(): Confetti {
    val random = Random.Default
    return Confetti(
        x = random.nextFloat() * 100,
        y = random.nextFloat() * 2000f,
        size = random.nextFloat() * 10f + 5f, // Confetti size
        color = Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)),
        speedX = random.nextFloat() * 2f - 1f, // Horizontal speed
        speedY = random.nextFloat() * 4f + 2f  // Falling speed
    )
}

fun DrawScope.drawConfetti(alpha: Float, piece: Confetti, offsetY: Float) {
    val newX = (piece.x + piece.speedX) % size.width
    val newY = (piece.y + piece.speedY + offsetY) % size.height
    drawCircle(piece.color, alpha = alpha, radius = piece.size, center = Offset(newX, newY))
}