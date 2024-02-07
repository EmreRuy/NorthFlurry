package com.example.wouple.activities.settingsActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.MutableState
import com.example.wouple.activities.mainActivity.MainActivity
import com.example.wouple.activities.startScreen.StartActivity
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.TemperatureResponse

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsView(
                onBackPressed = { onBackPressedDispatcher.onBackPressed() }
            )
        }
    }

}
