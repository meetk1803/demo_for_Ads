package com.example.demo_for_ads.gfc_adshelper.ad.bignative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Pattern;

public class BigNativeHelper {

    public static String TAG = "BigNativeHelper_ ";
    public static Context mContext;

    public static void showBgNativeAd(Context context, FrameLayout mFlNative) {
        mContext = context;

        if (isNetworkAvailable(context)) {

            showAdxBgNative(context, mFlNative);

        } else hideParent(mContext, mFlNative);

    }

    private static void showAdxBgNative(Context context, FrameLayout mFlNative) {
        OtherBgNativeHelper.showAdxBgNative(context, mFlNative);
    }

    public static void loadAdxBgNative(Context context) {

        String mID = All_Ads_ID.Admob_BgNative;

        if (!isEmptyStr(mID)) {
            OtherBgNativeHelper.loadNativeAd(context, null, mID);
        }
    }

    public static void hideParent(Context mContext, FrameLayout mFlBanner) {
        printAdsLog(TAG + "hideParent:: ", " " + mContext);
        printAdsLog(TAG + "hideParent:: ", " " + mFlBanner);

        if (mFlBanner != null)
            mFlBanner.setVisibility(View.GONE);
    }


    public static NativeAdView getNativeAdView(Context context, FrameLayout frameLayout) {
        final NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.ad_unified_bg_native, frameLayout, false);

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


    public static void populateBgNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        try {
            // Some assets are guaranteed to be in every UnifiedNativeAd.
            adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());

            // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
            ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

            // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
            // check before trying to display them.
            NativeAd.Image icon = nativeAd.getIcon();

            if (icon == null) {
                adView.getIconView().setVisibility(View.INVISIBLE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }

            /*if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }

            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }

            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }

            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }*/

            // Assign native ad object to the native view.
            adView.setNativeAd(nativeAd);

            // Get the video controller for the ad. One will always be provided, even if the ad doesn't
            // have a video asset.
            VideoController vc = nativeAd.getMediaContent().getVideoController();

            // Updates the UI to say whether or not this ad has a video asset.
            if (vc.hasVideoContent()) {
                // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
                // VideoController will call methods on this object when events occur in the video
                // lifecycle.
                vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                    @Override
                    public void onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.
                        super.onVideoEnd();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int HextoColor(String color) {
        // #ff00CCFF
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#00ffffff";
        }//from w  w w .ja  v a2s.  c  om

        return Color.parseColor(color);
    }
}
