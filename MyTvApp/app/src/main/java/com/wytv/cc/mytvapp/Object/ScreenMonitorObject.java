package com.wytv.cc.mytvapp.Object;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ScreenMonitorObject {
    //    “date：时间”:{
//        database_danger_count	：数据库告警
//        database_insert_count	：数据库新增
//        resources_add_count：资源新增
//        danger_count：文件告警
    private String date;//时间
    private float database_danger_count;//数据库告警
    private float database_insert_count;//数据库新增
    private float resources_add_count;//资源新增
    private float danger_count;//文件告警

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getDatabase_danger_count() {
        return database_danger_count;
    }

    public void setDatabase_danger_count(float database_danger_count) {
        this.database_danger_count = database_danger_count;
    }

    public float getDatabase_insert_count() {
        return database_insert_count;
    }

    public void setDatabase_insert_count(float database_insert_count) {
        this.database_insert_count = database_insert_count;
    }

    public float getResources_add_count() {
        return resources_add_count;
    }

    public void setResources_add_count(float resources_add_count) {
        this.resources_add_count = resources_add_count;
    }

    public float getDanger_count() {
        return danger_count;
    }

    public void setDanger_count(float danger_count) {
        this.danger_count = danger_count;
    }

    public static ArrayList<ScreenMonitorObject> getObj (String body)  {
        try {
            JSONObject jsonObject = new JSONObject(body);
            ArrayList<ScreenMonitorObject> screenMonitorObjects = null;
            if (jsonObject != null) {
                screenMonitorObjects = new ArrayList<ScreenMonitorObject>();
                Iterator it = jsonObject.keys();
                String vol = "";//值
                String key = null;//键
                while (it.hasNext()) {//遍历JSONObject
                    key = (String) it.next().toString();
                    vol = jsonObject.getString(key);
                    Gson gson = new Gson();
                    ScreenMonitorObject result = gson.fromJson(vol, ScreenMonitorObject.class);
                    result.setDate(key);
                    screenMonitorObjects.add(result);
                }
            }
            return screenMonitorObjects;
        }catch (JSONException e){
            e.printStackTrace();
        }

        return null;
    }
}
