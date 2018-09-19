package com.wytv.cc.mytvapp.Object;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class NewsContentObject {
    //
//    slide_to_left:左边显示数量，剩余的显示到右边
//    date:{
//        total	全部
//        update	更新
//        insert	添加
//    }
//
//    data:[
//    {
//        title	标题
//        description	简介
//        create_time	时间
//        Index 序号
//        log_type	类型，有insert,update,delete三种update标黄色，delete标红色
//    },
//            ...
//            ]
    private LinkedHashMap<String, NewsDate> dates;
    private int left;
    private ArrayList<NewsObject> data;

    public LinkedHashMap<String, NewsDate> getDates() {
        return dates;
    }

    public void setDates(LinkedHashMap<String, NewsDate> dates) {
        this.dates = dates;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public ArrayList<NewsObject> getData() {
        return data;
    }

    public void setData(ArrayList<NewsObject> data) {
        this.data = data;
    }

    public class NewsDate {
        private int total;//全部
        private int update;//	更新
        private int insert;//	添加

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public int getInsert() {
            return insert;
        }

        public void setInsert(int insert) {
            this.insert = insert;
        }
    }

    public class NewsObject {
        public static final String TYPE_INSERT = "insert";
        public static final String TYPE_UPDATE = "update";
        public static final String TYPE_DELETE = "delete";
        private String date;
        private String title;//	标题
        private String description;//	简介
        private String create_time;//	时间
        private int index;//	序号;//
        private String log_type;//		类型，有insert,update,delete三种update标黄色，delete标红色

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getLog_type() {
            return log_type;
        }

        public void setLog_type(String log_type) {
            this.log_type = log_type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static NewsContentObject getObj(String result) {
        JsonObject rootJs = new JsonParser().parse(result).getAsJsonObject();
        if (rootJs == null)
            return null;
        NewsContentObject newsContentObject = new NewsContentObject();
        JsonObject dateJs = rootJs.getAsJsonObject("date");
        if (dateJs == null) {
            return null;
        }
        Gson gson = new Gson();
        LinkedHashMap<String, NewsDate> newsDates = new LinkedHashMap<String, NewsDate>();
        Iterator it = dateJs.entrySet().iterator();
        Object vol = "";//值
        String key = null;//键
        while (it.hasNext()) {//遍历JSONObj
            Map.Entry entry = (Map.Entry) it.next();
            key = (String) entry.getKey();
            vol = entry.getValue();
            if (!(vol instanceof JsonObject))
                continue;
            NewsDate newsDate = gson.fromJson((JsonObject) vol, NewsDate.class);
            newsDates.put(key, newsDate);
        }
        newsContentObject.setDates(newsDates);
        JsonArray myDataStr = rootJs.getAsJsonArray("data");
        if (myDataStr == null) {
            return null;
        }
        Type type = new TypeToken<ArrayList<NewsObject>>() {
        }.getType();
        ArrayList<NewsObject> newsObjects = gson.fromJson(myDataStr, type);
        newsContentObject.setData(newsObjects);
        try {
            newsContentObject.setLeft(rootJs.getAsJsonPrimitive("left").getAsInt());
        } catch (Exception e) {
        }
        return newsContentObject;

    }

}
