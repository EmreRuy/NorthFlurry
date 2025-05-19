package com.example.wouple.activities.detailActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wouple.R
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun HourlyForecast(temp: TemperatureResponse) {
    val tabItem = listOf(
        TabItem(
            title = stringResource(id = R.string.Temperature),
        ),
        TabItem(
            title = stringResource(id = R.string.Precipitation),
        )
    )
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 12.dp)
           // .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(12.dp),
            text = stringResource(id = R.string.Next_24_Hours),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        var selectedTabIndex by remember {
            mutableIntStateOf(0)
        }
        Column {
            TabRow(
                modifier = Modifier.background(Color.Transparent),
                selectedTabIndex = selectedTabIndex,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
            ) {
                tabItem.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> {
                    TemperatureContent(temp)
                }

                1 -> {
                    PrecipitationContent(temp)
                }
            }
        }
    }
}

data class TabItem(val title: String)
