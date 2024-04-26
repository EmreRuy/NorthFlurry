package com.example.wouple.activities.detailActivity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.wouple.model.api.AirQuality
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val location = intent.getParcelableExtra<SearchedLocation>("location")
            val temp = intent.getParcelableExtra<TemperatureResponse>("temp")
            val air = intent.getParcelableExtra<AirQuality>("air")
            val (explodeConfetti, setExplodeConfetti) = remember { mutableStateOf(false) }
            Log.d("SecondActivity", "Location: $location")
            Log.d("SecondActivity", "Temperature: $temp")
            if (location == null) {
                throw IllegalStateException("location is missing or wrong")
            }

            if (temp == null) {
                throw IllegalStateException("temp is missing or wrong")
            }

            DetailView(
                onBackPressed = { onBackPressedDispatcher.onBackPressed() },
                temp = temp,
                searchedLocation = location,
                air = air,
                explodeConfettiCallback = {setExplodeConfetti(true)}
                )
        }
    }
}

