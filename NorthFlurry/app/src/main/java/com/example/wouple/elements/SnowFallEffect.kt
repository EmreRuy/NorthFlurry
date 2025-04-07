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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlinx.coroutines.delay
import kotlin.random.Random


data class Snowflake(
    var x: Float,
    var y: Float,
    var radius: Float,
    var speed: Float
)

@Composable
fun SnowfallEffect(searchBarAppear: MutableState<Boolean>) {
    val snowflakes = remember { List(100) { generateRandomSnowflake() } }
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
        searchBarAppear.value = true
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        snowflakes.forEach { snowflake ->
            drawSnowflake(colorAlpha.value, snowflake, offsetY.value % size.height)
        }
    }
}

fun generateRandomSnowflake(): Snowflake {
    return Snowflake(
        x = Random.nextFloat(),
        y = Random.nextFloat() * 2000f,
        radius = Random.nextFloat() * 2f + 2f, // Snowflake size
        speed = Random.nextFloat() * 1.2f + 1f  // Falling speed
    )
}

fun DrawScope.drawSnowflake(alpha: Float, snowflake: Snowflake, offsetY: Float) {
    val newY = (snowflake.y + offsetY * snowflake.speed) % size.height
    drawCircle(
        Color.Black,
        alpha = alpha,
        radius = snowflake.radius,
        center = Offset(snowflake.x * size.width, newY)
    )
}