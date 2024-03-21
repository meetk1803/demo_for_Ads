package com.example.demo_for_ads.gfc_adshelper.ad.middle;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.TEST_TAG;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isAppIsInBackground;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;
import static com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager.isAnyOtherFullAdShowing;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MiddleFullAdUtils {

    Context mContext;
    MiddleHelper.MyMiddleAdsListner mAdsListner;
    static InterstitialAd m_InterstitialAd;
    public String MIDDLE_TAG = "middle_ ";
    public static boolean isFromDialogLoad = false;
    public static boolean isFirstLoad = true;
    public static boolean directShow = false;
    private static MiddleFullAdUtils sharedInstance;

    public static MiddleFullAdUtils getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new MiddleFullAdUtils();
        }
        return sharedInstance;
    }

    public InterstitialAd getM_InterstitialAd() {
        return m_InterstitialAd;
    }

    public void checkNShowMiddleInterstitialAd(Context context, MiddleHelper.MyMiddleAdsListner myMiddleAdsListner) {
        mContext = context;
        mAdsListner = myMiddleAdsListner;

        boolean isAnyOtherFullAdShowing;
        if (isNetworkAvailable(context)) {
            String mID = All_Ads_ID.Admob_Full_Middle;
            if (isEmptyStr(mID)) {
                onMiddleAdFailedToLoad();
                return;
            }

            if (m_InterstitialAd == null) {
                Ads_Helper.loadingDialog(context);
                isFromDialogLoad = true;
                loadAdsAgain(mContext, mAdsListner);
            } else {
                isFirstLoad = false;
                isAnyOtherFullAdShowing = true;
                if (context != null && !((Activity) context).isFinishing() && !isAppIsInBackground(context)) {
                    if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        printAdsLog(MIDDLE_TAG, TEST_TAG + "m_InterstitialAd.show---000_ showOnly " + context);
                        m_InterstitialAd.show((Activity) context);
                    }
                } else {
                    isAnyOtherFullAdShowing = false;
                }
            }
        } else {
            Ads_Helper.dismissLoading();
            isAnyOtherFullAdShowing = false;
            myMiddleAdsListner.onAdFailedToLoad(); // no need to load another ads without internet!
        }
    }

    private void loadAdsAgain(Context context, MiddleHelper.MyMiddleAdsListner myMiddleAdsListner) {
        mContext = context;
        mAdsListner = myMiddleAdsListner;

        String mID = All_Ads_ID.Admob_Full_Middle;
        if (!isEmptyStr(mID)) {
            InterstitialAd.load(context, mID, getAdxAdsRequest(), interstitialAdLoadCallback);
        }
    }

    InterstitialAdLoadCallback interstitialAdLoadCallback = new InterstitialAdLoadCallback() {
        @Override
        public void onAdLoaded(@NonNull InterstitialAd interstitialAdForMiddle) {
            super.onAdLoaded(interstitialAdForMiddle);
            try {
                printAdsLog("Middle>> callback_", TEST_TAG + "onAdLoaded isAnyOtherFullAdShowing>> " + isAnyOtherFullAdShowing);

                if (interstitialAdForMiddle != null) {
                    m_InterstitialAd = interstitialAdForMiddle;
                    m_InterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            m_InterstitialAd = null;
                            isAnyOtherFullAdShowing = false;

                            if (mAdsListner != null)
                                mAdsListner.onAdclosed();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            m_InterstitialAd = null;
                            isAnyOtherFullAdShowing = false;
                            Ads_Helper.dismissLoading();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            m_InterstitialAd = null;
                        }
                    });

                    if (directShow || isFirstLoad || isFromDialogLoad) {
                        directShow = false;
                        isFirstLoad = false;

                        if (isFromDialogLoad) {
                            // direct show ads
                            isFromDialogLoad = false;
                            Ads_Helper.dismissLoading();
                        }

                        printAdsLog("Middle>> callback_", TEST_TAG + "onAdLoaded isAnyOtherFullAdShowing>> " + isAnyOtherFullAdShowing + "=====================================================");
                        if (isAnyOtherFullAdShowing)
                            return;

                        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                            printAdsLog(MIDDLE_TAG, TEST_TAG + "m_InterstitialAd.show---111_ load_show");
                            isAnyOtherFullAdShowing = true;
                            interstitialAdForMiddle.show((Activity) mContext);
                        } else {
                            printAdsLog("Middle>> callback_", TEST_TAG + " APP IN BGGGGG=============================");
//                                isAnyOtherFullAdShowing = false;
                        }
                    }

                } else {
                    onMiddleAdFailedToLoad();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            super.onAdFailedToLoad(loadAdError);
            printAdsLog("Middle>> callback_", "onAdFailedToLoad");
            onMiddleAdFailedToLoad();
        }
    };

    private void onMiddleAdFailedToLoad() {
        printAdsLog("Middle>> onMiddleAdFailedToLoad", "");

        m_InterstitialAd = null;
        isAnyOtherFullAdShowing = false;
        Ads_Helper.dismissLoading();

        if (ProcessLifecycleOwner.get().getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
            if (mAdsListner != null)
                mAdsListner.onAdFailedToLoad();
        }
    }


}
