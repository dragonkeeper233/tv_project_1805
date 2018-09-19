package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
    private String create_time;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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
            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
            ArrayList<DatabaseObject> databaseObjects = null;
            if (jsonObject != null) {
                databaseObjects = new ArrayList<DatabaseObject>();
                Iterator it = jsonObject.entrySet().iterator();
                Object vol = null;//值
                String key = null;//键
                while (it.hasNext()) {//遍历JSONObject
                    Map.Entry entry = (Map.Entry)it.next();
                    key = (String) entry.getKey();
                    vol = entry.getValue();
                    DatabaseObject databaseObject = new DatabaseObject();
                    databaseObject.setDate(key);
                    if (vol==null||!(vol instanceof JsonObject))
                        continue;
                    JsonObject itemObj = (JsonObject)vol;
                    try {
                        databaseObject.setTimeago(itemObj.getAsJsonPrimitive("timeago").getAsString());
                        databaseObject.setIs_warning(itemObj.getAsJsonPrimitive("is_warning").getAsInt());
                        databaseObject.create_time = itemObj.getAsJsonPrimitive("create_time").getAsString();
                    }catch (Exception e){
                    }
                    JsonObject listObj = itemObj.getAsJsonObject("data");
                    if (listObj == null)
                        continue;
                    Object itemvol = "";//值
                    String itemkey = null;//键
                    ArrayList<Data> itemList =  new ArrayList<Data>();
                    Iterator itemit = listObj.entrySet().iterator();
                    while (itemit.hasNext()) {//遍历JSONObject
                        Map.Entry chilEentry = (Map.Entry)itemit.next();
                        itemkey = (String) chilEentry.getKey();
                        itemvol = chilEentry.getValue();
                        if (!(itemvol instanceof JsonObject))
                            continue;
                        Gson gson = new Gson();
                        Data data =  gson.fromJson((JsonObject)itemvol, Data.class);
                        data.setTable(itemkey);
                        itemList.add(data);
                        }
                    databaseObject.setData(itemList);
                    databaseObjects.add(databaseObject);
                }
            }
            return databaseObjects;
    }
}
