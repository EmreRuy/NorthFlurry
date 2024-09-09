package com.example.wouple.activities.mainActivity.components

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.wouple.model.api.TemperatureResponse

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    temp: TemperatureResponse
) {
    val isDay = temp.current_weather.is_day == 1
   val background = if (isDay) Color(0xFF707DC2) else Color(0xFF2E3A59)
    BottomNavigation(
        backgroundColor =   background,
        contentColor = Color.White
    ) {
        val items = listOf("Home", "Details", "Settings")
        val icons = listOf(Icons.Default.Home, Icons.Default.Info, Icons.Default.Settings)

        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item
                    )
                },
                label = { Text(text = item) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.LightGray
            )
        }
    }
}