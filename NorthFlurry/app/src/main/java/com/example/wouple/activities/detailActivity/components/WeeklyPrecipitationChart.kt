package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.elements.CustomPrecipitationBarChart
import com.example.wouple.model.api.PrecipitationUnit
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.preferences.PrecipitationUnitPref
import java.time.LocalDate

@Composable
fun WeeklyPrecipitationChart(temp: TemperatureResponse) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        WeeklyShowersChartView(temp = temp)
    }
}

@Composable
fun WeeklyShowersChartView(temp: TemperatureResponse) {
    val context = LocalContext.current
    val precipitationSum = temp.daily.precipitation_sum.take(7)
    val maxRainSum = precipitationSum.maxOrNull()?.toFloat() ?: return
    val daysOfWeek = (0 until 7).map {
        getLocalizedDayName(LocalDate.now().plusDays(it.toLong()).dayOfWeek).substring(0, 3)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Precipitation_For_Upcoming_Days),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        PopUpViewForPrecipitation()
        val minSumForShowingGraph =
            if (PrecipitationUnitPref.getPrecipitationUnit(context) == PrecipitationUnit.MM) 0.1 else 0.01
        if (maxRainSum <= minSumForShowingGraph) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.No_Precipitation_expected_for_the_week),
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .height(170.dp)
                    .drawBehind {
                        // draws X-Axis
                        drawLine(
                            color = Color.White,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height)
                        )
                    },
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                precipitationSum.forEach { value ->
                    CustomPrecipitationBarChart(size = value.toFloat(), max = maxRainSum)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                daysOfWeek.forEach { label ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .width(10.dp), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PopUpViewForPrecipitation() {
    var popupVisible by remember { mutableStateOf(false) }
    if (popupVisible) {
        PopUpContent(
            title = stringResource(id = R.string.Weekly_precipitation_chart),
            text = stringResource(id = R.string.Explainer_For_Precipitation),
            onDismiss = { popupVisible = false },
        )
    }
    IconButton(onClick = { popupVisible = true }) {
        Icon(
            Icons.Default.Info,
            contentDescription = "Show Popup",
            tint = Color.White
        )
    }
}
