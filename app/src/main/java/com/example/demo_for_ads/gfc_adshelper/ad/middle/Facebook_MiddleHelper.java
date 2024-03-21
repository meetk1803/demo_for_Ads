package com.example.demo_for_ads.gfc_adshelper.ad.middle;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;

import android.content.Context;


public class Facebook_MiddleHelper {

    public static boolean isFromAdapterClick = false;


    public interface MyMiddleAdsListner {

        public void onAdclosed();

        public void onNoNeedToShow();

        public void onAdFailedToLoad();
    }


    Context mContext;
    MyMiddleAdsListner mMiddleAdsListner;

    private static Facebook_MiddleHelper sharedInstance;

    public static Facebook_MiddleHelper getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new Facebook_MiddleHelper();
        }

        return sharedInstance;
    }


    public void checkNShowInterstitialAd(Context context, MyMiddleAdsListner myMiddleAdsListner) {
        mContext = context;
        mMiddleAdsListner = myMiddleAdsListner;

        if (isNetworkAvailable(context)) {

            loadShowIntrestitialAd();

        } else {
            myMiddleAdsListner.onAdFailedToLoad(); // no need to load another ads without internet!
        }
    }

    private void checkMiddleAdType() {
        loadShowIntrestitialAd();
    }

    private void loadShowIntrestitialAd() {

        Facebook_MiddleFullUtils.getSharedInstance().checkNShowMiddleInterstitialAd(mContext, mMiddleAdsListner);
    }

}
