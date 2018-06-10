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
    private HashMap<String, NewsDate> dates;
    private int left;
    private ArrayList<NewsObject> data;

    public HashMap<String, NewsDate> getDates() {
        return dates;
    }

    public void setDates(HashMap<String, NewsDate> dates) {
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
        try {
            JSONObject rootJs = new JSONObject(result);
            NewsContentObject newsContentObject = new NewsContentObject();
            JSONObject dateJs = rootJs.getJSONObject("date");
            if (dateJs == null) {
                return null;
            }
            Gson gson = new Gson();
            HashMap<String, NewsDate> newsDates = new HashMap<String, NewsDate>();
            Iterator it = dateJs.keys();
            String vol = "";//值
            String key = null;//键
            while (it.hasNext()) {//遍历JSONObj
                key = (String) it.next().toString();
                vol = dateJs.getString(key);
                NewsDate newsDate = gson.fromJson(vol, NewsDate.class);
                newsDates.put(key, newsDate);
            }
            newsContentObject.setDates(newsDates);
            String myDataStr = rootJs.getString("data");
            if (TextUtils.isEmpty(myDataStr)) {
                return null;
            }
            Type type = new TypeToken<ArrayList<NewsObject>>() {
            }.getType();
            ArrayList<NewsObject> newsObjects = gson.fromJson(myDataStr, type);
            newsContentObject.setData(newsObjects);
            newsContentObject.setLeft(rootJs.getInt("left"));
            return newsContentObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}
