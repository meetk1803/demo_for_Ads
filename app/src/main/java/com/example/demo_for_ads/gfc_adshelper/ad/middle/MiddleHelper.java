package com.example.demo_for_ads.gfc_adshelper.ad.middle;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;

import android.content.Context;


public class MiddleHelper {

    public static boolean isFromAdapterClick = false;

    public interface MyMiddleAdsListner {

        public void onAdclosed();

        public void onNoNeedToShow();

        public void onAdFailedToLoad();
    }


    Context mContext;
    MyMiddleAdsListner mMiddleAdsListner;

    private static MiddleHelper sharedInstance;

    public static int mUserClickCounter = 0;

    public static MiddleHelper getSharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new MiddleHelper();
        }

        return sharedInstance;
    }

    public void showQuickMiddleAd(Context context, MyMiddleAdsListner myMiddleAdsListner) {
        mContext = context;
        mMiddleAdsListner = myMiddleAdsListner;

        if (isNetworkAvailable(context)) {
            checkMiddleAdType();

        } else {
            myMiddleAdsListner.onAdFailedToLoad(); // no need to load another ads without internet!
        }
    }


    private void checkMiddleAdType() {
        loadShowIntrestitialAd();
    }

    private void loadShowIntrestitialAd() {
        MiddleFullAdUtils.getSharedInstance().checkNShowMiddleInterstitialAd(mContext, mMiddleAdsListner);
    }

}
