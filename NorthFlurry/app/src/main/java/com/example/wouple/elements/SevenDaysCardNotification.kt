package com.example.wouple.elements

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
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
import com.example.wouple.activities.mainActivity.components.buildNotificationMessages
import com.example.wouple.model.api.TemperatureResponse
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.Locale

@Composable
fun SevenDaysCardNotification(temp: TemperatureResponse) {
    val context = LocalContext.current
    val messages = buildNotificationMessages(temp, context)
    val textIndex = remember { mutableIntStateOf(0) }

    // Text animation loop
    LaunchedEffect(Unit) {
        while (true) {
            delay(7000)
            textIndex.intValue = (textIndex.intValue + 1) % messages.size
        }
    }

    NotificationBox(
        icon = R.drawable.thebell,
        text = messages.getOrNull(textIndex.intValue) ?: ""
    )
}

@Composable
fun NotificationBox(
    icon: Int,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurfaceVariant),
        contentAlignment = CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "notification",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Crossfade(
                targetState = text,
                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
            ) { animatedText ->
                Text(
                    text = animatedText,
                    fontFamily = MaterialTheme.typography.displayMedium.fontFamily,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun getExtremeDayName(
    temperatures: List<Double>,
    dates: List<String>,
    context: Context,
    max: Boolean
): String {
    val index = temperatures
        .mapIndexed { i, temp -> i to temp.toInt() }
        .let { list -> if (max) list.maxByOrNull { it.second } else list.minByOrNull { it.second } }
        ?.first ?: 0

    val date = LocalDate.parse(dates[index])
    return getLocalizedDayNames(date.dayOfWeek, context)
}

@Composable
fun getWeatherEvents(
    temp: TemperatureResponse,
    context: Context
): Pair<List<String>, List<String>> {
    val thunderDays = mutableListOf<String>()
    val precipitationDays = mutableListOf<String>()

    for (i in temp.daily.time.indices.take(7)) {
        val date = LocalDate.parse(temp.daily.time[i])
        val day = getLocalizedDayNames(date.dayOfWeek, context).take(3)
        val code = temp.daily.weathercode[i]

        if (code in listOf(95, 96, 99)) thunderDays += day
        if (code in listOf(
                51, 53, 55, 56, 57, 61, 63, 65, 66, 67,
                80, 81, 82, 71, 73, 75, 77, 95, 96, 99
            )
        ) precipitationDays += day
    }

    return Pair(thunderDays, precipitationDays)
}

fun formatDayList(days: List<String>): String {
    return days.joinToString(", ") { day ->
        day.lowercase(Locale.getDefault()).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }
    }
}