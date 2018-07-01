package com.wytv.cc.mytvapp.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class CustomLinearLayout extends LinearLayout implements View.OnFocusChangeListener, View.OnHoverListener {

    public CustomViewInterface getCustomViewInterface() {
        return customViewInterface;
    }

    public void setCustomViewInterface(CustomViewInterface customViewInterface) {
        this.customViewInterface = customViewInterface;
    }

    private CustomViewInterface customViewInterface;

    public CustomLinearLayout(Context context) {
        super(context);
        init();
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void init() {
        this.setOnFocusChangeListener(this);
        this.setOnHoverListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (customViewInterface != null) {
            customViewInterface.setFocusedChanged( hasFocus);//外部接口调用
        }
        DisplayUtil.setViewAnimator(v, hasFocus);//放大缩小动画
    }

    //鼠标事件处理
    @Override
    public boolean onHover(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_HOVER_ENTER) {
            DisplayUtil.setViewAnimator(v, true);
        } else if (event.getAction() == MotionEvent.ACTION_HOVER_EXIT) {
            DisplayUtil.setViewAnimator(v, false);
        }
        return false;
    }
}