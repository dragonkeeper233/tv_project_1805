package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.DatabaseObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;

public class HomeDatabaseView extends BaseView implements IBaseView {
    private LinearLayout rootLy;

    public HomeDatabaseView(Context context) {
        super(context);
        init(context);
    }

    public HomeDatabaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeDatabaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_database, this);
        rootLy = (LinearLayout) findViewById(R.id.screen_database_ly);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getScreenDatabase(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_screen_database) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ArrayList<DatabaseObject> result = DatabaseObject.getObj(reson);
                if (result != null) {
                    sendSuccessMessage(result, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        });

    }

    @Override
    public void loadData() {

    }

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ArrayList<DatabaseObject> databaseObjects = (ArrayList<DatabaseObject>) obj;
        refreshUI(databaseObjects);
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    private void refreshUI(final ArrayList<DatabaseObject> databaseObjects) {
        if (databaseObjects == null || databaseObjects.size() == 0)
            return;
        rootLy.removeAllViews();
        DatabaseObject databaseObject;
        for (int i = 0; i < databaseObjects.size(); i++) {
            databaseObject = databaseObjects.get(i);
            HomeDatabaseItemLy homeDatabaseItemLy = new HomeDatabaseItemLy(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            int padding = CommonUtils.dip2px(getContext(), 10);
            layoutParams.leftMargin = padding;
            layoutParams.rightMargin = padding;
            layoutParams.topMargin = padding;
            layoutParams.bottomMargin = padding;
            rootLy.addView(homeDatabaseItemLy, layoutParams);
            homeDatabaseItemLy.setBackgroundResource(databaseObject.getIs_warning() == 1 ? R.drawable.danger_danger_bg : R.drawable.danger_nomal_bg);
            homeDatabaseItemLy.time = databaseObject.getDate();
            homeDatabaseItemLy.last = databaseObject.getTimeago();
            final ArrayList<DatabaseObject.Data> data = databaseObject.getData();
            if (data != null && data.size() != 0) {
                for (int j = 0; j < data.size(); j++) {
                    if (j == 0)
                        homeDatabaseItemLy.addItem(data.get(j));
                }
            }

        }

    }


}
