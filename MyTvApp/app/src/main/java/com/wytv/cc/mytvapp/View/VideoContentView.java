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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.wytv.cc.mytvapp.Object.PhotoObject;
import com.wytv.cc.mytvapp.Object.VideoObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.activity.ComonActivity;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;
import com.wytv.cc.mytvapp.widget.media.IjkVideoView;

import java.util.ArrayList;

public class VideoContentView extends BaseView implements IBaseView, PlayerManager.PlayerStateListener, RecyclerViewTV.OnItemListener, RecyclerViewTV.OnItemClickListener {
    private IjkVideoView ijkVideoView;
    private RecyclerViewTV recyclerView;
    private PlayerManager player;
    private TableLayout video_start;


    public VideoContentView(Context context) {
        super(context);
        init(context);
    }

    public VideoContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_video_content, this);
        ijkVideoView = (IjkVideoView) findViewById(R.id.video_left);
        recyclerView = (RecyclerViewTV) findViewById(R.id.video_rv);

        video_start = (TableLayout) findViewById(R.id.video_start);
        ijkVideoView.setHudView(video_start);
        GridLayoutManager mgr = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(mgr);
        recyclerView.setSelectedItemAtCentered(true);
        recyclerView.setOnItemListener(this);
        recyclerView.setOnItemClickListener(this);
    }

    @Override
    public void setActivity(ComonActivity activity) {
        super.setActivity(activity);
        initPlayer();
    }

    @Override
    public void refresh(final long currentTime) {

        MyHttpInterfae.getVideoLists(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_video_list) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ArrayList<VideoObject> videoObjects = VideoObject.getList(reson);
                if (videoObjects != null && videoObjects.size() > 0) {
                    sendSuccessMessage(videoObjects, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        }, 1, 50);
    }

    @Override
    public void loadData() {

    }
    private   ArrayList<VideoObject> mVideoObjects;
    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ArrayList<VideoObject> videoObjects = (ArrayList<VideoObject>) obj;
        if (videoObjects != null && videoObjects.size() > 0) {
            mVideoObjects = videoObjects;
            recyclerView.setAdapter(new VideoItemAdapter(videoObjects, activity));
            ijkVideoView.setVideoPath(videoObjects.get(0).getUrl());
            ijkVideoView.start();
        }
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    private void initPlayer() {
        player = new PlayerManager(activity, ijkVideoView);
        player.setFullScreenOnly(true);
//        player.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
        player.playInFullScreen(true);
        player.setPlayerStateListener(this);
//        player.play(url1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (player.gestureDetector.onTouchEvent(event))
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError() {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onPlay() {
    }

    public void onResume() {
        if (ijkVideoView != null)
            ijkVideoView.resume();
    }

    public void onPause() {
        if (ijkVideoView != null)
            ijkVideoView.pause();
    }

    public void onStop() {
        if (ijkVideoView != null)
            ijkVideoView.stopPlayback();
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
    }

    @Override
    public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
        itemView.animate().scaleX(1.2f).scaleY(1.2f).setDuration(300).start();
        ijkVideoView.stopPlayback();
        ijkVideoView.setVideoPath(mVideoObjects.get(position).getUrl());
        ijkVideoView.start();
    }
}
