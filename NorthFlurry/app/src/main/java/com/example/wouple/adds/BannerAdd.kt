package com.example.wouple.adds

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdaptiveBannerAd(modifier: Modifier = Modifier, adId: String) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            val displayMetrics = context.resources.displayMetrics
            val adWidthPixels = displayMetrics.widthPixels.toFloat()
            val adWidth = (adWidthPixels / displayMetrics.density).toInt()

            val adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)

            AdView(context).apply {
                setAdSize(adSize)
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

