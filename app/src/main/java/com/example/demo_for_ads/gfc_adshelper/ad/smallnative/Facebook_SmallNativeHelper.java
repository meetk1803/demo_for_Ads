package com.example.demo_for_ads.gfc_adshelper.ad.smallnative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class Facebook_SmallNativeHelper {

    public static String TAG = "FbSmallNativeHelper_ ";
    private static Context mContext;
    private static NativeAdLayout nativeAdLayout;
    private static FrameLayout frameLayout;
    private static LinearLayout adView;

    public static void showFBSmallNativeAd(Context context, FrameLayout mFlNative) {
        mContext = context;
        frameLayout = mFlNative;

        if (isNetworkAvailable(context)) {

            showAdSmallNative(context, mFlNative);

        } else hideParentsmall(mContext, mFlNative);

    }

    private static void showAdSmallNative(Context context, FrameLayout mFlNative) {
        Facebook_OtherSmallNativeHelper.showFBSmallNative(context, mFlNative);
    }

    public static void loadAdSmallNative(Context context) {

        String mID = All_Ads_ID.FB_smallNative;

        Facebook_OtherSmallNativeHelper.loadNativeAd(context, null, mID);
    }

    public static void hideParentsmall(Context mContext, FrameLayout mFlBanner) {
        printAdsLog(TAG + "hideParent:: ", " " + mContext);
        printAdsLog(TAG + "hideParent:: ", " " + mFlBanner);

        if (mFlBanner != null)
            mFlBanner.setVisibility(View.GONE);
    }

    public static void inflateAd(NativeAd nativeAd) {

        Ads_Helper.printAdsLog("inflateAd",".."+nativeAd);
        Ads_Helper.printAdsLog("inflateAd",".."+frameLayout);

        nativeAd.unregisterView();

        final View view = LayoutInflater.from(mContext).inflate(R.layout.layout_fb_nativeadlayout, frameLayout, false);

        // you can set text here too:q
        final NativeAdLayout nativeAdLayout = (NativeAdLayout) view.findViewById(R.id.native_ad_container);


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View adView = inflater.inflate(R.layout.layout_fb_native_small_childview, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        frameLayout.removeAllViews();
        frameLayout.addView(nativeAdLayout);

        Ads_Helper.printAdsLog("inflateAd",".."+nativeAdLayout);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(mContext, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
       // MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
//        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        MaterialButton nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
//        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());


        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdIcon, clickableViews);

    }

}
