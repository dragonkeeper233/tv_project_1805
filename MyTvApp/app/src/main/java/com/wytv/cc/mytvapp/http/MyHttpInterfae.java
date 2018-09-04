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

    public static void getScreenDanger(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.SCREEN_DANGER, "", callback);
    }

    public static void getScreenReport(MyHttp.MyHttpCallback callback, String type) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.SCREEN_REPORT, "&type=" + type, callback);
    }

    public static void getNewsBase(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.NEWS_BASE, "", callback);
    }

    // limit:数量，默认10
    public static void getNewsRecent(MyHttp.MyHttpCallback callback, int limit) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.NEWS_RECENT, "&limit=" + limit, callback);
    }

    // limit:数量，默认50
    public static void getNewsLists(MyHttp.MyHttpCallback callback, int limit) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.NEWS_LISTS, "&limit=" + limit, callback);
    }


    public static void getPhotoBase(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.PHOTO_BASE, "", callback);
    }

    // page:页码，默认1
    //pageSize：每页显示数量，默认50
    public static void getPhotoLists(MyHttp.MyHttpCallback callback, int page, int pageSize) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.PHOTO_LISTS, "&page=" + page + "&pageSize=" + pageSize, callback);
    }


    public static void getVideoBase(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.VIDEO_BASE, "", callback);
    }

    // page:页码，默认1
    //pageSize：每页显示数量，默认50
    public static void getVideoLists(MyHttp.MyHttpCallback callback, int page, int pageSize) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.VIDEO_LISTS, "&page=" + page + "&pageSize=" + pageSize, callback);
    }

    public static void getDialogFile(MyHttp.MyHttpCallback callback, String id) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.DIALOG_FILE, "&id=" + id, callback);
    }

    public static void getDialogReport(MyHttp.MyHttpCallback callback, String id, String field) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.DIALOG_REPORT, "&id=" + id + "&field=" + field, callback);
    }

    public static void getDialogDatabase(MyHttp.MyHttpCallback callback, String creatTime) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.DIALOG_DATABASE, "&create_time=" + creatTime, callback);
    }

    public static void getDialogFileMore(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.DIALOG_FILES, "", callback);
    }

    public static void getDialogDatabaseMore(MyHttp.MyHttpCallback callback) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.DIALOG_DATABASES, "", callback);
    }

    public static void getDialogReportMore(MyHttp.MyHttpCallback callback, String field) {
        MyHttp.get(UrlUtils.BASE_URL + UrlUtils.DIALOG_REPORTS, "&field=" + field, callback);
    }
}
