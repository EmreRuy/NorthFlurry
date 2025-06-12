package com.example.wouple.elements

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.CustomBarChart(
    size: Float,
    color: Color
) {
    var height by remember { mutableFloatStateOf(0f) }
    val heightStateAnimate by animateDpAsState(
        targetValue = height.dp,
        tween(2000, delayMillis = 300, easing = LinearEasing), label = ""
    )
    LaunchedEffect(key1 = size) {
        height = size * 20
    }
    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 4.dp)
            .size(heightStateAnimate)
            .weight(1f)
            .border(BorderStroke(1.dp, color = Color.Transparent))
            .background(
                color.copy(alpha = 0.8f),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        if (
            size > 1.5.toFloat()
        ) {
            Text(
                text = size.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 4.dp)
            )
        }
    }
}