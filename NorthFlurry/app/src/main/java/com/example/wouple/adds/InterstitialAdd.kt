package com.example.wouple.adds

import android.app.Activity
import android.media.tv.AdRequest
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


private var myInterstitialAd: InterstitialAd? = null
private var adId: String = "ca-app-pub-3940256099942544/1033173712"
@Composable
private fun InterstitialAd(adStatus: (Boolean) -> Unit){
    val context = LocalContext.current
    val adRequest = com.google.android.gms.ads.AdRequest.Builder().build()
    InterstitialAd.load(context, adId, adRequest, object: InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(error: LoadAdError) {
            super.onAdFailedToLoad(error)
            myInterstitialAd = null
        }

        override fun onAdLoaded(interstitalAd: InterstitialAd) {
            super.onAdLoaded(interstitalAd)
            myInterstitialAd = interstitalAd
        }
    })
}

@Composable
private fun ShowInterstitialAd(){
    val context = LocalContext.current
    myInterstitialAd?.let {ad ->
        ad.fullScreenContentCallback = object : FullScreenContentCallback(){
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                Log.i("AD_TAG", "onAdDismissedFullScreenContent")
                myInterstitialAd = null
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.i("AD_TAG", "onAdImpression")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.i("AD_TAG", "onAdClicked")
            }
        }
        ad.show(context as Activity)
    } ?: run {
        Toast.makeText(context, "Add is null", Toast.LENGTH_SHORT ).show()
    }
}