package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class PhotoObject {
//    url 地址
//    date	时间

    private String url;
    private String date;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static ArrayList<PhotoObject> getList(String jsStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsStr);
            if (jsonObject != null) {
                String resultStr = jsonObject.getString("data");
                Type type = new TypeToken<ArrayList<PhotoObject>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<PhotoObject> result = gson.fromJson(resultStr, type);
                return result;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
