package com.wytv.cc.mytvapp.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyHttp {
    public static void get(String  url,final MyHttpCallback callback ) {
        OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
//        ?_t=1526391481&token=12dL5F9tv0qww
        Request request = new Request.Builder().get().url(url+"?_t="+System.currentTimeMillis()+"&token="+ UrlUtils.TOKEN).build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback!=null)
                    callback.onFailure(0,e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()){
                    if (callback!=null)
                        callback.onFailure(response.code(),response.body().string());
                }else{
                    if (callback!=null)
                        callback.onFailure(response.code(),response.message());
                }
            }
        });
    }

    public static void post(String  url,final MyHttpCallback callback ) {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象。
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
        formBody.add("_t",System.currentTimeMillis()+"");//传递键值对参数
        formBody.add("token",UrlUtils.TOKEN);//传递键值对参数
        Request request = new Request.Builder()//创建Request 对象。
                .url(url)
                .post(formBody.build())//传递请求体
                .build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback!=null)
                    callback.onFailure(0,e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()){
                    if (callback!=null)
                        callback.onFailure(response.code(),response.body().string());
                }else{
                    if (callback!=null)
                        callback.onFailure(response.code(),response.message());
                }
            }
        });
    }

    public interface MyHttpCallback{
        public void onFailure(int code,String reson);

        public void onSuccess();
    }



}
