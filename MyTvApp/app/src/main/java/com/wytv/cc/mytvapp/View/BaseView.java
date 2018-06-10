package com.wytv.cc.mytvapp.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.wytv.cc.mytvapp.activity.ComonActivity;

public abstract class BaseView extends LinearLayout {
    public ComonActivity activity;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void creatMessage(Object obj, long currentTime, int TAG) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putLong("time", currentTime);
        message.setData(bundle);
        message.obj = obj;
        message.what = TAG;
        handler.sendMessage(message);
    }

    public void sendFailedMessage(String str, long currentTime) {
        creatMessage(str, currentTime, HOME_TITLE_MESSAGE_FAILED);
    }

    public void sendSuccessMessage(Object obj, long currentTime) {
        creatMessage(obj, currentTime, HOME_TITLE_MESSAGE_SUCCESS);
    }

    private static final int HOME_TITLE_MESSAGE_SUCCESS = 2736;
    private static final int HOME_TITLE_MESSAGE_FAILED = 2739;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HOME_TITLE_MESSAGE_SUCCESS) {
                Bundle bundle = msg.getData();
                handleSuccess(msg.obj, bundle.getLong("time"));

            } else if (msg.what == HOME_TITLE_MESSAGE_FAILED) {
                Bundle bundle = msg.getData();
                handleFailed((String) msg.obj, bundle.getLong("time"));
            }
        }
    };

    public abstract void handleSuccess(Object obj, long currentTime);

    public abstract void handleFailed(String str, long currentTime);

    public void alreadyRefresh( long currentTime) {
        if (activity != null) {
            activity.alreadyRefresh(this, currentTime);
        }
    }

    public void setActivity(ComonActivity activity){
        this.activity = activity;
    }
}
