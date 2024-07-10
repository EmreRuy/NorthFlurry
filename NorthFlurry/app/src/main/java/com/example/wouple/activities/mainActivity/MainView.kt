package com.example.wouple.activities.mainActivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.wouple.activities.mainActivity.components.GetBottomView
import com.example.wouple.activities.mainActivity.components.GetLocationAndDegree
import com.example.wouple.activities.mainActivity.components.GetSearchBarAndList
import com.example.wouple.adds.AdaptiveBannerAd
import com.example.wouple.model.api.SearchedLocation
import com.example.wouple.model.api.TemperatureResponse
import com.google.android.gms.ads.MobileAds
@Composable
fun MainView(
    temp: TemperatureResponse,
    locations: List<SearchedLocation>?,
    onSearch: (String) -> Unit,
    searchedLocation: MutableState<SearchedLocation?>,
    onLocationButtonClicked: (SearchedLocation) -> Unit,
    onDetailsButtonClicked: (TemperatureResponse) -> Unit,
    onClose: () -> Unit,
    onSettingsClicked: (TemperatureResponse) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val isDay = temp.current_weather.is_day == 1
        val background: List<Color> = if (isDay) {
            val baseColor = Color(0xFF3F54BE)
            val lighterShades = listOf(
                baseColor,
                baseColor.copy(alpha = 0.9f),
                baseColor.copy(alpha = 0.8f),
                baseColor.copy(alpha = 0.5f),
            )

            lighterShades
        } else {
            listOf(
                Color(0xFF1D244D),
                Color(0xFF2E3A59),
                Color(0xFF3F5066),
            )
        }
        //Top view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = background
                    )
                )
                .padding(bottom = 18.dp),
            contentAlignment = TopStart
        ) {
            // Location name with degrees
            GetLocationAndDegree(
                temp = temp,
                searchedLocation = searchedLocation,
                onDetailsButtonClicked = onDetailsButtonClicked,
                onSettingsClicked = onSettingsClicked
            )
            // locations search
            GetSearchBarAndList(
                locations = locations,
                onSearch = onSearch,
                searchedLocation = searchedLocation,
                onLocationButtonClicked = onLocationButtonClicked,
                onClose = onClose
            )
        }
        //Bottom view
        GetBottomView(searchedLocation = searchedLocation, temp = temp)
        MobileAds.initialize(context)
        AdaptiveBannerAd(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .background(Transparent),
            //test ID:  "ca-app-pub-3940256099942544/9214589741"
            adId = "ca-app-pub-4891264100733274/3799972498"
        )
    }
}