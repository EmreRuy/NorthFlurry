package com.example.wouple.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay
import kotlin.io.path.createTempDirectory

@Composable
fun LightningCardNotification(temp: TemperatureResponse) {
    val color = listOf(
        Color(0xFF25508C),
        Color(0xFF4180B3),
       /* Color(0xFF8ABFCC),
        Color(0xFFC0DDE1), */
    )
    val isDay = temp.current_weather.is_day == 1
    val background: List<Color> = if (isDay) {
        val baseColor = Color(0xFF7D8AE1) //#7D8AE1

        // Generate lighter shades
        val lighterShades = listOf(
            baseColor.copy(alpha = 0.7f),
            baseColor.copy(alpha = 0.8f),
            baseColor.copy(alpha = 0.9f),
        )

        lighterShades
    } else {
        listOf(
            Color(0xFF1D244D),
            Color(0xFF2E3A59),
            Color(0xFF3F5066),
        )
    }
    val texts = listOf(
        "Lightning possibility in Oslo 17 J/Kg",
        "Wind Direction is from the West",
        "Sunshine Duration is "
    )

    var visible by remember { mutableStateOf(true) }
    val currentTextIndex = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(400) // Adjust delay as needed
            currentTextIndex.value = (currentTextIndex.value + 1) % texts.size
            visible = true
            delay(8000) // Adjust delay as needed
            visible = false
        }
    }
    val currentText = texts[currentTextIndex.value]
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(brush = Brush.verticalGradient(background)),
        contentAlignment = CenterStart,
    ) {
        Row(modifier = Modifier.padding(start = 12.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_circle_notifications_24),
                contentDescription = "notification",
                tint = Color.White
            )
            Spacer(modifier = Modifier.padding(8.dp))
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(initialOffsetY = { -it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                Text(
                    text = currentText,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }
}