package com.wytv.cc.mytvapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.MYSharePreference;
import com.wytv.cc.mytvapp.Utils.VersionUtils;
import com.wytv.cc.mytvapp.http.UrlUtils;

public class TestEditActivity extends ControlActivity implements View.OnClickListener {
    private EditText tokenEt, urlEt;
    private EditText homeTimeEt, photoTimeEt, newsTimeEt, videoTimeEt;
    private static final long TIME_BASE = (long) 60000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_test);
        tokenEt = (EditText) findViewById(R.id.token_edt);
        urlEt = (EditText) findViewById(R.id.url_edt);
        TextView textView = findViewById(R.id.version);
        textView.setText("当前版本"+ VersionUtils.getVersionName(this));
        findViewById(R.id.ok).setOnClickListener(this);
        urlEt.setText(UrlUtils.BASE_URL);
        tokenEt.setText(UrlUtils.TOKEN);
        homeTimeEt = (EditText) findViewById(R.id.home_time_edt);
        homeTimeEt.setText((MYSharePreference.getInstance().getHomeTimeSp() / TIME_BASE) + "");
        photoTimeEt = (EditText) findViewById(R.id.photo_time_edt);
        photoTimeEt.setText((MYSharePreference.getInstance().getPhotoTimeSp() / TIME_BASE) + "");
        newsTimeEt = (EditText) findViewById(R.id.news_time_edt);
        newsTimeEt.setText((MYSharePreference.getInstance().getNewsTimeSp() / TIME_BASE) + "");
        videoTimeEt = (EditText) findViewById(R.id.video_time_edt);
        videoTimeEt.setText((MYSharePreference.getInstance().getVideoTimeSp() / TIME_BASE) + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                change();
                break;
        }

    }

    private void change() {
        String newUrl, newToken;
        newUrl = urlEt.getText().toString();
        newToken = tokenEt.getText().toString();
        if (!TextUtils.isEmpty(newUrl) && !newUrl.equals(UrlUtils.BASE_URL)) {
            MYSharePreference.getInstance().setBaseUrl(newUrl);
            UrlUtils.BASE_URL = MYSharePreference.getInstance().getBaseUrl();
            Toast.makeText(this, "Url修改成功", Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(newToken) && !newToken.equals(UrlUtils.TOKEN)) {
            MYSharePreference.getInstance().setToken(newToken);
            UrlUtils.TOKEN = MYSharePreference.getInstance().getToken();
            Toast.makeText(this, "token修改成功", Toast.LENGTH_LONG).show();
        }
        saveTime();
        finish();
    }

    private void saveTime() {
        long homeTime = praseInt(homeTimeEt.getText().toString());
        if (homeTime > 0 && homeTime != MYSharePreference.getInstance().getHomeTimeSp()) {
            MYSharePreference.getInstance().setHomeTimeSp(homeTime);
            Toast.makeText(this, "首页轮询时间修改成功", Toast.LENGTH_LONG).show();
        }

        long newsTime = praseInt(newsTimeEt.getText().toString());
        if (newsTime > 0 && newsTime != MYSharePreference.getInstance().getNewsTimeSp()) {
            MYSharePreference.getInstance().setNewsTimeSp(newsTime);
            Toast.makeText(this, "新闻轮询时间修改成功", Toast.LENGTH_LONG).show();
        }

        long photoTime = praseInt(photoTimeEt.getText().toString());
        if (photoTime > 0 && photoTime != MYSharePreference.getInstance().getPhotoTimeSp()) {
            MYSharePreference.getInstance().setPhotoTimeSp(photoTime);
            Toast.makeText(this, "图片轮询时间修改成功", Toast.LENGTH_LONG).show();
        }

        long videoTime = praseInt(videoTimeEt.getText().toString());
        if (videoTime > 0 && videoTime != MYSharePreference.getInstance().getVideoTimeSp()) {
            MYSharePreference.getInstance().setVideoTimeSp(videoTime);
            Toast.makeText(this, "视频轮询时间修改成功", Toast.LENGTH_LONG).show();
        }
    }

    private long praseInt(String str) {
        try {
            return Long.parseLong(str) * TIME_BASE;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
