package com.example.wouple.activities.mainActivity.components


import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.wouple.model.api.TemperatureResponse

import androidx.compose.material3.*
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    temp: TemperatureResponse,
    modifier: Modifier = Modifier
) {
    val isDay = temp.current_weather.is_day == 1
    val background = if (isDay) Color(0xFF707DC2) else Color(0xFF2E3A59)

    NavigationBar(
        containerColor = background,
        contentColor = Color.White,
        modifier = modifier
    ) {
        val items = listOf("Home", "Details", "Settings")
        val icons = listOf(Icons.Default.Home, Icons.Default.Info, Icons.Default.Settings)

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item
                    )
                },
                label = { Text(
                    color = Color.White,
                    text = item
                ) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.DarkGray,
                    selectedTextColor = Color.DarkGray,
                    unselectedIconColor = Color.LightGray,
                    unselectedTextColor = Color.LightGray
                )
            )
        }
    }
}
