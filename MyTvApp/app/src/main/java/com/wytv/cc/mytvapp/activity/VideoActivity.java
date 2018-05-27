package com.wytv.cc.mytvapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.View.VideoContentView;
import com.wytv.cc.mytvapp.View.VideoTitleView;

public class VideoActivity extends ComonActivity {
    private VideoTitleView videoTitleView;
    private VideoContentView videoContentView;
    private TextView lastTiemTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoTitleView = (VideoTitleView) findViewById(R.id.video_title);
        videoTitleView.activity = this;
        lastTiemTv = (TextView) findViewById(R.id.video_time_tv);
        videoContentView = (VideoContentView) findViewById(R.id.video_content_ly);
        videoContentView.activity = this;
        refresh();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_video;
    }

    @Override
    protected int getViewCount() {
        return 2;
    }

    @Override
    public void refreshByTime(long time) {
        if (videoTitleView != null)
            videoTitleView.refresh(time);
        if (videoContentView != null)
            videoContentView.refresh(time);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void toRight() {

    }

    @Override
    public void toLeft() {

    }

    public void setPhotoTimeTvText(String time) {
        if (lastTiemTv != null)
            lastTiemTv.setText(time);
    }
}
