package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.wytv.cc.mytvapp.Object.ScreenReportObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.activity.MyMainActivity;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class HomeReportView extends BaseView implements IBaseView, RrportItemAdapter.OnTypeClickListener, RecyclerViewTV.OnItemListener, RecyclerViewTV.OnItemClickListener {
    private String currentType = ScreenReportObject.TYPE_DAY;
    private RecyclerViewTV recyclerView;
//    Button dayBtn, weekBtn, monthBtn;

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
        recyclerView.setSelectedItemAtCentered(true);
        recyclerView.setOnItemListener(this);
        recyclerView.setOnItemClickListener(this);
//        dayBtn = findViewById(R.id.day);
//        dayBtn.setOnClickListener(this);
//        weekBtn = findViewById(R.id.week);
//        weekBtn.setOnClickListener(this);
//        monthBtn = findViewById(R.id.month);
//        monthBtn.setOnClickListener(this);
    }

    @Override
    public void onTypeClick(View v, String type) {
        if (currentType == type)
            return;
        currentType = type;
        activity.refresh();
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

    private RrportItemAdapter rrportItemAdapter;

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ScreenReportObject screenReportObject = (ScreenReportObject) obj;
        if (screenReportObject == null)
            return;
        GridLayoutManager mgr = new GridLayoutManager(getContext(), screenReportObject.getReportByDates().size() + 2);
        recyclerView.setLayoutManager(mgr);
        rrportItemAdapter = new RrportItemAdapter(screenReportObject, getContext()
                , activity instanceof MyMainActivity ? (MyMainActivity) activity : null, currentType);
        rrportItemAdapter.setOnTypeClickListener(this);
        recyclerView.setAdapter(rrportItemAdapter);
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    private boolean shouldChange(int position) {
        return rrportItemAdapter != null
                && (rrportItemAdapter.getItemViewType(position) == RrportItemAdapter.ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal()
                || rrportItemAdapter.getItemViewType(position) == RrportItemAdapter.ITEM_TYPE.ITEM_TYPE_MORE.ordinal()
                || rrportItemAdapter.getItemViewType(position) == RrportItemAdapter.ITEM_TYPE.ITEM_TYPE_BTN.ordinal());
    }


    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        if (shouldChange(position)) {
            itemView.setBackgroundResource(android.R.color.transparent);
        }
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        if (shouldChange(position)) {
            itemView.setBackgroundResource(R.color.news_content_yellow);
        }
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        if (shouldChange(position)) {
            itemView.setBackgroundResource(R.color.news_content_yellow);
        }
    }

    @Override
    public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
        if (shouldChange(position)) {
            itemView.setBackgroundResource(R.color.news_content_yellow);
        }
    }
}
