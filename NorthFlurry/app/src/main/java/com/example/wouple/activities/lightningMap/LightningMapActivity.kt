package com.example.wouple.activities.lightningMap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.wouple.model.api.SearchedLocation

class LightningMapActivity : ComponentActivity() {
    companion object {
        const val SEARCHED_LOCATION_KEY = "searched_location_key"
        fun newIntent(context: Context, searchedLocation: SearchedLocation) = Intent(context, LightningMapActivity::class.java).apply {
            putExtra(SEARCHED_LOCATION_KEY, searchedLocation)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val location = intent.getParcelableExtra<SearchedLocation>(SEARCHED_LOCATION_KEY)?: throw IllegalStateException("location is wrong")

        setContent {
            LightningMap(location)
        }
    }
}