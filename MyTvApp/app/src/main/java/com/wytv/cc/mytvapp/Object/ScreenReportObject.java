package com.wytv.cc.mytvapp.Object;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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
    private LinkedHashMap<String, String> field_text;
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

    public LinkedHashMap<String, String> getField_text() {
        return field_text;
    }

    public void setField_text(LinkedHashMap<String, String> field_text) {
        this.field_text = field_text;
    }

    public ArrayList<ReportByDate> getReportByDates() {
        return reportByDates;
    }

    public void setReportByDates(ArrayList<ReportByDate> reportByDates) {
        this.reportByDates = reportByDates;
    }
    public static class ReportByDate {
        private String id;
        private String date;
        private LinkedHashMap<String, ReportItem> reportItem;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public LinkedHashMap<String, ReportItem> getReportItem() {
            return reportItem;
        }

        public void setReportItem(LinkedHashMap<String, ReportItem> reportItem) {
            this.reportItem = reportItem;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
            JsonObject jsonObject = new JsonParser().parse(reson).getAsJsonObject();
            if (jsonObject == null)
                return screenReportObject;
            JsonObject listObj = jsonObject.getAsJsonObject("list");
            if (listObj == null)
                return screenReportObject;
            ArrayList<ReportByDate> reportByDates= new ArrayList<ReportByDate> ();
            Iterator it = listObj.entrySet().iterator();
            String key = null;//键
            JsonObject vol = null;//值
            while (it.hasNext()) {//遍历
                Map.Entry entry = (Map.Entry)it.next();
                key = (String) entry.getKey();
                vol = (JsonObject)entry.getValue();
                if (vol != null) {
                    JsonObject eventsObj = vol.getAsJsonObject("events");
                    if (eventsObj != null) {
                        Type type = new TypeToken<LinkedHashMap<String, ReportItem>>() {
                        }.getType();
                        LinkedHashMap<String, ReportItem> map = gson.fromJson(eventsObj, type);
                        ReportByDate reportByDate = new ReportByDate();
                        reportByDate.date = key;
                        reportByDate.reportItem = map;
                        String id = vol.getAsJsonPrimitive("id").getAsString();
                        reportByDate.id =id;
                        reportByDates.add(reportByDate);
                    }
                }
            }
            screenReportObject.setReportByDates(reportByDates);

        return screenReportObject;
    }

}
