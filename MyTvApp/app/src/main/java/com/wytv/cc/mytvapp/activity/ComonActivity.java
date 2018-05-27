package com.wytv.cc.mytvapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.dyhdyh.support.countdowntimer.CountDownTimerSupport;
import com.dyhdyh.support.countdowntimer.OnCountDownTimerListener;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.LoadProgressDialog;

import java.util.HashMap;

public abstract class ComonActivity extends FragmentActivity implements OnCountDownTimerListener {

    private CountDownTimerSupport mTimer;
    private LoadProgressDialog customDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        mTimer = new CountDownTimerSupport(60000, 1000);
        mTimer.setOnCountDownTimerListener(this);
        customDialog = new LoadProgressDialog(this,
                R.style.CustomProgressDialog, R.layout.progress_dialog,
                R.anim.rotate_refresh, 0, R.drawable.loading_refresh,
                0, "正在获取数据请稍后");
        customDialog.setCancelable(false);
    }

    protected abstract int getContentViewID();

    protected abstract int getViewCount();

    private long currentTime;

    public void refresh() {
        mTimer.stop();
        if (!customDialog.isShowing())
            customDialog.show();
        currentTime = System.currentTimeMillis();
        refreshByTime(currentTime);
    }

    public abstract void toRight();

    public abstract void toLeft();

    public abstract void refreshByTime(long time);

    public void alreadyRefresh(View view, long loadTime) {
        if (remberCount(view, loadTime)) {
            mTimer.reset();
            mTimer.start();
            if (!isFinishing())
                customDialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTimer != null)
            mTimer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null)
            mTimer.pause();
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
        if (obj.count >= getViewCount()) {
            remberCountObjHashMap.remove(loadTime + "");
            return true;
        }
        return false;
    }

    public class RemberCountObj {
        public long time;
        public int count;
    }
}
