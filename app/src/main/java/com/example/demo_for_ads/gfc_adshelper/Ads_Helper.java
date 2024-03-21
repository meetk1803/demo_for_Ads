package com.example.demo_for_ads.gfc_adshelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;

import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.ad.open.AppOpenManager;
import com.facebook.ads.BuildConfig;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Ads_Helper {

    public static String ads_status = "on"; //on - show ads; off - dont show ads
    public static String TEST_TAG = "find_ ";

    static String TAG = "AdsHelper ";
    public static final String TYPE_C = "C"; /*IntrestritialAd*/
    public static final String TYPE_c = "c"; /*IntrestritialAd*/

    public static final String TYPE_O = "O"; /*OpenAd*/
    public static final String TYPE_o = "o"; /*OpenAd*/

    public static final String TYPE_a = "a"; /*OpenAd*/

    public static AdRequest getOpenAdsRequest(String adNetworkType) {

        setTestRequest();
        if (adNetworkType.equalsIgnoreCase("admob")) {
            return new AdRequest.Builder().build();
        } else {
            return new AdManagerAdRequest.Builder().build();
        }



    /*    return new AdRequest.Builder()
                .addTestDevice("67D581248CB9DCA9E8B661C4ACA83BEC")
                .addTestDevice("1AABAA43DB2E466CF16860E95CE912F3")
                .addTestDevice("62F4B621D8D4DD4E1712E667AC1DA5B5")
                .build();  */
    }

    private static void setTestRequest() {
//        if (BuildConfig.DEBUG) {
        RequestConfiguration requestConfiguration = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList("434976599E4E922C524D51829905E90C"))
                .setTestDeviceIds(Arrays.asList("6A88FC423C71B0EA055F5975435506E3"))
                .setTestDeviceIds(Arrays.asList("58B5130039D2D4C61AF4950A7C9C4C01"))
                .setTestDeviceIds(Arrays.asList("0EB23E373D77BE85FE6F3DB361BC34A6"))
                .setTestDeviceIds(Arrays.asList("CEDE2D3845C3E6FC97693F71907C7F80"))
                .build();
        MobileAds.setRequestConfiguration(requestConfiguration);
//        }
    }

    public static AdRequest getAdsRequest() {

        setTestRequest();
        return new AdRequest.Builder().build();
    }

    public static AdManagerAdRequest getAdxAdsRequest() {

        setTestRequest();

        return new AdManagerAdRequest.Builder().build();
    }

    public static void printAdsLog(String key, String val) {
        if (BuildConfig.DEBUG)
            Log.e(TAG + key + "*===", "===*" + val);
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEmptyStr(String Val) {
        if (Val == null || Val.trim().isEmpty() || Val.trim().equalsIgnoreCase("null"))
            return true;
        else
            return false;
    }

    public static boolean isShowAds() {
        if (ads_status.trim().equalsIgnoreCase("on"))
            return true;
        else
            return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean isNetworkAvail = false;
        if (context == null) return isNetworkAvail;

        try {
            ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            isNetworkAvail = connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

            if (!isNetworkAvail) {
                printAdsLog("isNetworkAvailable", "No Internet!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNetworkAvail;
    }

    static AlertDialog loadDialog;

    public static void loadingDialog(Context context) {
        try {
            if (context == null)
                return;

            Ads_Helper.printAdsLog("loadingDialog ", "loadDialog " + loadDialog);

            if (loadDialog != null)
                Ads_Helper.printAdsLog("loadingDialog ", "isShowing " + loadDialog.isShowing());

            if (loadDialog != null && loadDialog.isShowing())
                return;

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.progressbar, null);

            TextView mTvLoading = (TextView) dialogView.findViewById(R.id.mTvLoading);
            if (mTvLoading != null)
                mTvLoading.setVisibility(View.VISIBLE);

            dialogBuilder.setView(dialogView);

            if (loadDialog == null) {
                loadDialog = dialogBuilder.create();
            }

            loadDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadDialog.setCancelable(false);

            WindowManager.LayoutParams lp = loadDialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.CENTER;
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lp.dimAmount = 0.5f;
            loadDialog.getWindow().setAttributes(lp);

            if (!loadDialog.isShowing())
                loadDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissLoading() {
        try {
            if (loadDialog != null) {
                if (loadDialog.isShowing()) {
                    loadDialog.dismiss();
                }
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int addRemoveThis = 9;

    public static String filterThis(String mStr) {
        return removeThis(mStr);
    }

    private static String removeThis(String adID) {
        String mAdID = adID.replace("[", "").replace("]", "");
        String[] parts = mAdID.trim().split(", ");

        String mResult = "";
        for (int i = 0; i < parts.length; i++) {
            mResult += toDrawing(Integer.parseInt(parts[i].trim()) - addRemoveThis);
        }

        return mResult;
    }

    public static String toDrawing(int input) {
        return Character.toString((char) input);
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static String pickRandom(String str1, String str2) {
//        return ""; // test show more ads

        if (!isEmptyStr(str1) && !isEmptyStr(str2)) {
            List<String> myList = Arrays.asList(str1, str2);
            Random random = new Random();
            int randomIndex = random.nextInt(myList.size());
            printAdsLog("pickRandom", "randomIndex: " + randomIndex);
            return myList.get(randomIndex);
        } else {

            if (isEmptyStr(str1))
                if (isEmptyStr(str2))
                    return "";
                else return str2;
            else return str1;
        }
    }


    public static int dpToPx(Context mContext, int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//        return Math.round(dp * (DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context mContext, int px) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//        return Math.round(px / ( DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void openCustomTab(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        try {
            String packageName = "com.android.chrome";
            if (packageName != null) {

                // we are checking if the package name is not null
                // if package name is not null then we are calling
                // that custom chrome tab with intent by passing its
                // package name.
                customTabsIntent.intent.setPackage(packageName);

                // in that custom tab intent we are passing
                // our url which we have to browse.
                customTabsIntent.launchUrl(activity, uri);
                AppOpenManager.isFromApp = true;
            } else {
                // if the custom tabs fails to load then we are simply
                // redirecting our user to users device default browser.
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                AppOpenManager.isFromApp = true;
            }
        } catch (Exception e) {
        }
    }
}
