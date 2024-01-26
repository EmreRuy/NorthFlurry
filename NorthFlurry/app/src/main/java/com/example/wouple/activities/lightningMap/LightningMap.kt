package com.example.wouple.activities.lightningMap

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.wouple.model.api.SearchedLocation

@Composable
fun LightningMap(searchedLocation: SearchedLocation) {
    val y = searchedLocation.lat
    val x = searchedLocation.lon
    val mUrl = "https://www.lightningmaps.org/#m=oss;t=3;s=0;o=0;b=;ts=0;y=$y;x=$x;z=5;d=2;dl=2;dc=0;"
    Column(Modifier.fillMaxSize()) {
        AndroidView(factory = {
            WebView(it).apply {
                settings.javaScriptEnabled = true
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        }, update = {
            it.loadUrl(mUrl)
        })
    }
}