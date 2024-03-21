package com.example.demo_for_ads.gfc_adshelper.ad.banner;


import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.getAdxAdsRequest;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

public class Banner_Helper {

    public static void showBannerAd(Context context, FrameLayout mFlBanner) {
        if (isNetworkAvailable(context)) {

            String mID = All_Ads_ID.Admob_Banner;
            if (!isEmptyStr(mID)) {
                showADXBanner(context, mID, mFlBanner);
            } else hideParent(mFlBanner);
        } else hideParent(mFlBanner);
    }

    private static void showADXBanner(Context context, String mID, FrameLayout mFlBanner) {
        AdView adxView = new AdView(context);
        adxView.setAdSize(AdSize.BANNER);
        adxView.setAdUnitId(mID);
        adxView.loadAd(getAdxAdsRequest());
        mFlBanner.removeAllViews();
        mFlBanner.addView(adxView);

        adxView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                printAdsLog("ADX Bnr", "onAdFailedToLoad");
                hideParent(mFlBanner);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                AppOpenManager.isFromApp = true;
            }
        });
    }

    private static void hideParent(FrameLayout mFlBanner) {
        if (mFlBanner != null)
            mFlBanner.setVisibility(View.GONE);
    }
}
