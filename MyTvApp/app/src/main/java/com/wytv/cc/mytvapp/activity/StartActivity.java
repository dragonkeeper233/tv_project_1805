package com.wytv.cc.mytvapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.TableLayout;
import android.widget.VideoView;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.View.PlayerManager;
import com.wytv.cc.mytvapp.widget.media.IjkVideoView;

import java.net.URI;
import java.net.URL;

public class StartActivity extends Activity implements MediaPlayer.OnCompletionListener {
    private VideoView ijkVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ijkVideoView = (VideoView) findViewById(R.id.video_start);
        ijkVideoView.setOnCompletionListener(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.start;
        ijkVideoView.setVideoURI(Uri.parse(uri));
        ijkVideoView.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        gotoHome();
    }

    private void gotoHome() {
        Intent intent = new Intent();
        intent.setClass(this, MyMainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (ijkVideoView != null)
            ijkVideoView.resume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (ijkVideoView != null)
            ijkVideoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (ijkVideoView != null)
            ijkVideoView.stopPlayback();
    }


}
