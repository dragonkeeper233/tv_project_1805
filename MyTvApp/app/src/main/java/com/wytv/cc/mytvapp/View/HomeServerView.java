package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.MediaUtils;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;
import com.wytv.cc.mytvapp.Object.ScreenSeverObject;

public class HomeServerView extends BaseView implements IBaseView {
    private TextView timerTv, lastTimeTv, cpuTv, cipanTv, tingdunTv, neicunTv, waiwangTv, neiwangTv;
    private LinearLayout last_time_ly;
    private long currentTime = System.currentTimeMillis();
    private long TEN_TIME = 10 * 60 * 1000;


    public HomeServerView(Context context) {
        super(context);
        init(context);
    }

    public HomeServerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeServerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_server, this);
        timerTv = findViewById(R.id.last_time_tv);
        lastTimeTv = findViewById(R.id.last_time_content);
        cpuTv = findViewById(R.id.server_cup_tv);
        cipanTv = findViewById(R.id.server_cipan_tv);
        tingdunTv = findViewById(R.id.server_tingdun_tv);
        neicunTv = findViewById(R.id.server_neicun_tv);
        waiwangTv = findViewById(R.id.server_waiwang_tv);
        neiwangTv = findViewById(R.id.server_neiwang_tv);
        last_time_ly = findViewById(R.id.last_time_ly);

    }

    @Override
    public void refresh(final long currentTime) {

        MyHttpInterfae.getScreenServer(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_screen_sever) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ScreenSeverObject screenSeverObject = ScreenSeverObject.getObj(reson);
                if (screenSeverObject != null) {
                    sendSuccessMessage(screenSeverObject, currentTime);
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
        HomeServerView.this.currentTime = System.currentTimeMillis();
        setDanger(false);
        ScreenSeverObject screenSeverObject = (ScreenSeverObject) obj;
        if (screenSeverObject == null)
            return;
        if (lastTimeTv != null)
            lastTimeTv.setText(screenSeverObject.getScreen_time());
        if (cpuTv != null)
            cpuTv.setText(screenSeverObject.getStatus().getCpu_usage());
        if (cipanTv != null)
            cipanTv.setText(screenSeverObject.getStatus().getHd_used() + "G/" + screenSeverObject.getStatus().getHd_total() + "G");
        if (tingdunTv != null)
            tingdunTv.setText(screenSeverObject.getTimeago());
        if (neicunTv != null) {
            float show = screenSeverObject.getStatus().getMem_usage() * 100;
            neicunTv.setText((int) show + "%");
        }

        if (waiwangTv != null)
            waiwangTv.setText(screenSeverObject.getOuternet());
        if (neiwangTv != null)
            neiwangTv.setText(screenSeverObject.getIntranet());
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        if (System.currentTimeMillis() - HomeServerView.this.currentTime > TEN_TIME) {
            setDanger(true);
        }
        alreadyRefresh(currentTime);
    }

    public void setCountDownTimeTv(int count) {
        if (timerTv != null)
            timerTv.setText(count + "");
    }

    private void setDanger(boolean danger) {
        if (last_time_ly != null)
            last_time_ly.setBackgroundResource(danger ? R.drawable.sever_time_waring_bg : R.drawable.sever_time_bg);
        if (danger)
            MediaUtils.getInstance().play(getContext());

    }

}
