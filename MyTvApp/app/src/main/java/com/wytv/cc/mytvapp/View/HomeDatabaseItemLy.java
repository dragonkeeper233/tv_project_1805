package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wytv.cc.mytvapp.Object.DatabaseObject;

import java.util.ArrayList;

public class HomeDatabaseItemLy extends RelativeLayout {
    public String time;
    public String last;
    private ArrayList<View> viewItemList = new ArrayList<View>();

    public HomeDatabaseItemLy(Context context) {
        super(context);
    }

    public HomeDatabaseItemLy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeDatabaseItemLy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addItem(DatabaseObject.Data data) {
        if (data == null)
            return;
        DataBaseItemView dataBaseItemView = new DataBaseItemView();
        View view = dataBaseItemView.init(getContext());
        addView(view);
        viewItemList.add(view);
        dataBaseItemView.setUI(data,time,last);
    }

}
