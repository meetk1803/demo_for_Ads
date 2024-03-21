package com.example.demo_for_ads.gfc_adshelper.ad.smallnative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.button.MaterialButton;

public class SmallNativeHelper {
    public static String TAG = "SmallNativeHelper_ ";
    public static Context mContext;

    public static void showSmallNativeAd(Context context, FrameLayout mFlNative) {
        mContext = context;

        if (isNetworkAvailable(context)) {
            showAdxSmallNative(context, mFlNative);

        } else hideSmallParent(mContext, mFlNative);

    }

    private static void showAdxSmallNative(Context context, FrameLayout mFlNative) {
        OtherSmallNativeHelper.showAdxSmallNative(context, mFlNative);
    }

    public static void loadAdxSmallNative(Context context) {

        String mID = All_Ads_ID.Admob_SmallNative;
        if (!isEmptyStr(mID)) {
            OtherSmallNativeHelper.loadNativeAd(context, null, mID);
        }
    }


    public static void hideSmallParent(Context mContext, FrameLayout mFlBanner) {
        Ads_Helper.printAdsLog(TAG + "hideParent:: ", " " + mContext);
        Ads_Helper.printAdsLog(TAG + "hideParent:: ", " " + mFlBanner);

        if (mFlBanner != null)
            mFlBanner.setVisibility(View.GONE);
    }


    public static NativeAdView getSmallNativeAdView(Context context, FrameLayout frameLayout) {
        final NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.ad_unified_small, frameLayout, false);

        if (adView == null)
            return null;

        TextView textView = adView.findViewById(R.id.ad_label);
        MaterialButton btn = adView.findViewById(R.id.ad_call_to_action);


        // Register the view used for each individual asset.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
//        adView.setPriceView(adView.findViewById(R.id.ad_price));
//        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
//        adView.setStoreView(adView.findViewById(R.id.ad_store));
//        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        return adView;
    }

    public static void populateSmallNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        try {
            // Some assets are guaranteed to be in every UnifiedNativeAd.
//            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

            // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
            // check before trying to display them.
            NativeAd.Image icon = nativeAd.getIcon();
            if (icon == null) {
                adView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }

          /*  if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }*/

           /* if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }*/

//            if (nativeAd.getStarRating() == null) {
//                adView.getStarRatingView().setVisibility(View.INVISIBLE);
//            } else {
//                ((RatingBar) adView.getStarRatingView())
//                        .setRating(nativeAd.getStarRating().floatValue());
//                adView.getStarRatingView().setVisibility(View.VISIBLE);
//            }
//
//            if (nativeAd.getAdvertiser() == null) {
//                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
//            } else {
//                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
//                adView.getAdvertiserView().setVisibility(View.VISIBLE);
//            }

            // Assign native ad object to the native view.
            adView.setNativeAd(nativeAd);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
