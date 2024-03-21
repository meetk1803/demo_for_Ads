package com.example.demo_for_ads;

import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.MyAdListner;
import com.example.demo_for_ads.gfc_adshelper.Remote_Helper;
import com.example.demo_for_ads.gfc_adshelper.ad.bignative.BigNativeHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.bignative.Facebook_BigNativeHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.example.demo_for_ads.gfc_adshelper.ad.smallnative.Facebook_SmallNativeHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.smallnative.SmallNativeHelper;
import com.example.demo_for_ads.gfc_adshelper.ad.splash.SplashHelper;

public class splash_screen extends AppCompatActivity {
    private LottieAnimationView loadingAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loadingAnimationView = findViewById(R.id.loadingAnimationView);

        // Start the animation
        loadingAnimationView.playAnimation();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);




        if (isNetworkAvailable(this)) {
            Remote_Helper.loadConfig(splash_screen.this, configListner);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToNext();
                }
            }, 2000);
        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent is used to switch from one activity to another.
                Intent i = new Intent(splash_screen.this, MainActivity.class);
                startActivity(i); // invoke the SecondActivity.
                finish(); // the current activity will get finished.
            }
        }, 3000);
    }


    Remote_Helper.configListner configListner = new Remote_Helper.configListner() {
        @Override
        public void onLoad() {
            if (All_Ads_ID.Network_BgNative.equalsIgnoreCase("a")) {
                BigNativeHelper.loadAdxBgNative(splash_screen.this);
                Facebook_BigNativeHelper.loadAdxBgNative(splash_screen.this);
            } else if (All_Ads_ID.Network_BgNative.equalsIgnoreCase("g")) {
                BigNativeHelper.loadAdxBgNative(splash_screen.this);
            } else if (All_Ads_ID.Network_BgNative.equalsIgnoreCase("f")) {
                Facebook_BigNativeHelper.loadAdxBgNative(splash_screen.this);
            }

            if (All_Ads_ID.Network_SmallNative.equalsIgnoreCase("a")) {
                SmallNativeHelper.loadAdxSmallNative(splash_screen.this);
                Facebook_SmallNativeHelper.loadAdSmallNative(splash_screen.this);
            } else if (All_Ads_ID.Network_SmallNative.equalsIgnoreCase("g")) {
                SmallNativeHelper.loadAdxSmallNative(splash_screen.this);
            } else if (All_Ads_ID.Network_SmallNative.equalsIgnoreCase("f")) {
                Facebook_SmallNativeHelper.loadAdSmallNative(splash_screen.this);
            }

            SplashHelper.getSharedInstance().showSplashAd(splash_screen.this, new MyAdListner() {
                @Override
                public void onAdclosed() {
                    goToNext();
                }

                @Override
                public void onNoNeedToShow() {
                    goToNext();
                }

                @Override
                public void onAdFailedToLoad() {
                    goToNext();
                }
            });
        }

        @Override
        public void onFail() {
            printAdsLog("configListner", "onFail");
            goToNext();
        }
    };

    private void goToNext() {
        Intent i = new Intent(splash_screen.this, MainActivity.class);
        startActivity(i); // invoke the SecondActivity.
        finish(); // the current activity will get finished.
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (All_Ads_ID.isFromLinkAdClick) {
            All_Ads_ID.isFromLinkAdClick = false;
            AppOpenManager.isFromApp = true;
            goToNext();
        }
    }

}