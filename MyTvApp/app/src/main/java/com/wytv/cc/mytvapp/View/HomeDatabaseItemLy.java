package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

import java.util.ArrayList;

public class HomeDatabaseItemLy extends RelativeLayout {
    public String time;
    public String last;
    private ArrayList<View> viewItemList = new ArrayList<View>();

    public HomeDatabaseItemLy(Context context) {
        super(context);
        setMyStyle();
    }

    public HomeDatabaseItemLy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMyStyle();
    }

    public HomeDatabaseItemLy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyStyle();
    }

    public void addItem(Object data) {
        if (data == null)
            return;
        DataBaseItemView dataBaseItemView = new DataBaseItemView();
        View view = dataBaseItemView.init(getContext());
        addView(view);
        viewItemList.add(view);
        dataBaseItemView.setUI(data, time, last);
    }

    private void setMyStyle() {
        int padding = CommonUtils.dip2px(getContext(), 5);
        setPadding(padding, padding, padding, padding);
    }

}
