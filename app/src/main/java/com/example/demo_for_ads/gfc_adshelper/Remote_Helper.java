package com.example.demo_for_ads.gfc_adshelper;




import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;
import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.printAdsLog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;


public class Remote_Helper {
    public interface configListner {
        public void onLoad();

        public void onFail();
    }

    public static void loadConfig(Context context, configListner configListner) {

        if (isNetworkAvailable(context)) {


            FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            HashMap<String, Object> firebaseDefaultMap = new HashMap<>();
            FirebaseRemoteConfigSettings configSettings = new Builder()
                    .setMinimumFetchIntervalInSeconds(1)
                    .setFetchTimeoutInSeconds(5)
                    .build();
            mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
            mFirebaseRemoteConfig.setDefaultsAsync(firebaseDefaultMap);

            mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(@NonNull Task<Boolean> task) {
                    if (task.isSuccessful()) {
                        try {

                            All_Ads_ID.Network_Splash_type = mFirebaseRemoteConfig.getString("network_type_splash");
                            All_Ads_ID.Network_Banner = mFirebaseRemoteConfig.getString("network_type_banner");
                            All_Ads_ID.Network_SmallNative = mFirebaseRemoteConfig.getString("network_type_small_native");
                            All_Ads_ID.Network_BgNative = mFirebaseRemoteConfig.getString("network_type_big_native");
                            All_Ads_ID.Network_Full = mFirebaseRemoteConfig.getString("network_type_interstitial");

                            All_Ads_ID.Admob_AppOpenAds = mFirebaseRemoteConfig.getString("admob_appopen_id");
                            All_Ads_ID.Admob_Full_Middle = mFirebaseRemoteConfig.getString("admob_interstitial_middle_id");
                            All_Ads_ID.Admob_Banner = mFirebaseRemoteConfig.getString("admob_banner_id");
                            All_Ads_ID.Admob_SmallNative = mFirebaseRemoteConfig.getString("admob_small_native_id");
                            All_Ads_ID.Admob_BgNative = mFirebaseRemoteConfig.getString("admob_big_native_id");

                            All_Ads_ID.FB_banner = mFirebaseRemoteConfig.getString("fb_banner_id");
                            All_Ads_ID.FB_smallNative = mFirebaseRemoteConfig.getString("fb_small_native_id");
                            All_Ads_ID.FB_BgNative = mFirebaseRemoteConfig.getString("fb_big_native_id");
                            All_Ads_ID.FB_Full = mFirebaseRemoteConfig.getString("fb_interstitial_id");

                            All_Ads_ID.mLinkads = mFirebaseRemoteConfig.getString("custom_link");
                            All_Ads_ID.isLinkAds = Boolean.parseBoolean(mFirebaseRemoteConfig.getString("custom_link_for_intersitital_status"));
                            All_Ads_ID.isbacklinkFail = Boolean.parseBoolean(mFirebaseRemoteConfig.getString("intersitital_status_fail_backlink"));
                            All_Ads_ID.isNativeFail = Boolean.parseBoolean(mFirebaseRemoteConfig.getString("native_status_fail_backlink"));


                            All_Ads_ID.Custom_Banner = mFirebaseRemoteConfig.getString("custom_banner_id");
                            All_Ads_ID.Custom_Big_Native = mFirebaseRemoteConfig.getString("custom_big_native_id");
                            All_Ads_ID.Custom_Small_Native = mFirebaseRemoteConfig.getString("custom_small_native_id");
                            All_Ads_ID.Custom_FullAds = mFirebaseRemoteConfig.getString("custom_interstitial_id");

                            All_Ads_ID.intersttital_middle_clickcounter = Integer.parseInt(mFirebaseRemoteConfig.getString("intersttital_middle_clickcounter"));
                            All_Ads_ID.intersttital_middle_clickcounter1 = Integer.parseInt(mFirebaseRemoteConfig.getString("intersttital_middle_clickcounter"));

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    if (configListner != null) {
                        configListner.onLoad();
                    }
                }
            });
        } else {
            printAdsLog("loadConfig onFail ", "No Internet!");
            if (configListner != null) {
                configListner.onFail();
            }
        }
    }

}
