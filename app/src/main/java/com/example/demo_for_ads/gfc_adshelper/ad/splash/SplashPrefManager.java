package com.example.demo_for_ads.gfc_adshelper.ad.splash;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SplashPrefManager {

    private Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SplashPrefManager(Context context) {
        this.context = context;

        if (pref == null)
            pref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);

        editor = pref.edit();
    }

    public Context getContext() {
        return context;
    }

    public boolean getBoolean(String key) {
        return pref.getBoolean(key, true);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
}
