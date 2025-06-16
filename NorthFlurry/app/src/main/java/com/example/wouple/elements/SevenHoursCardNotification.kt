package com.example.wouple.elements

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.activities.mainActivity.components.generateWeatherInfoTexts
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun SevenHoursCardNotification(temp: TemperatureResponse) {
    val context = LocalContext.current
    val texts = remember { generateWeatherInfoTexts(temp, context) }

    val currentTextIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (isActive) {
            delay(8000)
            currentTextIndex.intValue = (currentTextIndex.intValue + 1) % texts.size
        }
    }

    val currentText = texts[currentTextIndex.intValue]

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurfaceVariant),
        contentAlignment = CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.thebell),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Crossfade(
                targetState = currentText,
                animationSpec = tween(durationMillis = 600, easing = EaseOut),
                modifier = Modifier.animateContentSize()
            ) { text ->
                Text(
                    text = text,
                    fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 15.sp
                )
            }
        }
    }
}

