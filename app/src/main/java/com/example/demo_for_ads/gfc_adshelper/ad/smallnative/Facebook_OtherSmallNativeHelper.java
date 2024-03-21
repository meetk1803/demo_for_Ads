package com.example.demo_for_ads.gfc_adshelper.ad.smallnative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.ad.smallnative.Facebook_SmallNativeHelper.hideParentsmall;
import static com.example.demo_for_ads.gfc_adshelper.ad.smallnative.Facebook_SmallNativeHelper.inflateAd;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;


public class Facebook_OtherSmallNativeHelper {
    private static String TAG = "FBOtherSmallNativeHelper1_ ";
    private static boolean isLoadingNAtive = false;
    private static boolean isNativeClicked = false;
    private static NativeAd nativeAd;
    private static String mID;
    private static Context context;
    private static FrameLayout frameLayout;

    public static void showFBSmallNative(Context mContext, FrameLayout mFrame) {

        context = mContext;
        frameLayout = mFrame;
        Log.e("mFlSmallNative", "11-->" + frameLayout);

        Ads_Helper.printAdsLog(TAG + "showAdxBgNative:: ", "context " + context);
        Ads_Helper.printAdsLog(TAG + "showAdxBgNative:: ", "frameLayout " + frameLayout);

        if (isNetworkAvailable(context)) {

            if (frameLayout == null) {
                String mAdID = All_Ads_ID.FB_smallNative;
                if (!isEmptyStr(mAdID)) {
                    mID = mAdID;
                    loadNativeAd(context, null, mID);
                } else {
                    hideParentsmall(context, frameLayout);
                }
                return;
            }

            Ads_Helper.printAdsLog(TAG + "showAdxBgNative:: ", "mNative_Ad " + nativeAd);
            Ads_Helper.printAdsLog(TAG + "showAdxBgNative:: ", "isNativeClicked " + isNativeClicked);

            if (nativeAd != null && !isNativeClicked) {
                inflateAd(nativeAd);
            } else {

                String mAdID = All_Ads_ID.FB_smallNative;
                if (!isEmptyStr(mAdID)) {
                    mID = mAdID;
                    loadNativeAd(context, null, mID);
                } else {
                    hideParentsmall(context, frameLayout);
                }
            }
        } else {
            hideParentsmall(context, frameLayout);
        }
    }

    public static void loadNativeAd(Context context, FrameLayout adView, String mAdID) {
        Ads_Helper.printAdsLog(TAG + "loadHomeNativeAd", "isLoadingNAtive:: " + isLoadingNAtive);
        Ads_Helper.printAdsLog(TAG + "loadHomeNativeAd", "mAdID:: " + mAdID);
        if (isLoadingNAtive)
            return;

        if (isEmptyStr(mAdID))
            return;

        isNativeClicked = false;
        nativeAd = new NativeAd(context, mAdID);

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "onError Native ad failed to load:  " + adError.toString());
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
                isLoadingNAtive = false;
                nativeAd = null;
                Log.e("mFlSmallNative", "-->" + frameLayout);
                if (frameLayout != null) {
                    frameLayout.removeAllViews();
                    if (All_Ads_ID.isNativeFail == true) {
                        CustomLinkSmallNativeHelper.showCustomLinkSmallNativeAd(context, frameLayout);
                    } else {
                        hideParentsmall(context, frameLayout);
                    }
                }

            }

            @Override
            public void onAdLoaded(Ad ad) {
                nativeAd = (NativeAd) ad;

                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");

                if (nativeAd == null || nativeAd != ad) {
                    return;
                }

                isLoadingNAtive = false;
                // Inflate Native Ad into Container
                if (adView != null) {
                    Ads_Helper.printAdsLog("onAdLoaded", "....");
                    inflateAd(nativeAd);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
                isNativeClicked = true;
                isLoadingNAtive = false;
                AppOpenManager.isFromApp = true;
                nativeAd = null;
                showFBSmallNative(context, frameLayout);

            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");

                isLoadingNAtive = false;
//                hideParent(context, frameLayout);
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
        isLoadingNAtive = true;

    }
}
