package com.example.demo_for_ads.gfc_adshelper.ad.open;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isAppIsInBackground;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.demo_for_ads.MyApplication;
import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;


public class AppOpenManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {

    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;

    public static boolean isLoadingAd = false;
    private static boolean isShowingAd = false;

    public static boolean isFromApp = false;
    public static boolean isAnyOtherFullAdShowing = false;

    private long loadTime = 0;

    private int loadCount = 0;
    private int showCount = 0;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private Activity currentActivity;
    private final MyApplication myApplication;

    /**
     * Constructor
     */
    public AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    /**
     * LifecycleObserver methods
     */
    @OnLifecycleEvent(ON_START)
    public void onStart() {
        printAdsLog(LOG_TAG, "onStart");

        // no need to show when status is off
        if (!isNetworkAvailable(myApplication))
            return;

        printAdsLog(LOG_TAG, "isLoadingAd " + isLoadingAd);
        printAdsLog(LOG_TAG, "isAnyOtherFullAdShowing " + isAnyOtherFullAdShowing);

        if (!isAnyOtherFullAdShowing)
            showAdIfAvailable();
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        // Have unused ad, no need to fetch another.
        if (isLoadingAd || isAdAvailable()) {
            printAdsLog("showOpenAds", "isAdAvailable TRUE >>> isLoadingAd " + isLoadingAd);
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {

            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                super.onAdLoaded(appOpenAd);
                printAdsLog(LOG_TAG, "Open Ad onLoaded~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                AppOpenManager.this.appOpenAd = appOpenAd;
                AppOpenManager.this.loadTime = (new Date()).getTime();
                isLoadingAd = false;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                //Handle the error.
                printAdsLog(LOG_TAG, "Open Ad onAdFailedToLoad");

                printAdsLog("onAdFailedToLoad", "" + loadAdError.toString());

                isLoadingAd = false;
            }
        };

        loadAdxOpenAd();
    }

    private void loadAdxOpenAd() {
        String mADX_ID = All_Ads_ID.Admob_AppOpenAds;

        Ads_Helper.printAdsLog(LOG_TAG, "loadAdxOpenAd mADX_ID:: " + mADX_ID);

        if (!isEmptyStr(mADX_ID)) {

            if (isLoadingAd) {
                printAdsLog("loadAdxOpenAd", "isLoadingAd " + isLoadingAd);
                return;
            }

            isLoadingAd = true;
            loadCount++;
            AppOpenAd.load(myApplication, mADX_ID, getAdxAdsRequest(), AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
            Ads_Helper.printAdsLog(LOG_TAG, "appOpenAd loadAdxOpenAd loadCount:: " + loadCount);

        }
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */

    /**
     * Shows the ad if one isn't already showing.
     */
    public void showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.

        printAdsLog(LOG_TAG, "OPEN showAdIfAvailable=== ");

        if (!isFromApp && !isShowingAd && isAdAvailable()) {
            printAdsLog(LOG_TAG, "Will show ad.");
            FullScreenContentCallback fullScreenContentCallback =
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            //Set the reference to null so isAdAvailable() returns false.
                            printAdsLog(LOG_TAG, "onAdDismissedFullScreenContent=== ");
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            isFromApp = false;
                            isAnyOtherFullAdShowing = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            printAdsLog(LOG_TAG, "onAdFailedToShowFullScreenContent=== ");
                            AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            isFromApp = false;
                            isAnyOtherFullAdShowing = false;
                            fetchAd();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            printAdsLog(LOG_TAG, "onAdShowedFullScreenContent=== ");
                            isAnyOtherFullAdShowing = true;
                            isShowingAd = true;
                        }
                    };

            if (currentActivity != null && !currentActivity.isFinishing() && !isAppIsInBackground(currentActivity)) {
                if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                    printAdsLog(LOG_TAG, "appOpenAd.show=== ??? isAnyOtherFullAdShowing :: " + isAnyOtherFullAdShowing);

                    if (isAnyOtherFullAdShowing)
                        return;

                    isAnyOtherFullAdShowing = true;
                    isShowingAd = true;

                    showCount++;

                    appOpenAd.show(currentActivity);
                    printAdsLog(LOG_TAG, "appOpenAd.showING.... ================================================ showCount " + showCount);
                }
            } else {
//                goToNextOnFailed(adListenerForSplash); // dont load, bcz need to show splash ad
            }
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);

        } else {

            if (isShowingAd) {
                printAdsLog(LOG_TAG, "The app open ad is already showing.");
                return;
            }

            printAdsLog(LOG_TAG, "isFromApp ." + isFromApp);
            if (isFromApp)
                isFromApp = false;

            printAdsLog(LOG_TAG, "Can_not show open ad.");
            fetchAd();
        }
    }

    /**
     * ActivityLifecycleCallback methods
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
        // Ads: Interstitial that show when your app is in the background are a violation of AdMob policies and may lead to blocked ad serving. To learn more, visit  https://googlemobileadssdk.page.link/admob-interstitial-policies
        printAdsLog(LOG_TAG, "onActivityPaused");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }
}
