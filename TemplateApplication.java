package com.app.myapp.p596484;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;

public class TemplateApplication extends Application {

    private static final String TAG = "TemplateApp";

    @Override
    public void onCreate() {
        LocaleHelper.forceEnglish(this);
        super.onCreate();
        WebViewWarmup.warm(this);
        ExportAssets.preload(this);
        try {
            MobileAdsGate.ensureInit(this);
            AppConfig config = AppConfig.load(this);
            if (config != null && config.adsEnabled) {
                Log.i(TAG, "Preload ads testMode=" + config.useTestAds
                        + " banner=" + config.admobBannerId);
                MobileAdsGate.runWhenReady(this,
                        () -> AdPreloader.warm(TemplateApplication.this, config));
            }
        } catch (Throwable t) {
            Log.w(TAG, "Ads init skipped", t);
        }
    }
}
