package com.example.wouple.activities.detailActivity.components.openMetActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wouple.extensions.parcelable
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.AppTheme

class OpenMetAttributionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val temp = intent.parcelable<TemperatureResponse>("temp")
            Log.d("OpenMetActivity", "Temperature: $temp")
            if (temp == null) {
                throw IllegalStateException("temp is missing or wrong")
            }
            AppTheme {
                OpenMeteorologyView()
            }
        }
    }
}