package com.wytv.cc.mytvapp.Object;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DengerObject {
    //    		"rows": [{
//        "id": 2687,
//                "server_id": 1,
//                "code": {
//            "add": 0,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "生成"
//        },
//        "base": {
//            "add": 0,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "环境"
//        },
//        "backup": {
//            "add": 1,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "备份"
//        },
//        "resources": {
//            "add": 0,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "资源"
//        },
//        "page": [],
//        "log": {
//            "add": 1,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "日志"
//        },
//        "create_time": "2018-05-21 01:30:26",
//                "is_error": 0,
//                "timeago": "17小时前"
//    }, {
//        "id": 2680,
//                "server_id": 1,
//                "code": {
//            "add": 0,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "生成"
//        },
//        "base": {
//            "add": 0,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "环境"
//        },
//        "backup": {
//            "add": 1,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "备份"
//        },
//        "resources": {
//            "add": 0,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "资源"
//        },
//        "page": [],
//        "log": {
//            "add": 1,
//                    "update": 0,
//                    "delete": 0,
//                    "name": "日志"
//        },
//        "create_time": "2018-05-20 01:30:27",
//                "is_error": 0,
//                "timeago": "2天前"
//    },
    private String id;
    private String server_id;
    private ArrayList<DangerData> data;
    private String create_time;
    private int is_error;
    private String timeago;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer_id() {
        return server_id;
    }

    public void setServer_id(String server_id) {
        this.server_id = server_id;
    }

    public ArrayList<DangerData> getData() {
        return data;
    }

    public void setData(ArrayList<DangerData> data) {
        this.data = data;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_error() {
        return is_error;
    }

    public void setIs_error(int is_error) {
        this.is_error = is_error;
    }

    public String getTimeago() {
        return timeago;
    }

    public void setTimeago(String timeago) {
        this.timeago = timeago;
    }

    public class DangerData {
        private String type;
        private String name;
        private int add;
        private int update;
        private int delete;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAdd() {
            return add;
        }

        public void setAdd(int add) {
            this.add = add;
        }

        public int getUpdate() {
            return update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public int getDelete() {
            return delete;
        }

        public void setDelete(int delete) {
            this.delete = delete;
        }
    }

    public static ArrayList<DengerObject> getObj(String reson) {
        try {
            JSONObject jsonObject = new JSONObject(reson);
            if (jsonObject == null)
                return null;
            JSONArray objList = jsonObject.optJSONArray("rows");
            if (objList == null || objList.length() == 0)
                return null;
            ArrayList<DengerObject> dengerObjects = new ArrayList<DengerObject>();
            for (int i = 0; i < objList.length(); i++) {
                JSONObject dengerJsonObj = objList.getJSONObject(i);
                if (dengerJsonObj == null)
                    continue;
                DengerObject dengerObject = new DengerObject();
                dengerObject.setId(dengerJsonObj.getString("id"));
                dengerObject.setServer_id(dengerJsonObj.getString("server_id"));
                dengerObject.setTimeago(dengerJsonObj.getString("timeago"));
                dengerObject.setCreate_time(dengerJsonObj.getString("create_time"));
                dengerObject.setIs_error(dengerJsonObj.optInt("is_error"));
                Iterator it = dengerJsonObj.keys();
                String vol = null;//值
                String key = null;//键
                ArrayList<DengerObject.DangerData> dangerDatas = new ArrayList<DangerData>();
                while (it.hasNext()) {//遍历JSONObject
                    key = (String) it.next().toString();
                    vol = dengerJsonObj.optString(key);
                    if (dengerJsonObj.optJSONObject(key) != null) {
                        Gson gson = new Gson();
                        DangerData dangerData = gson.fromJson(vol, DangerData.class);
                        dangerData.setType(key);
                        dangerDatas.add(dangerData);
                    }
                }
                dengerObject.setData(dangerDatas);
                dengerObjects.add(dengerObject);
            }
            return dengerObjects;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
