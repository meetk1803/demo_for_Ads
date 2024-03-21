package com.example.demo_for_ads;

import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isEmptyStr;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValue;
import static com.example.demo_for_ads.gfc_adshelper.All_Ads_ID.adsValueBanner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.example.demo_for_ads.gfc_adshelper.Ads_Helper;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Alternate_Banner_Ads;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Custom_Link_Banner_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.banner.Facebook_BannerHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.middle.Facebook_MiddleHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.middle.MiddleHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button start = findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isEmptyStr(All_Ads_ID.Network_Full)) {
                    if (All_Ads_ID.Network_Full.equalsIgnoreCase("a")) {
                        if (!isEmptyStr(All_Ads_ID.Admob_Full_Middle) && !isEmptyStr(All_Ads_ID.FB_Full) && !isEmptyStr(All_Ads_ID.Custom_FullAds)) {
                            switch (adsValue) {
                                case 1:
                                    //adsString = "facebook";
                                    Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                                        @Override
                                        public void onAdclosed() {
                                            gonext();

                                        }

                                        @Override
                                        public void onNoNeedToShow() {
                                            gonext();
                                        }

                                        @Override
                                        public void onAdFailedToLoad() {
                                            if (All_Ads_ID.isbacklinkFail) {
                                                All_Ads_ID.isFromLinkAdClick = true;

                                                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                                Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                            } else {
                                                gonext();

                                            }
                                        }
                                    });
                                    break;
                                case 2:
                                    //adsString = "custom";
                                    if (All_Ads_ID.isLinkAds) {
                                        All_Ads_ID.isFromLinkAdClick = true;
                                        //AdsID.isFromAdpAdClick = true;

                                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                        customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                        Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                                    } else {
                                        gonext();

                                    }
                                    break;
                                default:
                                    //adsString = "google";
                                    MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity.this, new MiddleHelper.MyMiddleAdsListner() {
                                        @Override
                                        public void onAdclosed() {
                                            gonext();

                                        }

                                        @Override
                                        public void onNoNeedToShow() {
                                            gonext();

                                        }

                                        @Override
                                        public void onAdFailedToLoad() {
                                            if (All_Ads_ID.isbacklinkFail) {
                                                All_Ads_ID.isFromLinkAdClick = true;

                                                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                                customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                                Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                            } else {
                                                gonext();

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

                            if (All_Ads_ID.full_ads) {
                                MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity.this, new MiddleHelper.MyMiddleAdsListner() {
                                    @Override
                                    public void onAdclosed() {
                                        gonext();
                                    }

                                    @Override
                                    public void onNoNeedToShow() {
                                        gonext();
                                    }

                                    @Override
                                    public void onAdFailedToLoad() {
                                        if (All_Ads_ID.isbacklinkFail) {
                                            All_Ads_ID.isFromLinkAdClick = true;

                                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                            customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                            Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                        } else {
                                            gonext();
                                        }
                                    }
                                });
                                All_Ads_ID.full_ads = false;
                            } else {
                                Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                                    @Override
                                    public void onAdclosed() {
                                        gonext();
                                    }

                                    @Override
                                    public void onNoNeedToShow() {
                                       gonext();
                                    }

                                    @Override
                                    public void onAdFailedToLoad() {
                                        if (All_Ads_ID.isbacklinkFail) {
                                            All_Ads_ID.isFromLinkAdClick = true;

                                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                            customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                            Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                        } else {
                                            gonext();
                                        }
                                    }
                                });
                                All_Ads_ID.full_ads = true;
                            }
                        } else if (!isEmptyStr(All_Ads_ID.Admob_Full_Middle) && !isEmptyStr(All_Ads_ID.Custom_FullAds)) {
                            if (All_Ads_ID.full_ads) {
                                MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity.this, new MiddleHelper.MyMiddleAdsListner() {
                                    @Override
                                    public void onAdclosed() {
                                       gonext();
                                    }

                                    @Override
                                    public void onNoNeedToShow() {
                                      gonext();
                                    }

                                    @Override
                                    public void onAdFailedToLoad() {
                                        if (All_Ads_ID.isbacklinkFail) {
                                            All_Ads_ID.isFromLinkAdClick = true;

                                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                            customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                            Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                        } else {
                                            gonext();
                                        }
                                    }
                                });
                                All_Ads_ID.full_ads = false;
                            } else {
                                if (All_Ads_ID.isLinkAds) {
                                    All_Ads_ID.isFromLinkAdClick = true;
                                    //AdsID.isFromAdpAdClick = true;

                                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                    customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                    Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                                } else {
                                    gonext();
                                }
                                All_Ads_ID.full_ads = true;
                            }
                        } else if (!isEmptyStr(All_Ads_ID.FB_Full) && !isEmptyStr(All_Ads_ID.Custom_FullAds)) {

                            if (All_Ads_ID.full_ads) {
                                Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                                    @Override
                                    public void onAdclosed() {
                                        gonext();
                                    }

                                    @Override
                                    public void onNoNeedToShow() {
                                        gonext();
                                    }

                                    @Override
                                    public void onAdFailedToLoad() {
                                        if (All_Ads_ID.isbacklinkFail) {
                                            All_Ads_ID.isFromLinkAdClick = true;

                                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                            customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                            Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                        } else {
                                            gonext();
                                        }
                                    }
                                });
                                All_Ads_ID.full_ads = false;
                            } else {
                                if (All_Ads_ID.isLinkAds) {
                                    All_Ads_ID.isFromLinkAdClick = true;
                                    //AdsID.isFromAdpAdClick = true;

                                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                    customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                    Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                                } else {
                                    gonext();
                                }
                                All_Ads_ID.full_ads = true;
                            }
                        }

                    } else if (All_Ads_ID.Network_Full.equalsIgnoreCase("g")) {
                        MiddleHelper.getSharedInstance().showQuickMiddleAd(MainActivity.this, new MiddleHelper.MyMiddleAdsListner() {
                            @Override
                            public void onAdclosed() {
                                gonext();
                            }

                            @Override
                            public void onNoNeedToShow() {
                                gonext();
                            }

                            @Override
                            public void onAdFailedToLoad() {
                                if (All_Ads_ID.isbacklinkFail) {
                                    All_Ads_ID.isFromLinkAdClick = true;

                                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                    customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                    Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                } else {
                                    gonext();
                                }
                            }
                        });
                    } else if (All_Ads_ID.Network_Full.equalsIgnoreCase("f")) {
                        Facebook_MiddleHelper.getSharedInstance().checkNShowInterstitialAd(MainActivity.this, new Facebook_MiddleHelper.MyMiddleAdsListner() {
                            @Override
                            public void onAdclosed() {
                                gonext();
                            }

                            @Override
                            public void onNoNeedToShow() {
                                gonext();
                            }

                            @Override
                            public void onAdFailedToLoad() {
                                if (All_Ads_ID.isbacklinkFail) {
                                    All_Ads_ID.isFromLinkAdClick = true;

                                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                                    customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                                    Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));
                                } else {
                                    gonext();
                                }
                            }
                        });
                    } else if (All_Ads_ID.Network_Full.equalsIgnoreCase("c")) {
                        if (All_Ads_ID.isLinkAds) {
                            All_Ads_ID.isFromLinkAdClick = true;
                            //AdsID.isFromAdpAdClick = true;

                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                            customIntent.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                            Ads_Helper.openCustomTab(MainActivity.this, customIntent.build(), Uri.parse(All_Ads_ID.mLinkads));

                        } else {
                            gonext();
                        }
                    } else {
                        gonext();
                    }
                } else {

                    gonext();
                }


            }
        });

        //Banner
        FrameLayout mFlBanner = findViewById(R.id.mFlBanner);
        if (!isEmptyStr(All_Ads_ID.Network_Banner)) {
            if (All_Ads_ID.Network_Banner.equalsIgnoreCase("a")) {
                Alternate_Banner_Ads.checkAdsStatus(adsValueBanner, MainActivity.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("g")) {
                Banner_Helper.showBannerAd(MainActivity.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("f")) {
                Facebook_BannerHelper.showBannerAd(MainActivity.this, mFlBanner);
            } else if (All_Ads_ID.Network_Banner.equalsIgnoreCase("c")) {
                Custom_Link_Banner_Helper.showCustomLinkBannerAd(MainActivity.this, mFlBanner);
            } else {
                mFlBanner.setVisibility(View.GONE);
            }
        } else {
            mFlBanner.setVisibility(View.GONE);
        }

    }

    private void gonext() {
        startActivity(new Intent(MainActivity.this, MainActivity2.class));
    }
}