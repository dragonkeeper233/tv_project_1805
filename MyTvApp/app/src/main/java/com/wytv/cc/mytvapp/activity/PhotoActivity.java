package com.wytv.cc.mytvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.View.PhotoContentView;
import com.wytv.cc.mytvapp.View.PhotoTitleView;

public class PhotoActivity extends ComonActivity {
    private PhotoTitleView photoTitleView;
    private PhotoContentView photoContentView;
    private TextView photoTimeTv;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_photo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoTitleView = (PhotoTitleView) findViewById(R.id.photo_title);
        photoTitleView.activity = this;
        photoTimeTv = (TextView) findViewById(R.id.photo_time_tv);
        photoContentView = (PhotoContentView) findViewById(R.id.photo_content_ly);
        photoContentView.activity = this;
        refresh();
    }

    @Override
    protected int getViewCount() {
        return 2;
    }

    @Override
    public void refreshByTime(long time) {
        if (photoTitleView != null)
            photoTitleView.refresh(time);
        if (photoContentView != null)
            photoContentView.refresh(time);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    public void setPhotoTimeTvText(String time) {
        if (photoTimeTv != null)
            photoTimeTv.setText(getResources().getString(R.string.content_last_time_str) + " " + time);
    }

    @Override
    public void toRight() {
        Intent intent = new Intent(this, MyMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void toLeft() {
        Intent intent = new Intent(this, MyMainActivity.class);
        startActivity(intent);
    }
}