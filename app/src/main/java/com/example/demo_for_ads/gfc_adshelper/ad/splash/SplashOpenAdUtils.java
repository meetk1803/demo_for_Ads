package com.example.demo_for_ads.gfc_adshelper.ad.splash;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.demo_for_ads.MyApplication;
import com.example.demo_for_ads.gfc_adshelper.MyAdListner;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class SplashOpenAdUtils {
    Context mContext;
    String mAdId = "";
    MyAdListner myAdListner;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private static SplashOpenAdUtils sharedInstance;

    public static SplashOpenAdUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new SplashOpenAdUtils();
        }
        return sharedInstance;
    }

    public void initOpenAd(Context mContext, String adID, MyAdListner myAdListner) {
        this.mContext = mContext;
        this.mAdId = adID;
        this.myAdListner = myAdListner;

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);

                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    AppOpenManager.isAnyOtherFullAdShowing = true;
                    appOpenAd.show((Activity) mContext);
                } else {
                    AppOpenManager.isAnyOtherFullAdShowing = false;
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("Splash", " onAdFailedToLoad: " + loadAdError.toString());
                Log.e("Splash", " onAdFailedToLoad: " + loadAdError.getMessage());
                //Call next Activity
                onOpenAdFailed();
            }
        };

        printAdsLog("Splash", " mAdId: " + mAdId);

        AppOpenAd.load(mContext, this.mAdId, getAdxAdsRequest(),
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

//        AppOpenAd.load(mContext, "/6499/example/app-open", getAdxAdsRequest(),
//                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

//        AppOpenAd.load(mContext, "ca-app-pub-3940256099942544/3419835294", getAdsRequest(),
//                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);

    }

    FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
        @Override
        public void onAdDismissedFullScreenContent() {
            // Set the reference to null so isAdAvailable() returns false.
            printAdsLog("TAG", "onAdDismissedFullScreenContent: ");
            //Call next Activity
            AppOpenManager.isAnyOtherFullAdShowing = false;

            if (MyApplication.getInstance() != null)
                MyApplication.getInstance().setAppOpenManager();

            if (myAdListner != null)
                myAdListner.onAdclosed();
        }

        @Override
        public void onAdFailedToShowFullScreenContent(AdError adError) {
            printAdsLog("TAG", "onAdFailedToShowFullScreenContent: ");
            //Call next Activity
            AppOpenManager.isAnyOtherFullAdShowing = false;
            onOpenAdFailed();
        }

        @Override
        public void onAdShowedFullScreenContent() {

        }
    };

    private void onOpenAdFailed() {

        if (MyApplication.getInstance() != null)
            MyApplication.getInstance().setAppOpenManager();

        if (myAdListner != null)
            myAdListner.onAdFailedToLoad();
    }
}
