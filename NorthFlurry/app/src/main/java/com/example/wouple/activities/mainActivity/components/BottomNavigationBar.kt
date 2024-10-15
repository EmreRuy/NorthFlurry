package com.example.wouple.activities.mainActivity.components


import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wouple.activities.detailActivity.DetailView
import com.example.wouple.activities.detailActivity.components.openMetActivity.OpenMetAttributionActivity
import com.example.wouple.activities.lottieCopyRightActivity.LottieCopyRightActivity
import com.example.wouple.activities.mainActivity.BottomNavigationItem
import com.example.wouple.activities.mainActivity.MainView
import com.example.wouple.activities.mainActivity.Screens
import com.example.wouple.activities.settingsActivity.SettingsView
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onClose: () -> Unit,
    onSettingsClicked: (TemperatureResponse) -> Unit,
    air: AirQuality?,
) {
    val context = LocalContext.current
    // Initializing the default selected item
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }

    // Get an instance of NavController
    val navController = rememberNavController()
    // Scaffold to hold our bottom navigation bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                // Iterate through bottom navigation items
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = { Text(navigationItem.label) },
                            icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                            onClick = {
                                navigationSelectedItem = index
                                navController.navigate(navigationItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
            }
        }
    ) { paddingValues ->
        // Setup the NavHost for navigation
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.Home.route) {
                MainView(
                    temp = temp,
                    locations = locations,
                    onSearch = onSearch,
                    searchedLocation = searchedLocation,
                    onLocationButtonClicked = onLocationButtonClicked,
                    onDetailsButtonClicked = {
                        // Navigate to the Details screen
                        navController.navigate(Screens.Details.route)
                    },
                    onClose = onClose,
                    onSettingsClicked = onSettingsClicked
                )
            }
            composable(Screens.Details.route) {
                // Display DetailView when the user navigates to the Details screen
                DetailView(
                    temp = temp,
                    searchedLocation = searchedLocation.value ?: SearchedLocation(
                        display_name = "Oslo",
                        lat = "00.00",
                        lon = "00.00"
                    ),
                    air = air,  // Pass air quality data if available
                    onBackPressed = { navController.popBackStack() }
                )
            }
            composable(Screens.Settings.route) {
                SettingsView(
                    temp = temp,
                    onBackPressed = { navController.popBackStack() },
                    onFeedbackClicked = { feedback ->
                        // Handle feedback logic
                    },
                    onLottieClicked = {
                        val intent = Intent(context, LottieCopyRightActivity::class.java).apply {
                            putExtra("temp", temp)
                        }
                        context.startActivity(intent)
                    },
                    onMetClicked = {
                        val intent = Intent(context, OpenMetAttributionActivity::class.java).apply {
                            putExtra("temp", temp)
                        }
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}
