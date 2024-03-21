package com.example.demo_for_ads.gfc_adshelper.ad.banner;




import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueBanner;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;


public class Alternate_Banner_Ads {

    public static void checkAdsStatus(int value, Context context, FrameLayout mFlBanner) {
        if (isNetworkAvailable(context)) {
            if (!isEmptyStr(All_Ads_ID.Admob_Banner) && !isEmptyStr(All_Ads_ID.FB_banner) && !isEmptyStr(All_Ads_ID.Custom_Banner)) {
                switch (value) {
                    case 1:
                        //"facebook";
                        Facebook_BannerHelper.showBannerAd(context, mFlBanner);
                        break;
                    case 2:
                        //"custom";
                        Custom_Link_Banner_Helper.showCustomLinkBannerAd(context, mFlBanner);
                        break;
                    default:
                        //"google";
                        Banner_Helper.showBannerAd(context, mFlBanner);
                        break;
                }
                if (adsValueBanner == 2) {
                    adsValueBanner = 0;
                } else {
                    adsValueBanner++;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_Banner) && !isEmptyStr(All_Ads_ID.FB_banner)) {
                if (All_Ads_ID.banner == true) {
                    Banner_Helper.showBannerAd(context, mFlBanner);
                    All_Ads_ID.banner = false;
                } else {
                    Facebook_BannerHelper.showBannerAd(context, mFlBanner);
                    All_Ads_ID.banner = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_Banner) && !isEmptyStr(All_Ads_ID.Custom_Banner)) {
                if (All_Ads_ID.banner == true) {
                    Banner_Helper.showBannerAd(context, mFlBanner);
                    All_Ads_ID.banner = false;
                } else {
                    Custom_Link_Banner_Helper.showCustomLinkBannerAd(context, mFlBanner);
                    All_Ads_ID.banner = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.FB_banner) && !isEmptyStr(All_Ads_ID.Custom_Banner)) {
                Toast.makeText(context, "CubANNER", Toast.LENGTH_SHORT).show();
                if (All_Ads_ID.banner == true) {
                    Facebook_BannerHelper.showBannerAd(context, mFlBanner);
                    All_Ads_ID.banner = false;
                } else {
                    Custom_Link_Banner_Helper.showCustomLinkBannerAd(context, mFlBanner);
                    All_Ads_ID.banner = true;
                }
            }
        } else {
            mFlBanner.setVisibility(View.GONE);
        }
    }

}
