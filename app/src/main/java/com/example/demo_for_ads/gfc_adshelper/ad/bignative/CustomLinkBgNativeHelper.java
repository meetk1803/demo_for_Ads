package com.example.demo_for_ads.gfc_adshelper.ad.bignative;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;

import java.util.Random;

public class CustomLinkBgNativeHelper {

    private static FrameLayout mFlBgNative;

    public static void showCustomLinkBigNativeAd(Context context, FrameLayout frameLayout) {

        mFlBgNative = frameLayout;
        Log.e("mFlSmallNative", "viewbig-->" + frameLayout);
        if (isNetworkAvailable(context)) {
            ViewGroup.LayoutParams params = mFlBgNative.getLayoutParams();
            //params.width = 150;
            params.height = 600;
            mFlBgNative.setLayoutParams(params);

            // int[] images = {R.drawable.img_qureka_1,R.drawable.img_qureka_2,R.drawable.img_qureka_3,R.drawable.img_qureka_4,R.drawable.img_qureka_5};
            int[] images = {R.drawable.big_native1, R.drawable.big_native2, R.drawable.big_native3, R.drawable.big_native4, R.drawable.big_native5};
            Random rand = new Random();
            mFlBgNative.setBackgroundResource(images[rand.nextInt(images.length)]);
            Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
            mFlBgNative.startAnimation(myAnim);

            mFlBgNative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlBgNative.setBackgroundResource(images[rand.nextInt(images.length)]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(All_Ads_ID.mLinkads));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (null != intent.resolveActivity(context.getPackageManager())) {
                        context.startActivity(intent);
                    }
                    AppOpenManager.isFromApp = true;
                }
            });

        } else hideParent(mFlBgNative);
    }


    private static void hideParent(FrameLayout mFlBgNative) {
        if (mFlBgNative != null)
            mFlBgNative.setVisibility(View.GONE);
    }
}
