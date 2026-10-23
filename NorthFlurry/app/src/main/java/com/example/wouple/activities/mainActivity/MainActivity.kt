package com.example.wouple.activities.mainActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wouple.activities.firstScreen.FirstScreenView
import com.example.wouple.activities.mainActivity.components.BottomNavigationBar
import com.example.wouple.activities.mainActivity.components.ErrorScreen
import com.example.wouple.activities.mainActivity.components.LoadingScreen
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.data.preferences.PreferenceManager
import com.example.wouple.elements.NoInternetDialog
import com.example.wouple.elements.SettingsViewModel
import com.example.wouple.elements.isInternetConnected
import com.example.wouple.ui.main.MainUiState
import com.example.wouple.ui.main.MainViewModel
import com.example.wouple.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferenceManager = PreferenceManager(this)
        val isFirstLaunch = preferenceManager.getSearchedLocation() == null

        enableEdgeToEdge()
        setContent {
            AppTheme {
                val context = LocalContext.current
                var isConnected by remember { mutableStateOf(true) }
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(Unit) {
                    isConnected = isInternetConnected(context)
                }

                if (!isConnected) {
                    NoInternetDialog(
                        activity = this,
                        onRetry = {
                            isConnected = isInternetConnected(context)
                            if (isConnected) viewModel.loadWeatherData()
                        }
                    )
                } else if (isFirstLaunch) {
                    FirstScreenView(
                        onStartButtonClicked = {
                            val intent = Intent(this@MainActivity, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )
                } else {
                    val settingsViewModel: SettingsViewModel = viewModel()
                    when (val state = uiState) {
                        is MainUiState.Loading -> {
                            LoadingScreen()
                        }
                        is MainUiState.Success -> {
                            BottomNavigationBar(
                                temp = state.temperatureResponse,
                                locations = state.searchedLocationsResults,
                                onSearch = { query -> viewModel.searchLocations(query) },
                                searchedLocation = state.searchedLocation,
                                onLocationButtonClicked = { location ->
                                    viewModel.selectLocation(location)
                                },
                                onClose = { viewModel.clearSearchResults() },
                                air = state.airQuality,
                                viewModel = settingsViewModel,
                                onUnitSettingsChanged = { viewModel.loadWeatherData() }
                            )
                        }
                        is MainUiState.Error -> {
                            ErrorScreen(
                                message = state.message,
                                onRetry = { viewModel.loadWeatherData() }
                            )
                        }
                    }
                }
            }
        }
    }
}
