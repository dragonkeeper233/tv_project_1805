package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.ScreenReportObject;
import com.wytv.cc.mytvapp.Object.ScreenSeverObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class HomeReportView extends BaseView implements IBaseView, View.OnClickListener {
    private String currentType = ScreenReportObject.TYPE_DAY;
    private RecyclerView recyclerView;
    Button dayBtn, weekBtn, monthBtn;

    public HomeReportView(Context context) {
        super(context);
        init(context);
    }

    public HomeReportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeReportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_report, this);
        recyclerView = findViewById(R.id.report_rv);
        dayBtn = findViewById(R.id.day);
        dayBtn.setOnClickListener(this);
        weekBtn = findViewById(R.id.week);
        weekBtn.setOnClickListener(this);
        monthBtn = findViewById(R.id.month);
        monthBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.day:
                if (currentType == ScreenReportObject.TYPE_DAY)
                    return;
                currentType = ScreenReportObject.TYPE_DAY;
                dayBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_selector));
                weekBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                monthBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                activity.refresh();
                break;
            case R.id.week:
                if (currentType == ScreenReportObject.TYPE_WEEK)
                    return;
                currentType = ScreenReportObject.TYPE_WEEK;
                dayBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                weekBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_selector));
                monthBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                activity.refresh();
                break;
            case R.id.month:
                if (currentType == ScreenReportObject.TYPE_MONTH)
                    return;
                currentType = ScreenReportObject.TYPE_MONTH;
                dayBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                weekBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                monthBtn.setBackground(getResources().getDrawable(R.drawable.chat_btn_selector));
                activity.refresh();
                break;
        }

    }

    @Override
    public void refresh(final long currentTime) {

        MyHttpInterfae.getScreenReport(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_screen_report) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ScreenReportObject screenReportObject = ScreenReportObject.getObj(reson);
                if (screenReportObject != null) {
                    sendSuccessMessage(screenReportObject, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        }, currentType);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ScreenReportObject screenReportObject = (ScreenReportObject) obj;
        if (screenReportObject == null)
            return;
        GridLayoutManager mgr = new GridLayoutManager(getContext(), screenReportObject.getReportByDates().size() + 1);
        recyclerView.setLayoutManager(mgr);
        recyclerView.setAdapter(new RrportItemAdapter(screenReportObject, getContext()));
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }


}
