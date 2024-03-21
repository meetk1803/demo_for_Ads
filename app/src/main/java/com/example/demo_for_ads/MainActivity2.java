package com.example.demo_for_ads;

import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueBanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Alternate_Banner_Ads;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Custom_Link_Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Facebook_BannerHelper;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Banner
        FrameLayout mFlBanner = findViewById(R.id.mFlBanner);
        if (!isEmptyStr(All_Ads_ID.Network_Banner)) {
            if (All_Ads_ID.Network_Banner.equalsIgnoreCase("a")) {
                Alternate_Banner_Ads.checkAdsStatus(adsValueBanner, MainActivity2.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("g")) {
                Banner_Helper.showBannerAd(MainActivity2.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("f")) {
                Facebook_BannerHelper.showBannerAd(MainActivity2.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("c")) {
                Custom_Link_Banner_Helper.showCustomLinkBannerAd(MainActivity2.this, mFlBanner);
            } else {
                mFlBanner.setVisibility(View.GONE);
            }
        } else {
            mFlBanner.setVisibility(View.GONE);
        }
    }
}