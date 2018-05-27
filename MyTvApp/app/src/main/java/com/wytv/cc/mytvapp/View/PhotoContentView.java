package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.wytv.cc.mytvapp.Object.PhotoObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;

public class PhotoContentView extends BaseView implements IBaseView {
    private ImageView leftImgv;
    private RecyclerView recyclerView;
    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
            .resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
            .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
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
        recyclerView = (RecyclerView) findViewById(R.id.photo_rv);
        GridLayoutManager mgr = new GridLayoutManager(context, 5);
        recyclerView.setLayoutManager(mgr);


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

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ArrayList<PhotoObject> photoObjects = (ArrayList<PhotoObject>) obj;
        if (photoObjects != null && photoObjects.size() > 0) {
            recyclerView.setAdapter(new PhotoItemAdapter(photoObjects, activity));
            ImageLoader.getInstance().loadImage(photoObjects.get(0).getUrl(), options, new ImageLoadingListener() {
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
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }


}
