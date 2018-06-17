package com.wytv.cc.mytvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.MYSharePreference;
import com.wytv.cc.mytvapp.View.VideoContentView;
import com.wytv.cc.mytvapp.View.VideoTitleView;

public class VideoActivity extends ComonActivity {
    private VideoTitleView videoTitleView;
    private VideoContentView videoContentView;
    private TextView lastTiemTv;

    @Override
    protected long getMillisInFutureTime() {
        return MYSharePreference.getInstance().getVideoTimeSp();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoTitleView = (VideoTitleView) findViewById(R.id.video_title);
        videoTitleView.activity = this;
        lastTiemTv = (TextView) findViewById(R.id.video_time_tv);
        videoContentView = (VideoContentView) findViewById(R.id.video_content_ly);
        videoContentView.setActivity(this);
        refresh();
    }

    @Override
    protected int getContentViewID() {
        return R.layout.activity_video;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoContentView != null)
            videoContentView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoContentView != null)
            videoContentView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoContentView != null)
            videoContentView.onStop();
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
        Intent intent = new Intent(this, MyMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void toLeft() {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void setPhotoTimeTvText(String time) {
        if (lastTiemTv != null)
            lastTiemTv.setText(time);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            toLeft();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
