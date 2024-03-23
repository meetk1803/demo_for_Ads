package com.example.demo_for_ads;

import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValue;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueBanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Alternate_Banner_Ads;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Custom_Link_Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Facebook_BannerHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.middle.Facebook_MiddleHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.middle.MiddleHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;

public class MainActivity2 extends AppCompatActivity {

   int a = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       Button fragment = findViewById(R.id.fragment);
       Button fragment1 = findViewById(R.id.fragment1);
       Button fragment2 = findViewById(R.id.fragment2);

        fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity("blankFragment");
            }
        });
        fragment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(MainActivity2 .this,MainActivity3.class);
               startActivity(i);
            }
        });
        fragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openactivity("blankFragment1");}
        });
        // Banner
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
    private void openactivity(String label)  {
    //    a = 0;
        if (!isEmptyStr(All_Ads_ID.Network_Full)) {
        if (All_Ads_ID.Network_Full.equalsIgnoreCase("a")) {
            if (!isEmptyStr(All_Ads_ID.Admob_Full_Middle) && !isEmptyStr(All_Ads_ID.FB_Full) && !isEmptyStr(All_Ads_ID.Custom_FullAds)) {
                switch (adsValue) {
                    case 1:
                        //adsString = "facebook";
                        Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity2.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                            @Override
                            public void onAdclosed() {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                            @Override
                            public void onNoNeedToShow() {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                            @Override
                            public void onAdFailedToLoad() {
                                if (All_Ads_ID.isbacklinkFail == true) {
                                    All_Ads_ID.isFromLinkAdClick = true;
                                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                    customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                                    Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                } else {
                                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                    intent.putExtra("label", label);
                                    startActivity(intent);
                                }
                            }
                        });
                        break;
                    case 2:
                        //adsString = "custom";
                        if (All_Ads_ID.isLinkAds == true) {
                            All_Ads_ID.isFromLinkAdClick = true;
                            //AdsID.isFromAdpAdClick = true;

                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                            customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                            Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                        } else {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }
                        break;
                    default:
                        //adsString = "google";
                        MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity2.this, new MiddleHelper.MyMiddleAdsListner() {
                            @Override
                            public void onAdclosed() {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }

                            @Override
                            public void onNoNeedToShow() {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                            @Override
                            public void onAdFailedToLoad() {
                                if (All_Ads_ID.isbacklinkFail == true) {
                                    All_Ads_ID.isFromLinkAdClick = true;

                                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                    customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                                    Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                } else {
                                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                    intent.putExtra("label", label);
                                    startActivity(intent);
                                }
                            }
                        });
                        break;
                }
                if (adsValue == 2) {
                    adsValue = 0;
                } else {
                    adsValue++;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_Full_Middle) && !isEmptyStr(All_Ads_ID.FB_Full)) {

                if (All_Ads_ID.full_ads == true) {
                    MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity2.this, new MiddleHelper.MyMiddleAdsListner() {
                        @Override
                        public void onAdclosed() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }

                        @Override
                        public void onNoNeedToShow() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }

                        @Override
                        public void onAdFailedToLoad() {
                            if (All_Ads_ID.isbacklinkFail == true) {
                                All_Ads_ID.isFromLinkAdClick = true;

                                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                                Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                        }
                    });
                    All_Ads_ID.full_ads = false;
                } else {
                    Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity2.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                        @Override
                        public void onAdclosed() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }

                        @Override
                        public void onNoNeedToShow() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }
                        @Override
                        public void onAdFailedToLoad() {
                            if (All_Ads_ID.isbacklinkFail == true) {
                                All_Ads_ID.isFromLinkAdClick = true;

                                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                                Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                        }
                    });
                    All_Ads_ID.full_ads = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.Admob_Full_Middle) && !isEmptyStr(All_Ads_ID.Custom_FullAds)) {
                if (All_Ads_ID.full_ads == true) {
                    MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity2.this, new MiddleHelper.MyMiddleAdsListner() {
                        @Override
                        public void onAdclosed() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }
                        @Override
                        public void onNoNeedToShow() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }
                        @Override
                        public void onAdFailedToLoad() {
                            if (All_Ads_ID.isbacklinkFail == true) {
                                All_Ads_ID.isFromLinkAdClick = true;

                                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                                Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                        }
                    });

                } else {
                    if (All_Ads_ID.isLinkAds == true) {
                        All_Ads_ID.isFromLinkAdClick = true;
                        //AdsID.isFromAdpAdClick = true;

                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                        customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                        Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                    } else {
                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                        intent.putExtra("label", label);
                        startActivity(intent);
                    }
                    All_Ads_ID.full_ads = true;
                }
            } else if (!isEmptyStr(All_Ads_ID.FB_Full) && !isEmptyStr(All_Ads_ID.Custom_FullAds)) {

                if (All_Ads_ID.full_ads == true) {
                    Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity2.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                        @Override
                        public void onAdclosed() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }
                        @Override
                        public void onNoNeedToShow() {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            intent.putExtra("label", label);
                            startActivity(intent);
                        }
                        @Override
                        public void onAdFailedToLoad() {
                            if (All_Ads_ID.isbacklinkFail == true) {
                                All_Ads_ID.isFromLinkAdClick = true;

                                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                                Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                            } else {
                                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                                intent.putExtra("label", label);
                                startActivity(intent);
                            }
                        }
                    });
                    All_Ads_ID.full_ads = false;
                } else {
                    if (All_Ads_ID.isLinkAds == true) {
                        All_Ads_ID.isFromLinkAdClick = true;
                        //AdsID.isFromAdpAdClick = true;
                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                        customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                        Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                    } else {
                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                        intent.putExtra("label", label);
                        startActivity(intent);
                    }
                    All_Ads_ID.full_ads = true;
                }
            }

        } else if (All_Ads_ID.Network_Full.equalsIgnoreCase("g")) {
            MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity2.this, new MiddleHelper.MyMiddleAdsListner() {
                @Override
                public void onAdclosed() {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("label", label);
                    startActivity(intent);
                }

                @Override
                public void onNoNeedToShow() {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("label", label);
                    startActivity(intent);
                }

                @Override
                public void onAdFailedToLoad() {
                    if (All_Ads_ID.isbacklinkFail == true) {
                        All_Ads_ID.isFromLinkAdClick = true;

                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                        customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                        Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                    } else {
                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                        intent.putExtra("label", label);
                        startActivity(intent);
                    }
                }
            });
        } else if (All_Ads_ID.Network_Full.equalsIgnoreCase("f")) {
            Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity2.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                @Override
                public void onAdclosed() {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("label", label);
                    startActivity(intent);
                }
                @Override
                public void onNoNeedToShow() {
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("label", label);
                    startActivity(intent);
                }
                @Override
                public void onAdFailedToLoad() {
                    if (All_Ads_ID.isbacklinkFail == true) {
                        All_Ads_ID.isFromLinkAdClick = true;

                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                        customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                        Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                    } else {
                        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                        intent.putExtra("label", label);
                        startActivity(intent);
                    }
                }
            });
        } else if (All_Ads_ID.Network_Full.equalsIgnoreCase("c")) {
            if (All_Ads_ID.isLinkAds == true) {
                All_Ads_ID.isFromLinkAdClick = true;
                //AdsID.isFromAdpAdClick = true;
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity2.this, R.color.colorPrimary));
                Ads_Helper.openCustomTab(MainActivity2.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

            } else {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("label", label);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            intent.putExtra("label", label);
            startActivity(intent);
        }
    } else {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        intent.putExtra("label", label);
        startActivity(intent);
    }
}
    @Override
    protected void onRestart() {
        super.onRestart();
        if (All_Ads_ID.isFromLinkAdClick) {
            All_Ads_ID.isFromLinkAdClick = false;
            AppOpenManager.isFromApp = true;
        //    gonext();
        }
    }

    private void gonext() {

        if (a == 1) {
            a = 0;
            startActivity(new Intent(MainActivity2.this, MainActivity3.class));
        } else if (a == 2) {
            a = 0;
            startActivity(new Intent(MainActivity2.this, MainActivity3.class));
        }
    }
}