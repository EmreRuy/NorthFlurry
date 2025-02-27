package com.example.wouple.elements

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.ui.theme.orgn
import javax.annotation.meta.When
import kotlin.math.max
import kotlin.math.min

@Composable
fun RowScope.CustomBarChart(
    size: Float,
    max: Float
) {
    val context = LocalContext.current
    var height by remember { mutableFloatStateOf(0f) }
    val heightStateAnimate by animateDpAsState(
        targetValue = height.dp,
        tween(2000, delayMillis = 300, easing = LinearEasing), label = ""
    )
    LaunchedEffect(key1 = size) {
        height = size * 20
    }
    val color = getGradientColor(size, max)
    Box(
        modifier = Modifier
            .padding(start = 6.dp, end = 6.dp, top = 4.dp)
            .size(heightStateAnimate)
            .weight(1f)
            .border(BorderStroke(1.dp, color = Color.Transparent))
            .background(
                color.copy(alpha = max(size / max, 2f)),
                shape = RoundedCornerShape(10.dp)
            )
           /* .clickable {
                val uvIndexDescriptionOfTheChart = when (size.toInt()) {
                    in 0..2 -> "Low"
                    in 3..5 -> "Moderate"
                    in 6..7 -> "High"
                    in 8..10 -> "Very High"
                    in 11..Int.MAX_VALUE -> "Extreme"
                    else -> {
                        "Unknown"
                    }

                }
                Toast
                    .makeText(
                        context,
                        "UV: $size $uvIndexDescriptionOfTheChart",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            } */
    ){
        if (
            size > 1.5.toFloat()
        ) {
        Text(
            text = size.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 4.dp)
        )
    }
    }
}
@Composable
fun GradientBar(size: Float, max: Float) {
    // Determine the color based on the ratio of size to max
    val color = getGradientColor(size, max)

    // Apply the background color dynamically based on size
    Modifier
        .background(
            color.copy(alpha = min(size / max, 1f)), // Ensure the alpha value is not greater than 1
            shape = RoundedCornerShape(10.dp)
        )
}

fun getGradientColor(size: Float, max: Float): Color {
    val ratio = size / max

    return when {
        ratio >= 1.7f -> Color.Red  // If size is equal to max, set color to red
        ratio >= 0.8f -> Color(255, 69, 0) // Orange when it's 75% of max
        ratio >= 0.7f -> Color(255, 165, 0) // Lighter Orange when it's 50%
        ratio >= 0.25f -> Color(255, 255, 0) // Yellow when it's 25% of max
        else -> Color(255, 255, 0) // Green when it's lower than 25%
    }
}
// orgn.copy(alpha = size / max)

