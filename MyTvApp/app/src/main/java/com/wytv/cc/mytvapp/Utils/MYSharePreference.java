package com.wytv.cc.mytvapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wytv.cc.mytvapp.MyApp;


public class MYSharePreference {

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


    private static final String BASE_URL_SP = "BASE_URL_SP";

    public void setBaseUrl(String BaseUrl) {
        preferences.edit().putString(BASE_URL_SP, BaseUrl).commit();
    }

    public String getBaseUrl() {
        return preferences.getString(BASE_URL_SP, "");
    }

    private static final String TOKEN_SP = "TOKEN_SP";

    public void setToken(String BaseUrl) {
        preferences.edit().putString(TOKEN_SP, BaseUrl).commit();
    }

    public String getToken() {
        return preferences.getString(TOKEN_SP, "");
    }

    private static final String HOME_TIME_SP = "HOME_TIME_SP";

    public void setHomeTimeSp(long time) {
        preferences.edit().putLong(HOME_TIME_SP, time).commit();
    }

    public long getHomeTimeSp() {
        return preferences.getLong(HOME_TIME_SP, 60000);
    }

    private static final String PHOTO_TIME_SP = "PHOTO_TIME_SP";

    public void setPhotoTimeSp(long time) {
        preferences.edit().putLong(PHOTO_TIME_SP, time).commit();
    }

    public long getPhotoTimeSp() {
        return preferences.getLong(PHOTO_TIME_SP, (long) 60000 * 60);
    }

    private static final String NEWS_TIME_SP = "NEWS_TIME_SP";

    public void setNewsTimeSp(long time) {
        preferences.edit().putLong(NEWS_TIME_SP, time).commit();
    }

    public long getNewsTimeSp() {
        return preferences.getLong(NEWS_TIME_SP, (long) 60000 * 60);
    }

    private static final String VIDEO_TIME_SP = "VIDEO_TIME_SP";

    public void setVideoTimeSp(long time) {
        preferences.edit().putLong(VIDEO_TIME_SP, time).commit();
    }

    public long getVideoTimeSp() {
        return preferences.getLong(VIDEO_TIME_SP, (long) 60000 * 60 * 3);
    }

}
