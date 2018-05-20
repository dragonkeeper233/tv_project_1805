package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wytv.cc.mytvapp.Object.ScreenMonitorObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;
import java.util.List;

public class HomeDrawView extends BaseView implements IBaseView, View.OnClickListener {
    private int timeCount = 24;
    private TextView time24Btn, time48Btn, time72Btn;
    private LineChart mChart;


    public HomeDrawView(Context context) {
        super(context);
        init(context);
    }

    public HomeDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_draw, this);
        time24Btn = (TextView) findViewById(R.id.title_monitor_time_24);
        time24Btn.setOnClickListener(this);
        time48Btn = (TextView) findViewById(R.id.title_monitor_time_48);
        time48Btn.setOnClickListener(this);
        time72Btn = (TextView) findViewById(R.id.title_monitor_time_72);
        time72Btn.setOnClickListener(this);
        mChart = (LineChart) findViewById(R.id.chart);
        initChart(mChart);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getScreenMoitor(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_screen_monitor) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ArrayList<ScreenMonitorObject> screenMonitorObjects = ScreenMonitorObject.getObj(reson);
                if (screenMonitorObjects != null) {
                    sendSuccessMessage(screenMonitorObjects, currentTime);
                }
            }
        }, timeCount);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        sortDataAndShow((ArrayList<ScreenMonitorObject>) obj);
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_monitor_time_24:
                timeCount = 24;
                activity.refresh();
                break;
            case R.id.title_monitor_time_48:
                timeCount = 48;
                activity.refresh();
                break;
            case R.id.title_monitor_time_72:
                timeCount = 72;
                activity.refresh();
                break;
        }
    }

    private void sortDataAndShow(final ArrayList<ScreenMonitorObject> screenMonitorObjects) {
        if (screenMonitorObjects == null || screenMonitorObjects.size() == 0)
            return;
        List<Entry> entries1 = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        List<Entry> entries3 = new ArrayList<>();
        List<Entry> entries4 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries1.add(new Entry(i, screenMonitorObjects.get(i).getDanger_count()));
            entries2.add(new Entry(i, screenMonitorObjects.get(i).getDatabase_danger_count()));
            entries3.add(new Entry(i, screenMonitorObjects.get(i).getDatabase_insert_count()));
            entries4.add(new Entry(i, screenMonitorObjects.get(i).getResources_add_count()));
        }
        mChart.clear();
        LineData data = new LineData(setChartData(entries1), setChartData(entries2), setChartData(entries3), setChartData(entries4));
        mChart.setData(data);
        mChart.invalidate();
    }

    /**
     * 初始化图表
     *
     * @param chart 原始图表
     * @return 初始化后的图表
     */
    private LineChart initChart(LineChart chart) {
        // 不显示数据描述
        chart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        chart.setNoDataText("暂无数据");
        // 不显示表格颜色
        chart.setDrawGridBackground(false);
        // 不可以缩放
        chart.setScaleEnabled(false);
        // 不显示y轴右边的值
        chart.getAxisRight().setEnabled(false);
        // 不显示图例
        Legend legend = chart.getLegend();
        legend.setEnabled(false);
//        // 向左偏移15dp，抵消y轴向右偏移的30dp
//        chart.setExtraLeftOffset(-15);

        XAxis xAxis = chart.getXAxis();
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.WHITE);
        // 设置x轴数据偏移量
        xAxis.setYOffset(-12);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(false);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(30);
        yAxis.setYOffset(-3);
        yAxis.setAxisMinimum(0);

        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);
        // x轴执行动画
        //chart.animateX(2000);
        chart.invalidate();
        return chart;
    }

    /**
     * 设置图表数据
     *
     * @param values 数据
     */
    public LineDataSet setChartData(List<Entry> values) {
        LineDataSet lineDataSet;

        lineDataSet = new LineDataSet(values, "");
        // 设置曲线颜色
        lineDataSet.setColor(Color.parseColor("#FFFFFF"));
        // 设置平滑曲线
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // 不显示坐标点的小圆点
        lineDataSet.setDrawCircles(true);
        // 不显示坐标点的数据
        lineDataSet.setDrawValues(false);
        // 不显示定位线
        lineDataSet.setHighlightEnabled(false);
        return lineDataSet;
    }
}
