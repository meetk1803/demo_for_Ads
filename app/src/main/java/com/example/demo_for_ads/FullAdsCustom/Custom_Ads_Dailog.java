package com.example.demo_for_ads.FullAdsCustom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.demo_for_ads.R;
import com.example.demo_for_ads.gfc_adshelper.All_Ads_ID;


public class Custom_Ads_Dailog extends DialogFragment {

    ImageView mClose;
    WebView simpleWebView;
    OnClickListener m_clickListener;
    Dialog dialog;

    public interface OnClickListener {
        void delete();
    }

    public Custom_Ads_Dailog(OnClickListener clickListener) {
        this.m_clickListener = clickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // dialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.daliog_custom_ads);

        try {

            simpleWebView = dialog.findViewById(R.id.simpleWebView);
            mClose = dialog.findViewById(R.id.mClose);

            simpleWebView.loadUrl(All_Ads_ID.mLinkads);
            simpleWebView.getSettings().setJavaScriptEnabled(true);
            simpleWebView.setWebViewClient(new WebViewClient());

            mClose.setEnabled(false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("m_clickListener", "--> Ok");
                    mClose.setEnabled(true);
                }
            }, 2000);

            mClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    m_clickListener.delete();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dialog = null;
    }

}
