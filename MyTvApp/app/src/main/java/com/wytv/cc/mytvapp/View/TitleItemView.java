package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleItemView extends LinearLayout {
    private TextView countView;
    private TextView titleView;

    public TitleItemView(Context context) {
        super(context);
        initView(context);
    }

    public TitleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        countView = new TextView(context);
        countView.setGravity(Gravity.CENTER);
        countView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        addView(countView);
        titleView = new TextView(context);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        addView(titleView);
    }

    public void setTitle(String str) {
        if (titleView != null)
            titleView.setText(str);
    }

    public void setCount(String str) {
        if (countView != null)
            countView.setText(str);
    }
}
