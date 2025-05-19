package com.example.wouple.activities.mainActivity.components


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.example.wouple.elements.SettingsViewModel
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import androidx.core.net.toUri


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onClose: () -> Unit,
    air: AirQuality?,
    viewModel: SettingsViewModel,
    onUnitSettingsChanged: () -> Unit
) {
    val context = LocalContext.current
    var navigationSelectedItem by remember {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = index == navigationSelectedItem,
                            label = {
                                Text(
                                    navigationItem.label,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
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
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
        ) {
            composable(Screens.Home.route) {
                MainView(
                    temp = temp,
                    locations = locations,
                    onSearch = onSearch,
                    searchedLocation = searchedLocation,
                    onLocationButtonClicked = onLocationButtonClicked,
                    onClose = onClose
                )
            }
            composable(Screens.Details.route) {
                DetailView(
                    temp = temp,
                    searchedLocation = searchedLocation.value ?: SearchedLocation(
                        display_name = "Oslo",
                        lat = "00.00",
                        lon = "00.00"
                    ),
                    air = air,
                )
            }
            composable(Screens.Settings.route) {
                SettingsView(
                    temp = temp,
                    onFeedbackClicked = { feedback ->
                        sendEmail(context, feedback)
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
                    },
                    viewModel = viewModel,
                    onUnitSettingsChanged = onUnitSettingsChanged
                )
            }
        }
    }
}

fun sendEmail(context: Context, isProblem: Boolean) {
    val selectorIntent = Intent(Intent.ACTION_SENDTO)
    selectorIntent.data = "mailto:".toUri()

    val emailIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_EMAIL, arrayOf("uyar.em.eu@gmail.com"))
        putExtra(
            Intent.EXTRA_SUBJECT,
            if (isProblem) "Trouble with the app" else " I have an idea"
        )
        putExtra(
            Intent.EXTRA_TEXT, """
                ––––––––––––––––––
                Device name: ${android.os.Build.MODEL}
                OS version: ${android.os.Build.VERSION.RELEASE}""".trimIndent()
        )
    }
    emailIntent.selector = selectorIntent
    context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
}