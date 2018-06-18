package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;

public abstract class BasetitleView extends BaseView implements IBaseView, View.OnClickListener {

    public abstract int getCount();

    public abstract int[] getTitleDrawable();

    public abstract String[] getContentTitle();

    public void setTitleContent(String... content) {
        if (content != null && content.length > 0) {
            for (int i = 0; i < getCount(); i++) {
                titleItemViews[i].setCount(content[i]);
            }
        }
    }


    private TitleItemView[] titleItemViews = new TitleItemView[5];
    private ImageButton refreshBtn, leftBtn, rightBtn;
    protected TextView titleTv;

    public BasetitleView(Context context) {
        super(context);
    }

    public BasetitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasetitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_title, this);
        titleTv = (TextView) findViewById(R.id.title_text);
        titleItemViews[0] = (TitleItemView) findViewById(R.id.title_0);
        titleItemViews[1] = (TitleItemView) findViewById(R.id.title_1);
        titleItemViews[2] = (TitleItemView) findViewById(R.id.title_2);
        titleItemViews[3] = (TitleItemView) findViewById(R.id.title_3);
        titleItemViews[4] = (TitleItemView) findViewById(R.id.title_4);
        for (int i = 0; i < titleItemViews.length; i++) {
            if (getCount() <= i) {
                titleItemViews[i].setVisibility(View.GONE);
                continue;
            }
            if (getTitleDrawable() != null && getTitleDrawable().length > i) {
                titleItemViews[i].setImage(getTitleDrawable()[i]);
            }
            if (getContentTitle() != null && getContentTitle().length > i) {
                titleItemViews[i].setTitle(getContentTitle()[i]);
            }
        }
        refreshBtn = (ImageButton) findViewById(R.id.title_refresh_btn);
        refreshBtn.setOnClickListener(this);
        leftBtn = (ImageButton) findViewById(R.id.title_left_btn);
        leftBtn.setOnClickListener(this);
        rightBtn = (ImageButton) findViewById(R.id.title_right_btn);
        rightBtn.setOnClickListener(this);
    }


    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_refresh_btn:
                if (activity != null)
                    activity.refresh();
                break;
            case R.id.title_left_btn:
                if (activity != null)
                    activity.toLeft();
                break;
            case R.id.title_right_btn:
                if (activity != null)
                    activity.toRight();
                break;
        }
    }
}
