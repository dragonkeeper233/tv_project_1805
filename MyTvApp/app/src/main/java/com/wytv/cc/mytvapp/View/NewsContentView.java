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
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.wytv.cc.mytvapp.Object.NewsContentObject;
import com.wytv.cc.mytvapp.Object.PhotoObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;
import java.util.List;

public class NewsContentView extends BaseView implements IBaseView {
    private RecyclerViewTV leftRv, rightRv;


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
        leftRv = (RecyclerViewTV) findViewById(R.id.news_left_rv);
        LinearLayoutManager leftLm = new LinearLayoutManager(context);
        leftRv.setLayoutManager(leftLm);
        leftRv.setSelectedItemAtCentered(true);
        leftRv.setOnItemListener(leftOnItemListener);
        rightRv = (RecyclerViewTV) findViewById(R.id.news_right_rv);
        LinearLayoutManager rightLm = new LinearLayoutManager(context);
        rightRv.setLayoutManager(rightLm);
        rightRv.setSelectedItemAtCentered(true);
        rightRv.setOnItemListener(rightOnItemListener);
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

    NewsItemAdapter leftAdapter, rightAdapter;

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        NewsContentObject newsContentObject = (NewsContentObject) obj;
        if (newsContentObject != null && newsContentObject.getData() != null && newsContentObject.getData().size() != 0) {
            if (newsContentObject.getData().size() > newsContentObject.getLeft()) {
                final List<NewsContentObject.NewsObject> leftList = newsContentObject.getData().subList(0, newsContentObject.getLeft());
                final List<NewsContentObject.NewsObject> rightList = newsContentObject.getData().subList
                        (newsContentObject.getLeft(), newsContentObject.getData().size());
                leftAdapter = new NewsItemAdapter(leftList, newsContentObject.getDates(), activity,leftRv);
                leftRv.setAdapter(leftAdapter);
                rightAdapter = new NewsItemAdapter(rightList, newsContentObject.getDates(), activity,rightRv);
                rightRv.setAdapter(rightAdapter);
            }

        }
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    RecyclerViewTV.OnItemListener leftOnItemListener = new RecyclerViewTV.OnItemListener() {
        @Override
        public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {

        }

        @Override
        public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
            if (leftAdapter != null) {
                leftAdapter.setCurrentShow(position);
            }
        }

        @Override
        public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
            if (leftAdapter != null) {
                leftAdapter.setCurrentShow(position);
            }
        }
    };

    RecyclerViewTV.OnItemListener rightOnItemListener = new RecyclerViewTV.OnItemListener() {
        @Override
        public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {

        }

        @Override
        public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
            if (rightAdapter != null) {
                rightAdapter.setCurrentShow(position);
            }
        }

        @Override
        public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
            if (rightAdapter != null) {
                rightAdapter.setCurrentShow(position);
            }

        }
    };


}
