package com.example.demo_for_ads.gfc_adshelper.ad.bignative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueBigNative;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;

public class AlternateBigNative {

    public static void checkAdsStatus(int value, Context context, FrameLayout mFlBgNative) {
        if (isNetworkAvailable(context)) {

            if (!isEmptyStr(All_Ads_ID.Admob_BgNative) && !isEmptyStr(All_Ads_ID.FB_BgNative) && !isEmptyStr(All_Ads_ID.Custom_Big_Native)) {

                switch (value) {
                    case 1:
                        //"facebook";
                        Facebook_BigNativeHelper.showFBBgNative(context, mFlBgNative);
                        break;
                    case 2:
                        // "custom";
                        CustomLinkBgNativeHelper.showCustomLinkBigNativeAd(context, mFlBgNative);
                        break;
                    default:
                        //"google";
                        BigNativeHelper.showBgNativeAd(context, mFlBgNative);
                        break;
                }

                if (adsValueBigNative == 2) {
                    adsValueBigNative = 0;
                } else {
                    adsValueBigNative++;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_BgNative) && !isEmptyStr(All_Ads_ID.FB_BgNative)) {
                if (All_Ads_ID.bgnativead == true) {
                    BigNativeHelper.showBgNativeAd(context, mFlBgNative);
                    All_Ads_ID.bgnativead = false;
                } else {
                    Facebook_BigNativeHelper.showFBBgNative(context, mFlBgNative);
                    All_Ads_ID.bgnativead = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_BgNative) && !isEmptyStr(All_Ads_ID.Custom_Big_Native)) {
                if (All_Ads_ID.bgnativead == true) {
                    BigNativeHelper.showBgNativeAd(context, mFlBgNative);
                    All_Ads_ID.bgnativead = false;
                } else {
                    CustomLinkBgNativeHelper.showCustomLinkBigNativeAd(context, mFlBgNative);
                    All_Ads_ID.bgnativead = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.FB_BgNative) && !isEmptyStr(All_Ads_ID.Custom_Big_Native)) {
                if (All_Ads_ID.bgnativead == true) {
                    Facebook_BigNativeHelper.showFBBgNative(context, mFlBgNative);
                    All_Ads_ID.bgnativead = false;
                } else {
                    CustomLinkBgNativeHelper.showCustomLinkBigNativeAd(context, mFlBgNative);
                    All_Ads_ID.bgnativead = true;
                }
            }
        } else {
            mFlBgNative.setVisibility(View.GONE);
        }
    }
}
