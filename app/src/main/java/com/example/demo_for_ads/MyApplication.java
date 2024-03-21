package com.example.demo_for_ads;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MyApplication extends Application {

    public static AppOpenManager appOpenManager;

    private static MyApplication singleton = null;

    public static MyApplication getInstance() {
        if (singleton == null) {
            singleton = new MyApplication();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        singleton = this;

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });

        setAppOpenManager();
    }

    public void setAppOpenManager() {
        if (MyApplication.appOpenManager == null)
            appOpenManager = new AppOpenManager(this);
    }

}
