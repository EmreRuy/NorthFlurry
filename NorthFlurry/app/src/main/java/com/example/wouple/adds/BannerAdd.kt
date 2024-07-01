package com.example.wouple.adds

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAdd(modifier: Modifier = Modifier, adId: String) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(getAdaptiveBannerAdSize(context))
                adUnitId = adId
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

fun getAdaptiveBannerAdSize(context: Context): AdSize {
    val display = context.resources.displayMetrics
    val adWidth = display.widthPixels.toFloat()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
        context, (adWidth / display.density).toInt()
    )
}