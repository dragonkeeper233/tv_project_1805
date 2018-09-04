package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;
import com.wytv.cc.mytvapp.Object.PhotoObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;

public class PhotoContentView extends BaseView implements IBaseView, RecyclerViewTV.OnItemListener {
    private ImageView leftImgv;
    private RecyclerViewTV recyclerView;
//    private MainUpView mainUpView;
//    private RecyclerViewBridge mRecyclerViewBridge;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
            .bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
            .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
            .build();


    public PhotoContentView(Context context) {
        super(context);
        init(context);
    }

    public PhotoContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_photo_content, this);
        leftImgv = (ImageView) findViewById(R.id.photo_left);
        recyclerView = (RecyclerViewTV) findViewById(R.id.photo_rv);
        GridLayoutManager mgr = new GridLayoutManager(context, 5);
        recyclerView.setLayoutManager(mgr);
        recyclerView.setSelectedItemAtCentered(true);
        recyclerView.setOnItemListener(this);
//        mainUpView = (MainUpView) findViewById(R.id.phtot_mainupview);
//        mainUpView.setEffectBridge(new RecyclerViewBridge());
//        mRecyclerViewBridge = (RecyclerViewBridge) mainUpView.getEffectBridge();
//        mRecyclerViewBridge.setUpRectResource(R.drawable.video_cover_cursor);
//        int w = CommonUtils.dip2px(getContext(), 3);
//        RectF receF = new RectF(w, w, w, w);
//        mRecyclerViewBridge.setDrawUpRectPadding(receF);
    }

    @Override
    public void refresh(final long currentTime) {

        MyHttpInterfae.getPhotoLists(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_photo_list) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ArrayList<PhotoObject> photoObjects = PhotoObject.getList(reson);
                if (photoObjects != null && photoObjects.size() > 0) {
                    sendSuccessMessage(photoObjects, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        }, 1, 50);
    }

    @Override
    public void loadData() {

    }

    private ArrayList<PhotoObject> mPhotoObjects;

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ArrayList<PhotoObject> photoObjects = (ArrayList<PhotoObject>) obj;
        if (photoObjects != null && photoObjects.size() > 0) {
            mPhotoObjects = photoObjects;
            recyclerView.setAdapter(new PhotoItemAdapter(photoObjects, activity));
            setImgae(photoObjects.get(0).getUrl());
        }
        alreadyRefresh(currentTime);
    }

    private void setImgae(String url) {
        ImageLoader.getInstance().loadImage(url, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                leftImgv.setImageDrawable(new ColorDrawable(Color.WHITE));
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                leftImgv.setImageDrawable(new ColorDrawable(Color.WHITE));
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                leftImgv.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                leftImgv.setImageDrawable(new ColorDrawable(Color.WHITE));
            }
        });
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
//        mRecyclerViewBridge.setUnFocusView(itemView);
        itemView.animate().scaleX(1).scaleY(1).setDuration(300).start(); // 放大焦点VIEW的动画.
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
//        mRecyclerViewBridge.setFocusView(itemView, 1.2f);
        itemView.animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).start();
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
//        mRecyclerViewBridge.setFocusView(itemView, 1.2f);
        itemView.animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).start();
        setImgae(mPhotoObjects.get(position).getUrl());
    }

}
