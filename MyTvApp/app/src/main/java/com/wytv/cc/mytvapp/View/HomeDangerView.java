package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.DengerObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;

public class HomeDangerView extends BaseView implements IBaseView {
    private LinearLayout rootLy;

    public HomeDangerView(Context context) {
        super(context);
        init(context);
    }

    public HomeDangerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeDangerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_danger, this);
        rootLy = (LinearLayout) findViewById(R.id.screen_danger_ly);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getScreenDanger(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_screen_danger) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ArrayList<DengerObject> result = DengerObject.getObj(reson);
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
        ArrayList<DengerObject> dengerObjectArrayList = (ArrayList<DengerObject>) obj;
        refreshUI(dengerObjectArrayList);
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    private void refreshUI(ArrayList<DengerObject> data) {
        if (data == null || data.size() == 0)
            return;
        rootLy.removeAllViews();
        DengerObject dengerObject;
        for (int i = 0; i < data.size(); i++) {
            dengerObject = data.get(i);
            HomeDatabaseItemLy homeDatabaseItemLy = new HomeDatabaseItemLy(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            int padding = CommonUtils.dip2px(getContext(), 10);
            layoutParams.leftMargin = padding;
            layoutParams.rightMargin = padding;
            layoutParams.topMargin = padding;
            layoutParams.bottomMargin = padding;
            rootLy.addView(homeDatabaseItemLy, layoutParams);
            homeDatabaseItemLy.time = dengerObject.getCreate_time();
            homeDatabaseItemLy.last = dengerObject.getTimeago();
            homeDatabaseItemLy.setBackgroundResource(dengerObject.getIs_error() == 1 ? R.drawable.danger_danger_bg : R.drawable.danger_nomal_bg);
            final ArrayList<DengerObject.DangerData> items = dengerObject.getData();
            if (items != null && items.size() != 0) {
                for (int j = 0; j < items.size(); j++) {
                    if (j == 0)
                        homeDatabaseItemLy.addItem(items.get(j));
                }
            }

        }
    }
}
