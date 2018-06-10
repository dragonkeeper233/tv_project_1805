package com.wytv.cc.mytvapp.Object;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ScreenReportObject {

    //    地址：/screen/report
//    方式：get
//    参数：
//    type=day|week|month
//    Day:日报
//    Week：周报
//    Month：月报
//    返回值：
//    {
//        field:需要显示的字段列表
//        field_text：字段名
//        List:[
//        {
//            status:数据条状态
////            percent ：蓝色长度
////            code|base|backup...(需要遍历field，将field存在的都显示到页面)：{
////            add	：新增
////            update	更新
////            delete	删除
//        }
//
//        }
//]
//    }
    private ArrayList<String> field;
    private HashMap<String, String> field_text;
    private ArrayList<ReportByDate> reportByDates;
    public static final String TYPE_DAY = "day";
    public static final String TYPE_WEEK = "week";
    public static final String TYPE_MONTH = "month";

    public ArrayList<String> getField() {
        return field;
    }

    public void setField(ArrayList<String> field) {
        this.field = field;
    }

    public HashMap<String, String> getField_text() {
        return field_text;
    }

    public void setField_text(HashMap<String, String> field_text) {
        this.field_text = field_text;
    }

    public ArrayList<ReportByDate> getReportByDates() {
        return reportByDates;
    }

    public void setReportByDates(ArrayList<ReportByDate> reportByDates) {
        this.reportByDates = reportByDates;
    }
    public static class ReportByDate {
        private String date;
        private HashMap<String, ReportItem> reportItem;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public HashMap<String, ReportItem> getReportItem() {
            return reportItem;
        }

        public void setReportItem(HashMap<String, ReportItem> reportItem) {
            this.reportItem = reportItem;
        }
    }


    //          code|base|backup...(需要遍历field，将field存在的都显示到页面)：{
    public class ReportItem {
        private int status;//数据条状态
        private double percent;//蓝色长度
        private int add;//新增
        private int update;//更新
        private int delete;//删除

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getPercent() {
            return percent;
        }

        public void setPercent(double percent) {
            this.percent = percent;
        }

        public int getAdd() {
            return add;
        }

        public void setAdd(int add) {
            this.add = add;
        }

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public int getDelete() {
            return delete;
        }

        public void setDelete(int delete) {
            this.delete = delete;
        }
    }

    public static ScreenReportObject getObj(String reson) {
        if (TextUtils.isEmpty(reson))
            return null;
        Gson gson = new Gson();
        ScreenReportObject screenReportObject = gson.fromJson(reson, ScreenReportObject.class);
        try {
            JSONObject jsonObject = new JSONObject(reson);
            if (jsonObject == null)
                return screenReportObject;
            JSONObject listObj = jsonObject.getJSONObject("list");
            if (listObj == null)
                return screenReportObject;
            ArrayList<ReportByDate> reportByDates= new ArrayList<ReportByDate> ();
            Iterator it = listObj.keys();
            String key = null;//键
            JSONObject vol = null;//值
            while (it.hasNext()) {//遍历
                key = (String) it.next().toString();
                vol = listObj.getJSONObject(key);
                if (vol != null) {
                    JSONObject eventsObj = vol.getJSONObject("events");
                    if (eventsObj != null) {
                        Type type = new TypeToken<HashMap<String, ReportItem>>() {
                        }.getType();
                        HashMap<String, ReportItem> map = gson.fromJson(eventsObj.toString(), type);
                        ReportByDate reportByDate = new ReportByDate();
                        reportByDate.date = key;
                        reportByDate.reportItem = map;
                        reportByDates.add(reportByDate);
                    }
                }
            }
            screenReportObject.setReportByDates(reportByDates);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return screenReportObject;
    }

}
