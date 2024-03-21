package com.example.demo_for_ads.gfc_adshelper.ad.banner;



import static com.example.demo_for_ads.gfc_adshelper.Ads_Helper.isNetworkAvailable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;


import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;

import java.util.Random;

public class Custom_Link_Banner_Helper {
    public static void showCustomLinkBannerAd(Context context, FrameLayout mFlBanner) {
        if (isNetworkAvailable(context)) {

            ViewGroup.LayoutParams params = mFlBanner.getLayoutParams();
            //params.width = 150;
            params.height = 150;
            mFlBanner.setLayoutParams(params);

            int[] images = {R.drawable.img_qureka_banner1, R.drawable.img_qureka_banner2, R.drawable.img_qureka_banner3, R.drawable.img_qureka_banner4, R.drawable.img_qureka_banner5};
//            int[] images = {R.drawable.img_banner,R.drawable.img_banner,R.drawable.img_banner,R.drawable.img_banner,R.drawable.img_banner};
            Random rand = new Random();
            mFlBanner.setBackgroundResource(images[rand.nextInt(images.length)]);
            Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
            mFlBanner.startAnimation(myAnim);

            mFlBanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFlBanner.setBackgroundResource(images[rand.nextInt(images.length)]);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(All_Ads_ID.mLinkads));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (null != intent.resolveActivity(context.getPackageManager())) {
                        context.startActivity(intent);
                    }
                    AppOpenManager.isFromApp = true;
                }
            });


        } else hideParent(mFlBanner);
    }


    private static void hideParent(FrameLayout mFlBanner) {
        if (mFlBanner != null)
            mFlBanner.setVisibility(View.GONE);
    }
}
