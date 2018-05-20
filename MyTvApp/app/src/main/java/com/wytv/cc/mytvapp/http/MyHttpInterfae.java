package com.wytv.cc.mytvapp.http;

public class MyHttpInterfae {

    public static void getScreenBase(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.SCREEN_BASE, "", callback);
    }

    public static void getScreenServer(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.SCREEN_SERVER, "", callback);
    }

    public static void getScreenMoitor(MyHttp.MyHttpCallback callback, int time) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.SCREEN_MONITOR, "&monitor_time=" + time, callback);
    }

    public static void getScreenDatabase(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.SCREEN_DATABSE, "", callback);
    }
}
