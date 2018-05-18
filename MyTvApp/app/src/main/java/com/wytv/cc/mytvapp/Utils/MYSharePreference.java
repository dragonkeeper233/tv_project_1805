package com.wytv.cc.mytvapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wytv.cc.mytvapp.MyApp;



public class MYSharePreference  {

    private volatile static MYSharePreference mySharePreference;

    private MYSharePreference() {
    }

    public void init(Context mContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static MYSharePreference getInstance() {
        if (mySharePreference == null) {
            synchronized (MYSharePreference.class) {
                if (mySharePreference == null) {
                    mySharePreference = new MYSharePreference();
                    mySharePreference.init(MyApp.getInstance());
                }
            }
        }
        return mySharePreference;
    }

    /**
     * ############################################系统配置的sp start####################################################
     */
    private SharedPreferences preferences;//User相关数据保存


    private static final String BASE_URL_SP  ="BASE_URL_SP";
    public void setBaseUrl(String BaseUrl) {
        preferences.edit().putString(BASE_URL_SP, BaseUrl).commit();
    }

    public String getBaseUrl() {
        return preferences.getString(BASE_URL_SP, "");
    }

    private static final String TOKEN_SP  ="BASE_URL_SP";
    public void setToken(String BaseUrl) {
        preferences.edit().putString(TOKEN_SP, BaseUrl).commit();
    }

    public String getToken() {
        return preferences.getString(TOKEN_SP, "");
    }

}
