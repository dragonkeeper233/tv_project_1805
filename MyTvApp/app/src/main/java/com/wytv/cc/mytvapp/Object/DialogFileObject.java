package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogFileObject {
//
//    {
//        "data": {
//        "field": {
//            "code": "生成"
//        },
//        "content": {
//            "code": {
//                "width": [30, 70],
//                "data": [
//					["更新", 1]
//				]
//            }
//        },
//        "title": "文件变动"
//    },
//        "status": 1,
//            "msg": "获取成功"
//    }

    private HashMap<String, String> field;
    private String title;
    private HashMap<String, Item> content;

    public class Item {
        float[] width;
        ArrayList<ArrayList<String>> data;

        public float[] getWidth() {
            return width;
        }

        public void setWidth(float[] width) {
            this.width = width;
        }

        public ArrayList<ArrayList<String>> getData() {
            return data;
        }

        public void setData(ArrayList<ArrayList<String>> data) {
            this.data = data;
        }
    }

    public HashMap<String, String> getField() {
        return field;
    }

    public void setField(HashMap<String, String> field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HashMap<String, Item> getContent() {
        return content;
    }

    public void setContent(HashMap<String, Item> content) {
        this.content = content;
    }

    public static DialogFileObject getObj(String js) {
        Gson gson = new Gson();
        DialogFileObject dialogFileObject = gson.fromJson(js, DialogFileObject.class);
        return dialogFileObject;
    }

}
