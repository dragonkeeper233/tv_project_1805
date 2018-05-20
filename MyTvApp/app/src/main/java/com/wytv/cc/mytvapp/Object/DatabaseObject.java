package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseObject {
    //    “date：时间”:{
//        Timeago:多久前,
//                is_warning：是否显示红色告警
//        data:[
//        表名：{
//            table_name：表中文名
//            data_table：表名
//            Insert：添加数量
//            Update:更新数量
//            Delete:删除数量
//        },
//...更多
//]
//    },
//            ...更多
    private String date;
    private String timeago;
    private int is_warning;
    private ArrayList<Data> data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeago() {
        return timeago;
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }

    public int getIs_warning() {
        return is_warning;
    }

    public void setIs_warning(int is_warning) {
        this.is_warning = is_warning;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public class Data {
        private String table;//表名
        private String table_name;//表中文名
        private String data_table;//表名
        private String insert;//添加数量
        private String update;//更新数量
        private String delete;//删除数量

        public String getTable_name() {
            return table_name;
        }

        public void setTable_name(String table_name) {
            this.table_name = table_name;
        }

        public String getData_table() {
            return data_table;
        }

        public void setData_table(String data_table) {
            this.data_table = data_table;
        }

        public String getInsert() {
            return insert;
        }

        public void setInsert(String insert) {
            this.insert = insert;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getDelete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }
    }

    public static ArrayList<DatabaseObject> getObj(String body){
        try {
            JSONObject jsonObject = new JSONObject(body);
            ArrayList<DatabaseObject> databaseObjects = null;
            if (jsonObject != null) {
                databaseObjects = new ArrayList<DatabaseObject>();
                Iterator it = jsonObject.keys();
                String vol = "";//值
                String key = null;//键
                while (it.hasNext()) {//遍历JSONObject
                    key = (String) it.next().toString();
                    vol = jsonObject.getString(key);
                    DatabaseObject databaseObject = new DatabaseObject();
                    databaseObject.setDate(key);
                    JSONObject itemObj = new JSONObject(vol);
                    if (itemObj==null)
                        continue;
                    databaseObject.setTimeago(itemObj.getString("timeago"));
                    databaseObject.setIs_warning(itemObj.optInt("is_warning"));
                    JSONObject listObj = itemObj.getJSONObject("data");
                    if (listObj==null)
                        continue;
                    String itemvol = "";//值
                    String itemkey = null;//键
                    ArrayList<Data> itemList =  new ArrayList<Data>();
                    Iterator itemit = listObj.keys();
                    while (itemit.hasNext()) {//遍历JSONObject
                        itemkey = (String) itemit.next().toString();
                        itemvol = listObj.getString(itemkey);
                        Gson gson = new Gson();
                        Data data =  gson.fromJson(itemvol, Data.class);
                        data.setTable(itemkey);
                        itemList.add(data);
                        }
                    databaseObject.setData(itemList);
                    databaseObjects.add(databaseObject);
                }
            }
            return databaseObjects;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
