package com.example.wouple.adds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAdd(modifier: Modifier, adId: String){
    Column(modifier = Modifier) {
       // Spacer(modifier = Modifier.size(24.dp))
        AndroidView(
            modifier = modifier.fillMaxWidth(),
            factory = {context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = adId
                    // add request
                    loadAd(AdRequest.Builder().build())
                }

            }
        )
    }
}