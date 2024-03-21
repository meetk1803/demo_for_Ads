package com.example.demo_for_ads.gfc_adshelper.ad.middle;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.dismissLoading;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isAppIsInBackground;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.loadingDialog;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;


public class Facebook_MiddleFullUtils {

    Context mContext;
    Facebook_MiddleHelper.MyMiddleAdsListner mAdsListner;

    public static boolean isFromDialogLoad = false;
    public static boolean isFirstLoad = true;
    public static boolean directShow = false;

    private InterstitialAd interstitialAd;

    private static Facebook_MiddleFullUtils sharedInstance;

    public static Facebook_MiddleFullUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new Facebook_MiddleFullUtils();
        }

        return sharedInstance;
    }

    public void checkNShowMiddleInterstitialAd(Context context, Facebook_MiddleHelper.MyMiddleAdsListner myMiddleAdsListner) {
        mContext = context;
        mAdsListner = myMiddleAdsListner;

        if (isNetworkAvailable(context)) {
            String mID = All_Ads_ID.FB_Full;
            if (isEmptyStr(mID)) {
                onMiddleAdFailedToLoad();
                return;
            }

            if (interstitialAd == null) {
                loadingDialog(context);
                isFromDialogLoad = true;
                loadAdsAgain(mContext, mAdsListner);

            } else {

                if (interstitialAd != null && interstitialAd.isAdLoaded()) {
                    isFirstLoad = false;
                    if (context != null && !((Activity) context).isFinishing() && !isAppIsInBackground(context)) {
                        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                            interstitialAd.show();
                        }
                    }
                } else {
                    dismissLoading();
                    mAdsListner.onAdFailedToLoad();
                }

            }
        } else {
            onMiddleAdFailedToLoad();
        }
    }

    private boolean isNetworkAvailable(Context context) {
        return false;
    }

    private void loadAdsAgain(Context context, Facebook_MiddleHelper.MyMiddleAdsListner myMiddleAdsListner) {
        mContext = context;
        mAdsListner = myMiddleAdsListner;

        String mID = All_Ads_ID.FB_Full;
        if (!isEmptyStr(mID)) {
            interstitialAd = new InterstitialAd(context, mID);
            // Create listeners for the Interstitial Ad
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    // Interstitial ad displayed callback
                    interstitialAd = null;
//                    Global.printLog_("FB_loadAdsAgain", "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    // Interstitial dismissed callback
//                    Global.printLog_("FB_loadAdsAgain", "Interstitial ad dismissed.");
                    interstitialAd = null;
                    if (mAdsListner != null)
                        mAdsListner.onAdclosed();

//                    if (!isExitAd)
//                        loadAdsAgain(mContext, mAdsListner, mPriorityAdsListner);
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    // Ad error callback
//                    Global.printLog_("FB_loadAdsAgain", "Interstitial ad failed to load: " + adError.getErrorMessage());
                    // Toast.makeText(context, "Error loading ad: " + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    onMiddleAdFailedToLoad();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
//                    Global.printLog_("FB_loadAdsAgain", "Interstitial ad is loaded and ready to be displayed!");
                    // Show the ad
                    if (context != null && !((Activity) context).isFinishing() && !isAppIsInBackground(context)) {
                        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {

                            if (directShow || isFirstLoad || isFromDialogLoad) {
                                directShow = false;
                                isFirstLoad = false;

                                if (isFromDialogLoad) {
                                    // direct show ads
                                    isFromDialogLoad = false;
                                    dismissLoading();

                                }

                                interstitialAd.show();

                            }
                        }
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
//                    Global.printLog_("FB_loadAdsAgain", "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
//                    Global.printLog_("FB_loadAdsAgain", "Interstitial ad impression logged!");
                }
            };

            // For auto play video ads, it's recommended to load the ad
            // at least 30 seconds before it is shown
            interstitialAd.loadAd(
                    interstitialAd.buildLoadAdConfig()
                            .withAdListener(interstitialAdListener)
                            .build());
        }
    }


    private void onMiddleAdFailedToLoad() {
        printAdsLog("Middle>> onMiddleAdFailedToLoad", "FB");
        dismissLoading();
        if (mAdsListner != null) {
            mAdsListner.onAdFailedToLoad();
        }

    }
}
