package com.example.wouple.adds

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.AdRequest


object AdManager {
    private var myInterstitialAd: InterstitialAd? = null
    private var adId: String = "ca-app-pub-3940256099942544/1033173712"
    fun loadInterstitialAd(context: Context, adLoadCallback: (Boolean) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                super.onAdFailedToLoad(error)
                myInterstitialAd = null
                adLoadCallback.invoke(false)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                myInterstitialAd = interstitialAd
                adLoadCallback.invoke(true)
            }
        })
    }
    fun showInterstitialAd(context: Context, onAdDismissed: () -> Unit) {
        myInterstitialAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    onAdDismissed()
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
            Toast.makeText(context, "Ad is null", Toast.LENGTH_SHORT).show()
        }
    }
}