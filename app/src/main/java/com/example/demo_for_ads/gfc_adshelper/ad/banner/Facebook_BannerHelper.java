package com.example.demo_for_ads.gfc_adshelper.ad.banner;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;


public class Facebook_BannerHelper {

    public static void showBannerAd(Context context, FrameLayout mFlBanner) {
        if (isNetworkAvailable(context)) {

            String mID = All_Ads_ID.FB_banner;
            if (!isEmptyStr(mID)) {
                showFBBanner(context, mID, mFlBanner);
            } else hideParent(mFlBanner);
        } else hideParent(mFlBanner);
    }

    private static void showFBBanner(Context context, String mID, FrameLayout mFlBanner) {

        AdView adView = new AdView(context, mID, AdSize.BANNER_HEIGHT_50);
        mFlBanner.removeAllViews();
        mFlBanner.addView(adView);
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                printAdsLog("Fb Bnr", "onError");
                hideParent(mFlBanner);
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {
                AppOpenManager.isFromApp = true;
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());

    }

    private static void hideParent(FrameLayout mFlBanner) {
        if (mFlBanner != null)
            mFlBanner.setVisibility(View.GONE);
    }
}
