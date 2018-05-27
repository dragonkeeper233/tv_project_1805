package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

public class PhotoBaseObject {

    //    image_num	全部图片
//    today	当天
//    yesterday	昨天
//    week	本周
//    month	本月
//    time	最后查询时间
    private int image_num;//	全部图片
    private int today;//		当天
    private int yesterday;//		昨天
    private int week;//		本周
    private int month;//		本月
    private String time;//		最后查询时间

    public int getImage_num() {
        return image_num;
    }

    public void setImage_num(int image_num) {
        this.image_num = image_num;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getYesterday() {
        return yesterday;
    }

    public void setYesterday(int yesterday) {
        this.yesterday = yesterday;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static PhotoBaseObject getObj(String jsStr) {
        Gson gson = new Gson();
        PhotoBaseObject result = gson.fromJson(jsStr, PhotoBaseObject.class);
        return result;
    }
}
