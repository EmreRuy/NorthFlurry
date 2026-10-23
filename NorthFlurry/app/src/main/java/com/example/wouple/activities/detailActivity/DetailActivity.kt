package com.example.wouple.activities.detailActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wouple.extensions.parcelable
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.example.wouple.ui.theme.AppTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val location = intent.parcelable<SearchedLocation>("location")
        val temp = intent.parcelable<TemperatureResponse>("temp")
        val air = intent.parcelable<AirQuality>("air")

        Log.d("DetailActivity", "Location: $location")
        Log.d("DetailActivity", "Temperature: $temp")

        if (location == null || temp == null) {
            finish()
            return
        }

        setContent {
            AppTheme {
                DetailView(
                    temp = temp,
                    searchedLocation = location,
                    air = air,
                )
            }
        }
    }
}
