package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

public class NewsBaseObject {
    //    {"data":{"total":781,"month":778,"week":151,"yesterday":31,"today":36},"status":1,"msg":"获取成功"}
//    地址：/news/base
//    方式：get
//    参数：无
//    返回值：
//    {
//        total	总数
//        month	当月
//        week	本周
//        yesterday	昨天
//        today	当天
//    }
    private int total;//	总数
    private int month;//		当月
    private int week;//	本周
    private int yesterday;//		昨天
    private int today;//	当天

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getYesterday() {
        return yesterday;
    }

    public void setYesterday(int yesterday) {
        this.yesterday = yesterday;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public static NewsBaseObject getObj(String jsStr) {
        Gson gson = new Gson();
        NewsBaseObject result = gson.fromJson(jsStr, NewsBaseObject.class);
        return result;
    }
}
