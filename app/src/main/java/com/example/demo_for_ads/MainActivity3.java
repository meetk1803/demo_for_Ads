package com.example.demo_for_ads;

import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueBanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Alternate_Banner_Ads;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Custom_Link_Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Facebook_BannerHelper;

public class MainActivity3 extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        String level = getIntent().getStringExtra("label");

        if (level != null) {
            loadFragment(level);
        }
        //Banner
       FrameLayout mFlBanner = findViewById(R.id.mFlBanner);
        if (!isEmptyStr(All_Ads_ID.Network_Banner)) {
            if (All_Ads_ID.Network_Banner.equalsIgnoreCase("a")) {
                Alternate_Banner_Ads.checkAdsStatus(adsValueBanner, MainActivity3.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("g")) {
                Banner_Helper.showBannerAd(MainActivity3.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("f")) {
                Facebook_BannerHelper.showBannerAd(MainActivity3.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("c")) {
                Custom_Link_Banner_Helper.showCustomLinkBannerAd(MainActivity3.this, mFlBanner);
            } else {
                mFlBanner.setVisibility(View.GONE);
            }
        } else {
            mFlBanner.setVisibility(View.GONE);
        }
    }

    private void loadFragment(String label) {
        Fragment fragment;
        switch (label) {
            case "blankFragment":
                fragment = new BlankFragment();
                break;
            case "blankFragment1":
                fragment = new BlankFragment2();
                break;
            default:
                return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }
}