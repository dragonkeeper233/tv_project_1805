package com.wytv.cc.mytvapp.http;

public class MyHttpInterfae {

    public static void getScreenBase(MyHttp.MyHttpCallback callback){
        MyHttp.get(UrlUtils.SCREEN_BASE, callback);
    }
    public static void getScreenServer(MyHttp.MyHttpCallback callback){
        MyHttp.get(UrlUtils.SCREEN_SERVER, callback);
    }
}
