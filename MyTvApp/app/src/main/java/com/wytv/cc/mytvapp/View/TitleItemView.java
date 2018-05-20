package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

public class TitleItemView extends LinearLayout {
    private ImageView imageView;
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
        imageView = new ImageView(context);
        addView(imageView);
        LinearLayout.LayoutParams countLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        countLp.leftMargin = CommonUtils.dip2px(context, 5);
        countView = new TextView(context);
        countView.setGravity(Gravity.CENTER);
        countView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        countView.setTextColor(Color.WHITE);
        addView(countView, countLp);
        titleView = new TextView(context);
        titleView.setGravity(Gravity.CENTER);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        titleView.setTextColor(context.getResources().getColor(R.color.home_title_text_blue));
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleLp.leftMargin = CommonUtils.dip2px(context, 5);
        addView(titleView, titleLp);
    }

    public void setImage(int resource) {
        if (imageView != null)
            imageView.setImageResource(resource);
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
