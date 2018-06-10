package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wytv.cc.mytvapp.Object.NewsContentObject;
import com.wytv.cc.mytvapp.Object.PhotoObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;
import java.util.List;

public class NewsContentView extends BaseView implements IBaseView {
    private RecyclerView leftRv, rightRv;


    public NewsContentView(Context context) {
        super(context);
        init(context);
    }

    public NewsContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewsContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_news_content, this);
        leftRv = (RecyclerView) findViewById(R.id.news_left_rv);
        LinearLayoutManager leftLm = new LinearLayoutManager(context);
        leftRv.setLayoutManager(leftLm);
        rightRv = (RecyclerView) findViewById(R.id.news_right_rv);
        LinearLayoutManager rightLm = new LinearLayoutManager(context);
        rightRv.setLayoutManager(rightLm);
    }

    @Override
    public void refresh(final long currentTime) {

        MyHttpInterfae.getNewsLists(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_news_list) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                NewsContentObject newsContentObject = NewsContentObject.getObj(reson);
                if (newsContentObject != null) {
                    sendSuccessMessage(newsContentObject, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        }, 50);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        NewsContentObject newsContentObject = (NewsContentObject) obj;
        if (newsContentObject != null && newsContentObject.getData() != null && newsContentObject.getData().size() != 0) {
            if (newsContentObject.getData().size() > newsContentObject.getLeft()) {
                final List<NewsContentObject.NewsObject> leftList = newsContentObject.getData().subList(0, newsContentObject.getLeft());
                final List<NewsContentObject.NewsObject> rightList = newsContentObject.getData().subList
                        (newsContentObject.getLeft(), newsContentObject.getData().size());
                leftRv.setAdapter(new NewsItemAdapter(leftList, newsContentObject.getDates(), activity));
                rightRv.setAdapter(new NewsItemAdapter(rightList, newsContentObject.getDates(), activity));
            }

        }
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }


}
