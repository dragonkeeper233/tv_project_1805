package com.wytv.cc.mytvapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dyhdyh.support.countdowntimer.CountDownTimerSupport;
import com.dyhdyh.support.countdowntimer.OnCountDownTimerListener;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.View.HomeDatabaseView;
import com.wytv.cc.mytvapp.View.HomeDrawView;
import com.wytv.cc.mytvapp.View.HomeServerView;
import com.wytv.cc.mytvapp.View.HometitleView;

import java.util.HashMap;

public class MyMainActivity extends ComonActivity implements OnCountDownTimerListener {

    private HometitleView hometitleView;
    private HomeServerView homeServerView;
    private HomeDrawView homeDrawView;
    private HomeDatabaseView homeDatabaseView;
    private static final int VIEW_COUNT = 4;

    private CountDownTimerSupport mTimer;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_main_new;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hometitleView = (HometitleView) findViewById(R.id.screen_title);
        hometitleView.activity = this;
        homeServerView = (HomeServerView) findViewById(R.id.screen_sever);
        homeServerView.activity = this;
        homeDrawView = (HomeDrawView) findViewById(R.id.screen_monitor);
        homeDrawView.activity = this;
        homeDatabaseView = (HomeDatabaseView) findViewById(R.id.screen_database);
        homeDatabaseView.activity = this;
        mTimer = new CountDownTimerSupport(60000, 1000);
        mTimer.setOnCountDownTimerListener(this);
        refresh();
    }

    private long currentTime;

    public void refresh() {
        mTimer.stop();
        currentTime = System.currentTimeMillis();
        if (hometitleView != null)
            hometitleView.refresh(currentTime);
        if (homeServerView != null)
            homeServerView.refresh(currentTime);
        if (homeDrawView != null)
            homeDrawView.refresh(currentTime);
        if (homeDatabaseView != null)
            homeDatabaseView.refresh(currentTime);
    }

    public void alreadyRefresh(View view, long loadTime) {
        if (remberCount(view, loadTime)) {
            mTimer.reset();
            mTimer.start();
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (homeServerView != null) {
            homeServerView.setCountDownTimeTv((int) millisUntilFinished / 1000);
        }
    }

    @Override
    public void onFinish() {
        refresh();
    }

    private HashMap<String, RemberCountObj> remberCountObjHashMap = new HashMap<String, RemberCountObj>();

    private synchronized boolean remberCount(View view, long loadTime) {
        RemberCountObj obj = remberCountObjHashMap.get(loadTime + "");
        if (obj == null) {
            obj = new RemberCountObj();
            obj.time = loadTime;
            obj.count++;
            remberCountObjHashMap.put(loadTime + "", obj);
        } else {
            obj.count++;
        }
        if (obj.count >= VIEW_COUNT) {
            remberCountObjHashMap.remove(loadTime + "");
            return true;
        }
        return false;
    }

    public class RemberCountObj {
        public long time;
        public int count;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.stop();
    }
}
