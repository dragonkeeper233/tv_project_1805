package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

public class ScreenBaseObject {

//    dir_num：文件夹数量
//    file_num：文件数量
//    article_num：文章数量
//    hash_num：指纹数量
//
//    {"data":{"monitor_num":7,"database_num":591,"article_num":591,
//            "dir_num":8401,"file_num":42312,"image_num":6428,"hash_num":2611,"today_hash_num":31},"status":1,"msg":"获取成功"

    private int dir_num;
    private int file_num;
    private int article_num;
    private int hash_num;
    private int database_num;
    private int image_num;

    public int getDir_num() {
        return dir_num;
    }

    public void setDir_num(int dir_num) {
        this.dir_num = dir_num;
    }

    public int getFile_num() {
        return file_num;
    }

    public void setFile_num(int file_num) {
        this.file_num = file_num;
    }

    public int getArticle_num() {
        return article_num;
    }

    public void setArticle_num(int article_num) {
        this.article_num = article_num;
    }

    public int getHash_num() {
        return hash_num;
    }

    public void setHash_num(int hash_num) {
        this.hash_num = hash_num;
    }

    public int getDatabase_num() {
        return database_num;
    }

    public void setDatabase_num(int database_num) {
        this.database_num = database_num;
    }

    public int getImage_num() {
        return image_num;
    }

    public void setImage_num(int image_num) {
        this.image_num = image_num;
    }

    public static ScreenBaseObject getObj(String jsStr){
        Gson gson = new Gson();
        ScreenBaseObject result = gson.fromJson(jsStr, ScreenBaseObject.class);
        return result;
    }
}
