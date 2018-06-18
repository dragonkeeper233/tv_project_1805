package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wytv.cc.mytvapp.Object.NewsContentObject;
import com.wytv.cc.mytvapp.Object.NewsRecentObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class NewsBanderView extends BaseView implements IBaseView, ViewPager.OnPageChangeListener, View.OnClickListener {
    private TextView titleTv, contentTv, timeTv;
    private Banner banner;
    private ImageButton leftBtn, rightBtn;


    public NewsBanderView(Context context) {
        super(context);
        init(context);
    }

    public NewsBanderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewsBanderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_news_banner, this);
        banner = (Banner) findViewById(R.id.banner);
        banner.getLayoutParams().width = CommonUtils.getScreenWidth(context) / 4;
        banner.getLayoutParams().height = (int) (banner.getLayoutParams().width / 1.5);
        titleTv = (TextView) findViewById(R.id.banner_text_title);
        contentTv = (TextView) findViewById(R.id.banner_text_content);
        timeTv = (TextView) findViewById(R.id.banner_text_time);
        leftBtn = findViewById(R.id.banner_left_btn);
        leftBtn.setOnClickListener(this);
        rightBtn = findViewById(R.id.banner_right_btn);
        rightBtn.setOnClickListener(this);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnPageChangeListener(this);
    }

    @Override
    public void refresh(final long currentTime) {

        MyHttpInterfae.getNewsRecent(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_news_list) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ArrayList<NewsRecentObject> newsContentObjects = NewsRecentObject.getList(reson);
                if (newsContentObjects != null && newsContentObjects.size() > 0) {
                    sendSuccessMessage(newsContentObjects, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        }, 10);
    }

    @Override
    public void loadData() {

    }

    ArrayList<NewsRecentObject> currentList;

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ArrayList<NewsRecentObject> newsContentObjects = (ArrayList<NewsRecentObject>) obj;
        currentList = newsContentObjects;
        if (newsContentObjects != null) {
            banner.stopAutoPlay();
            banner.setImages(newsContentObjects);
            banner.start();
        }
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    public class GlideImageLoader extends ImageLoader {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();

        @Override
        public void displayImage(Context context, Object path, final ImageView imageView) {
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(
                    ((NewsRecentObject) path).getHost() + ((NewsRecentObject) path).getThumb(), options, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            imageView.setImageBitmap(loadedImage);
                        }

                        @Override
                        public void onLoadingCancelled(String imageUri, View view) {
                            imageView.setImageDrawable(new ColorDrawable(Color.WHITE));
                        }
                    });
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            ImageView simpleDraweeView = new ImageView(context);
            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return simpleDraweeView;
        }
    }

    public void onStart() {
        if (banner != null)
            //开始轮播
            banner.startAutoPlay();
    }


    public void onStop() {
        if (banner != null)
            //结束轮播
            banner.stopAutoPlay();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        final ArrayList<NewsRecentObject> newsContentObjects = currentList;
        if (currentList != null && currentList.size() > position) {
            NewsRecentObject newsRecentObject = newsContentObjects.get(position);
            if (newsRecentObject == null)
                return;
            if (titleTv != null) {
                titleTv.setText(newsRecentObject.getTitle());
            }
            if (contentTv != null) {
                contentTv.setText(newsRecentObject.getDescription());
            }
            if (timeTv != null) {
                timeTv.setText(newsRecentObject.getCreate_time());
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner_left_btn:
                break;
            case R.id.banner_right_btn:
                break;
        }
    }
}
