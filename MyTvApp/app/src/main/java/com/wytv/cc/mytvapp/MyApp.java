package com.wytv.cc.mytvapp;

import android.app.Application;
import android.text.TextUtils;

import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.Utils.MYSharePreference;
import com.wytv.cc.mytvapp.http.UrlUtils;

public class MyApp extends Application {

    public static Application getInstance() {
        return myApp;
    }
   private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        //desiredDensity : ldpi = 0.75 (120dpi) , mdpi = 1 (160dpi), hdpi = 1.5 (240dpi), xhdpi = 2.0 (320dpi)
        myApp = this;
        initUrl();
        initToken();
    }

    private void initUrl(){
        if (TextUtils.isEmpty( MYSharePreference.getInstance().getBaseUrl()))
            MYSharePreference.getInstance().setBaseUrl(UrlUtils.INIT_BASE_URL);
        UrlUtils.BASE_URL = MYSharePreference.getInstance().getBaseUrl();
    }

    private void initToken(){
        if (TextUtils.isEmpty( MYSharePreference.getInstance().getToken()))
            MYSharePreference.getInstance().setToken(UrlUtils.INIT_TOKEN);
        UrlUtils.TOKEN = MYSharePreference.getInstance().getToken();
    }
}
