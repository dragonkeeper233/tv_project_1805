package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NewsRecentObject {

//    host 域名，拼接缩略图使用
//    thumb	缩略图
//    title	标题
//    description	简介
//    create_time	时间

    private String host;// 域名，拼接缩略图使用
    private String thumb;//缩略图
    private String title;//	标题
    private String description;//	简介
    private String create_time;//	时间

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

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

    public static ArrayList<NewsRecentObject> getList(String str) {
        Type type = new TypeToken<ArrayList<NewsRecentObject>>() {
        }.getType();
        ArrayList<NewsRecentObject> result = new Gson().fromJson(str, type);
        return result;
    }
}
