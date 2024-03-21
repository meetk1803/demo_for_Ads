package com.example.demo_for_ads.gfc_adshelper.ad.smallnative;



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

public class CustomLinkSmallNativeHelper {

    private static FrameLayout mFlSmallNative;

    public static void showCustomLinkSmallNativeAd(Context context, FrameLayout frameLayout) {

        mFlSmallNative = frameLayout;
        Log.e("mFlSmallNative", "viewsmall-->" + frameLayout);

        if (isNetworkAvailable(context)) {

            ViewGroup.LayoutParams params = mFlSmallNative.getLayoutParams();
            //params.width = 150;
            params.height = 450;
            mFlSmallNative.setLayoutParams(params);

            int[] images = {R.drawable.img_smallnative, R.drawable.img_smallnative, R.drawable.img_smallnative, R.drawable.img_smallnative, R.drawable.img_smallnative};
            Random rand = new Random();
            mFlSmallNative.setBackgroundResource(images[rand.nextInt(images.length)]);
            Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
            mFlSmallNative.startAnimation(myAnim);

            mFlSmallNative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlSmallNative.setBackgroundResource(images[rand.nextInt(images.length)]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(All_Ads_ID.mLinkads));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (null != intent.resolveActivity(context.getPackageManager())) {
                        context.startActivity(intent);
                    }
                    AppOpenManager.isFromApp = true;
                }
            });
        } else hideParent(mFlSmallNative);
    }


    private static void hideParent(FrameLayout mFlSmallNative) {
        if (mFlSmallNative != null)
            mFlSmallNative.setVisibility(View.GONE);
    }
}
