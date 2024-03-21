package com.example.demo_for_ads.gfc_adshelper.ad.splash;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isAppIsInBackground;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.MyAdListner;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SplashFullAdUtils {

    Context mContext;
    String mAdId = "";
    MyAdListner myAdListner;

    private static SplashFullAdUtils sharedInstance;

    public static SplashFullAdUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new SplashFullAdUtils();
        }
        return sharedInstance;
    }

    public void initAd(Context mContext, String adID, MyAdListner myAdListner) {
        this.mContext = mContext;
        this.mAdId = adID;
        this.myAdListner = myAdListner;

        if (isNetworkAvailable(mContext)) {
            if (isEmptyStr(adID)) {
                onSplashAdFailedToLoad();
                return;
            }

            InterstitialAd.load(mContext, mAdId, getAdxAdsRequest(), new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAdForSplash) {
                    super.onAdLoaded(interstitialAdForSplash);
                    if (interstitialAdForSplash != null) {

                        interstitialAdForSplash.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                printAdsLog("Splash>> callback_", "The ad was dismissed.");
                                AppOpenManager.isAnyOtherFullAdShowing = false;

                                if (myAdListner != null)
                                    myAdListner.onAdclosed();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                onSplashAdFailedToLoad();

                                printAdsLog("Splash>> callback_", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
//                        mInterstitialAd = null;
                                printAdsLog("Splash>> callback_", "The ad was shown.");
                            }
                        });

                        if (mContext != null && !((Activity) mContext).isFinishing() && !isAppIsInBackground(mContext)) {
                            if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                                AppOpenManager.isAnyOtherFullAdShowing = true;
                                interstitialAdForSplash.show((Activity) mContext);
                            }
//                        } else {
//                            AppOpenManager.isAnyOtherFullAdShowing = false;
                        }

                    } else {
                        onSplashAdFailedToLoad();
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    printAdsLog("Splash>> callback_", "onAdFailedToLoad");
                    onSplashAdFailedToLoad();
                }
            });

        } else {
            onSplashAdFailedToLoad();
            // no need to load another ads without internet!
        }
    }

    private void onSplashAdFailedToLoad() {
        Ads_Helper.dismissLoading();
        AppOpenManager.isAnyOtherFullAdShowing = false;

        if (myAdListner != null)
            myAdListner.onAdFailedToLoad();
    }

}
