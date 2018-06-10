package com.wytv.cc.mytvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.View.NewsBanderView;
import com.wytv.cc.mytvapp.View.NewsContentView;
import com.wytv.cc.mytvapp.View.NewsTitleView;

public class NewsActivity extends ComonActivity {
    private NewsBanderView banderView;
    private NewsTitleView newsTitleView;
    private NewsContentView newsContentView;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_news;
    }

    @Override
    protected int getViewCount() {
        return 3;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsTitleView = (NewsTitleView) findViewById(R.id.news_title);
        newsTitleView.activity = this;
        banderView = (NewsBanderView) findViewById(R.id.news_recent_ly);
        banderView.activity = this;
        newsContentView = (NewsContentView) findViewById(R.id.news_content_ly);
        newsContentView.activity = this;
        refresh();
    }

    @Override
    public void refreshByTime(long time) {
        if (newsTitleView != null) {
            newsTitleView.refresh(time);
        }
        if (banderView != null) {
            banderView.refresh(time);
        }
        if (newsContentView != null) {
            newsContentView.refresh(time);
        }
    }


    @Override
    public void toRight() {
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void toLeft() {
        Intent intent = new Intent(this, MyMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (banderView != null)
            banderView.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (banderView != null)
            banderView.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            toLeft();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }
}
