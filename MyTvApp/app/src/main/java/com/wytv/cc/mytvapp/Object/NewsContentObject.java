package com.wytv.cc.mytvapp.Object;

import java.util.ArrayList;

public class NewsContentObject {
    //
//    left:左边显示数量，剩余的显示到右边
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
    private ArrayList<NewsDate> date;
    private int left;
    private ArrayList<NewsObject> data;

    public ArrayList<NewsDate> getDate() {
        return date;
    }

    public void setDate(ArrayList<NewsDate> date) {
        this.date = date;
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
        private int date;//日期
        private int total;//全部
        private int update;//	更新
        private int insert;//	添加
        private ArrayList<NewsObject> data;

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

        public ArrayList<NewsObject> getData() {
            return data;
        }

        public void setData(ArrayList<NewsObject> data) {
            this.data = data;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }
    }

    public class NewsObject {
        public static final String TYPE_INSERT = "insert";
        public static final String TYPE_UPDATE = "update";
        public static final String TYPE_DELETE = "delete";
        private String title;//	标题
        private String description;//	简介
        private String create_time;//	时间
        private int Index;//	序号;//
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
            return Index;
        }

        public void setIndex(int index) {
            Index = index;
        }

        public String getLog_type() {
            return log_type;
        }

        public void setLog_type(String log_type) {
            this.log_type = log_type;
        }
    }

    public static NewsContentObject getObj(String result) {

        return null;

    }

}
