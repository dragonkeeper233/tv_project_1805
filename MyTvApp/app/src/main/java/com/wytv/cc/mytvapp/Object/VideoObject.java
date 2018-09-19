package com.wytv.cc.mytvapp.Object;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VideoObject {
//    url 地址
//    date	时间

    private String url;
    private String date;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static ArrayList<VideoObject> getList(String jsStr) {
            JsonObject jsonObject = new JsonParser().parse(jsStr).getAsJsonObject();
            if (jsonObject != null) {
                JsonElement resultStr = jsonObject.get("data");
                String image = jsonObject.get("image")==null?"":jsonObject.getAsJsonPrimitive("image").getAsString();
                Type type = new TypeToken<ArrayList<VideoObject>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<VideoObject> result = gson.fromJson(resultStr, type);
                if (result != null && result.size() > 0 && !TextUtils.isEmpty(image)) {
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i) != null)
                            result.get(i).setImage(image);
                    }
                }
                return result;
            }
        return null;
    }
}
