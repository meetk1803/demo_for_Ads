package com.example.demo_for_ads.gfc_adshelper.ad.smallnative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueSmallNative;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;


public class AlternateSmallNative {

    public static void checkAdsStatus(int value, Context context, FrameLayout mFlSmallNative) {

        if (isNetworkAvailable(context)) {
            if (!isEmptyStr(All_Ads_ID.Admob_SmallNative) && !isEmptyStr(All_Ads_ID.FB_smallNative) && !isEmptyStr(All_Ads_ID.Custom_Small_Native)) {
                switch (value) {
                    case 1:
                        //"facebook";
                        Facebook_SmallNativeHelper.showFBSmallNativeAd(context, mFlSmallNative);
                        break;
                    case 2:
                        //"custom";
                        CustomLinkSmallNativeHelper.showCustomLinkSmallNativeAd(context, mFlSmallNative);
                        break;
                    default:
                        //"google";
                        SmallNativeHelper.showSmallNativeAd(context, mFlSmallNative);
                        break;
                }

                if (adsValueSmallNative == 2) {
                    adsValueSmallNative = 0;
                } else {
                    adsValueSmallNative++;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_SmallNative) && !isEmptyStr(All_Ads_ID.FB_smallNative)) {
                if (All_Ads_ID.smallnativead == true) {
                    SmallNativeHelper.showSmallNativeAd(context, mFlSmallNative);
                    All_Ads_ID.smallnativead = false;
                } else {
                    Facebook_SmallNativeHelper.showFBSmallNativeAd(context, mFlSmallNative);
                    All_Ads_ID.smallnativead = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_SmallNative) && !isEmptyStr(All_Ads_ID.Custom_Small_Native)) {
                if (All_Ads_ID.smallnativead == true) {
                    SmallNativeHelper.showSmallNativeAd(context, mFlSmallNative);
                    All_Ads_ID.smallnativead = false;
                } else {
                    CustomLinkSmallNativeHelper.showCustomLinkSmallNativeAd(context, mFlSmallNative);
                    All_Ads_ID.smallnativead = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.FB_smallNative) && !isEmptyStr(All_Ads_ID.Custom_Small_Native)) {
                if (All_Ads_ID.smallnativead == true) {
                    Facebook_SmallNativeHelper.showFBSmallNativeAd(context, mFlSmallNative);
                    All_Ads_ID.smallnativead = false;
                } else {
                    CustomLinkSmallNativeHelper.showCustomLinkSmallNativeAd(context, mFlSmallNative);
                    All_Ads_ID.smallnativead = true;
                }
            }
        } else {
            mFlSmallNative.setVisibility(View.GONE);
        }

    }

}
