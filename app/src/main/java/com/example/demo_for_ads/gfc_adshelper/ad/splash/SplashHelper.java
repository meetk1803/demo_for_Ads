package com.example.demo_for_ads.gfc_adshelper.ad.splash;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.example.demo_for_ads.MyApplication;
import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.MyAdListner;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;


public class SplashHelper {

    Context mContext;

    MyAdListner myAdListner;

    private static SplashHelper sharedInstance;

    public SplashPrefManager splashPrefManager;

    public static SplashHelper getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new SplashHelper();
        }

        return sharedInstance;
    }

    public void showSplashAd(Context context, MyAdListner mySplashAdsListner) {
        mContext = context;
        myAdListner = mySplashAdsListner;

        splashPrefManager = new SplashPrefManager(context);


        if (isNetworkAvailable(context)) {

            String mADType = All_Ads_ID.Network_Splash_type;
            All_Ads_ID.SplashAds = splashPrefManager.getBoolean(All_Ads_ID.SplashPreKey);

            printAdsLog("splash mADType ", " " + mADType);

            if (!isEmptyStr(All_Ads_ID.Network_Splash_type)) {
                if (All_Ads_ID.Network_Splash_type.equalsIgnoreCase("a")) {
                    printAdsLog("SplashAd ", " " + All_Ads_ID.SplashAds + " tf" + All_Ads_ID.SplashAds);
                    if (All_Ads_ID.SplashAds) {
                        loadShowOpenAd();
                    } else {
                        printAdsLog("SplashAd ", "else " + All_Ads_ID.SplashAds);
                        loadShowIntrestitialAd(context);
                        if (MyApplication.getInstance() != null)
                            MyApplication.getInstance().setAppOpenManager();
                    }
                    splashPrefManager.setBoolean(All_Ads_ID.SplashPreKey, !All_Ads_ID.SplashAds);
                } else if (All_Ads_ID.Network_Splash_type.equalsIgnoreCase("o")) {
                    loadShowOpenAd();
                } else if (All_Ads_ID.Network_Splash_type.equalsIgnoreCase("c")) {
                    loadShowIntrestitialAd(context);
                    if (MyApplication.getInstance() != null)
                        MyApplication.getInstance().setAppOpenManager();
                } else {
                    splashFailedToLoad();
                }
            } else {
                splashFailedToLoad();
            }

        } else {
            splashFailedToLoad(); // no need to load another ads without internet!
        }
    }

    private void loadShowOpenAd() {
        String adID = All_Ads_ID.Admob_AppOpenAds;
        if (!Ads_Helper.isEmptyStr(adID)) {
            SplashOpenAdUtils.getSharedInstance().initOpenAd(mContext, adID, myAdListner);
        } else {
            splashFailedToLoad();
        }
    }

    private void loadShowIntrestitialAd(Context context) {

        if (isNetworkAvailable(context)) {
            if (All_Ads_ID.isLinkAds == true) {
                All_Ads_ID.isFromLinkAdClick = true;

                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
                openCustomTab(context, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

            } else {
                splashFailedToLoad();
            }
        } else {
            splashFailedToLoad();
        }
    }

    public static void openCustomTab(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        try {
            String packageName = "com.android.chrome";
            if (packageName != null) {

                // we are checking if the package name is not null
                // if package name is not null then we are calling
                // that custom chrome tab with intent by passing its
                // package name.
                customTabsIntent.intent.setPackage(packageName);

                // in that custom tab intent we are passing
                // our url which we have to browse.
                customTabsIntent.launchUrl(activity, uri);
                AppOpenManager.isFromApp = true;
            } else {
                // if the custom tabs fails to load then we are simply
                // redirecting our user to users device default browser.
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                AppOpenManager.isFromApp = true;
            }
        } catch (Exception e) {
        }
    }

    private void splashFailedToLoad() {
        if (MyApplication.getInstance() != null)
            MyApplication.getInstance().setAppOpenManager();


        if (myAdListner != null)
            myAdListner.onAdFailedToLoad();
    }

}
