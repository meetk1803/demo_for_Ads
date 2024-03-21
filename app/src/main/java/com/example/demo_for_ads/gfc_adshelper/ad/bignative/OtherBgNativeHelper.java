package com.example.demo_for_ads.gfc_adshelper.ad.bignative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.ad.bignative.BigNativeHelper.getNativeAdView;
import static com.example.demo_for_ads.gfc_adshelper.ad.bignative.BigNativeHelper.populateBgNativeAdView;
import static com.example.demo_for_ads.gfc_adshelper.ad.bignative.Facebook_BigNativeHelper.hideParent;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class OtherBgNativeHelper {

    private static String TAG = "OtherBgNativeHelper_ ";

    private static boolean isNativeClicked = false;
    private static NativeAd mNative_Ad;
    private static AdLoader adLoader;

    private static Context context;
    private static FrameLayout frameLayout;
    private static String mID;

    private static boolean isLoadingNAtive = false;

    public static void showAdxBgNative(Context mContext, FrameLayout mFrame) {

        context = mContext;
        frameLayout = mFrame;

//        AdsHelper.printAdsLog(TAG + "showAdxBgNative:: ", "context " + context);
//        AdsHelper.printAdsLog(TAG + "showAdxBgNative:: ", "frameLayout " + frameLayout);

        if (isNetworkAvailable(context)) {

            if (frameLayout == null) {
                String mAdID = All_Ads_ID.Admob_BgNative;
                if (!isEmptyStr(mAdID)) {
                    mID = mAdID;
                    loadNativeAd(context, null, mID);
                }
                //loadNativeAd(context, null, mID);
                return;
            }

            final NativeAdView adView = getNativeAdView(context, frameLayout);

//            AdsHelper.printAdsLog(TAG + "showAdxBgNative:: ", "adView " + adView);

            if (adView == null) return;

//            AdsHelper.printAdsLog(TAG + "showAdxBgNative:: ", "mNative_Ad " + mNative_Ad);
//            AdsHelper.printAdsLog(TAG + "showAdxBgNative:: ", "isNativeClicked " + isNativeClicked);


            if (mNative_Ad != null && !isNativeClicked) {
                directShowloadedBigNativeAd(adView);
            } else {

                String mAdID = All_Ads_ID.Admob_BgNative;
                if (!isEmptyStr(mAdID)) {
                    mID = mAdID;
                    loadNativeAd(context, adView, mID);
                } else {
                    hideParent(context,frameLayout);
                }
            }
        } else {
            hideParent(context,frameLayout);
        }
    }

    static NativeAdView tempNativeAdView;

    public static void loadNativeAd(Context context, NativeAdView adView, String mAdID) {
        Ads_Helper.printAdsLog(TAG + "loadHomeNativeAd", "isLoadingNAtive:: " + isLoadingNAtive);
        if (isLoadingNAtive)
            return;

        if (Ads_Helper.isEmptyStr(mAdID))
            return;

        isNativeClicked = false;
        tempNativeAdView = adView;

        AdLoader.Builder builder = new AdLoader.Builder(context, mAdID)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd unifiedNativeAd) {
                        // Assumes you have a placeholder FrameLayout in your View layout
                        // (with id fl_adplaceholder) where the ad is to be placed.

                        // This method sets the text, images and the native ad, etc into the ad view.

//                        AdsHelper.printAdsLog(TAG + "HomeBg onNativeAdLoaded ", "tempNativeAdView " + tempNativeAdView);
//                        AdsHelper.printAdsLog(TAG + "HomeBg onNativeAdLoaded ", "frameLayout " + frameLayout);
//                        AdsHelper.printAdsLog(TAG + "loadHomeNativeAd", "isLoadingNAtive:: " + isLoadingNAtive);

                        mNative_Ad = unifiedNativeAd;
                        isLoadingNAtive = false;

                        if (tempNativeAdView == null && frameLayout != null) {
                            tempNativeAdView = getNativeAdView(context, frameLayout);
                        }

                        if (tempNativeAdView != null) {
                            directShowloadedBigNativeAd(tempNativeAdView);
                        }
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                        isNativeClicked = true;
                        isLoadingNAtive = false;
                        AppOpenManager.isFromApp = true;
                        mNative_Ad = null;
                        showAdxBgNative(context, frameLayout);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
//                        printAdsLog(TAG + "HomeBg onAdFailedToLoad", "onAdFailedToLoad");
                        isLoadingNAtive = false;
                        if (frameLayout != null) {
                            frameLayout.removeAllViews();
                            if (All_Ads_ID.isNativeFail == true) {
                                CustomLinkBgNativeHelper.showCustomLinkBigNativeAd(context, frameLayout);
                            }else {
                                hideParent(context,frameLayout);
                            }
                        }
                    }
                });

        adLoader = builder.build();
//        AdsHelper.printAdsLog(TAG + "loadHomeNativeAd", "Native load request sent...! ");
        adLoader.loadAd(getAdxAdsRequest());
        isLoadingNAtive = true;
    }

    private static void directShowloadedBigNativeAd(NativeAdView adView) {
        populateBgNativeAdView(mNative_Ad, adView);
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }
    }

}
