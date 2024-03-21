package com.example.demo_for_ads.gfc_adshelper.ad.smallnative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;
import static com.example.demo_for_ads.gfc_adshelper.ad.smallnative.SmallNativeHelper.getSmallNativeAdView;
import static com.example.demo_for_ads.gfc_adshelper.ad.smallnative.SmallNativeHelper.hideSmallParent;
import static com.example.demo_for_ads.gfc_adshelper.ad.smallnative.SmallNativeHelper.populateSmallNativeAdView;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;

public class OtherSmallNativeHelper {

    private static String TAG = "OtherSmallNativeHelper_ ";

    private static boolean isNativeClicked = false;
    private static NativeAd mNative_Ad;
    private static AdLoader adLoader;

    private static Context context;
    private static FrameLayout frameLayout;
    private static String mID;

    private static boolean isLoadingNAtive = false;

    public static void showAdxSmallNative(Context mContext, FrameLayout mFrame) {

        context = mContext;
        frameLayout = mFrame;

        printAdsLog(TAG + "showAdxSmallNative:: ", "context " + context);
        printAdsLog(TAG + "showAdxSmallNative:: ", "frameLayout " + frameLayout);

        if (isNetworkAvailable(context)) {

            if (frameLayout == null) {
                //loadNativeAd(context, null, mID);
                String mAdID = All_Ads_ID.Admob_SmallNative;
                if (!isEmptyStr(mAdID)) {
                    mID = mAdID;
                    loadNativeAd(context, null, mID);
                }
                return;
            }

            final NativeAdView adView = getSmallNativeAdView(context, frameLayout);

            printAdsLog(TAG + "showAdxSmallNative:: ", "adView " + adView);

            if (adView == null) return;

            printAdsLog(TAG + "showAdxSmallNative:: ", "mNative_Ad " + mNative_Ad);
            printAdsLog(TAG + "showAdxSmallNative:: ", "isNativeClicked " + isNativeClicked);


            if (mNative_Ad != null && !isNativeClicked) {
                directShowloadedSmallNativeAd(adView);
            } else {

                String mAdID = All_Ads_ID.Admob_SmallNative;
                if (!isEmptyStr(mAdID)) {
                    mID = mAdID;
                    loadNativeAd(context, adView, mID);
                } else {
                    hideSmallParent(context, frameLayout);
                }
            }
        } else {
            hideSmallParent(context, frameLayout);
        }
    }

    static NativeAdView tempNativeAdView;

    public static void loadNativeAd(Context context, NativeAdView adView, String mAdID) {
        printAdsLog(TAG + "loadNativeAd", "isLoadingNAtive:: " + isLoadingNAtive);
        if (isLoadingNAtive)
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

                        printAdsLog(TAG + "HomeBg onNativeAdLoaded ", "tempNativeAdView " + tempNativeAdView);
                        printAdsLog(TAG + "HomeBg onNativeAdLoaded ", "frameLayout " + frameLayout);
                        printAdsLog(TAG + "loadNativeAd", "isLoadingNAtive:: " + isLoadingNAtive);

                        mNative_Ad = unifiedNativeAd;
                        isLoadingNAtive = false;

                        if (tempNativeAdView == null && frameLayout != null) {
                            tempNativeAdView = getSmallNativeAdView(context, frameLayout);
                        }

                        if (tempNativeAdView != null) {
                            directShowloadedSmallNativeAd(tempNativeAdView);
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
//                        mNative_Ad = null;
//                        showAdxSmallNative(context, frameLayout, mAdID);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        printAdsLog(TAG + "HomeBg onAdFailedToLoad", "onAdFailedToLoad");
                        isLoadingNAtive = false;
                        if (frameLayout != null) {
                            frameLayout.removeAllViews();
                            if (All_Ads_ID.isNativeFail == true) {
                                CustomLinkSmallNativeHelper.showCustomLinkSmallNativeAd(context, frameLayout);
                            } else {
                                hideSmallParent(context, frameLayout);
                            }
                        }
                    }
                });

        adLoader = builder.build();
        printAdsLog(TAG + "loadNativeAd", "Native load request sent...! ");
        adLoader.loadAd(getAdxAdsRequest());
        isLoadingNAtive = true;
    }

    private static void directShowloadedSmallNativeAd(NativeAdView adView) {
        populateSmallNativeAdView(mNative_Ad, adView);
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            frameLayout.addView(adView);
        }
    }


}
