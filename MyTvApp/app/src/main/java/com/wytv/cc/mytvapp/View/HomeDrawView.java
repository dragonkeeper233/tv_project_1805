package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.wytv.cc.mytvapp.Object.ScreenMonitorObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

import java.util.ArrayList;
import java.util.List;

public class HomeDrawView extends BaseView implements IBaseView, View.OnClickListener {
    private int timeCount = 24;
    private Button time24Btn, time48Btn, time72Btn;
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
        time24Btn = (Button) findViewById(R.id.title_monitor_time_24);
        time24Btn.setOnClickListener(this);
        time48Btn = (Button) findViewById(R.id.title_monitor_time_48);
        time48Btn.setOnClickListener(this);
        time72Btn = (Button) findViewById(R.id.title_monitor_time_72);
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
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
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
                if (timeCount == 24)
                    return;
                timeCount = 24;
                time24Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_selector));
                time48Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                time72Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                activity.refresh();
                break;
            case R.id.title_monitor_time_48:
                if (timeCount == 48)
                    return;
                timeCount = 48;
                time48Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_selector));
                time24Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                time72Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                activity.refresh();
                break;
            case R.id.title_monitor_time_72:
                if (timeCount == 72)
                    return;
                timeCount = 72;
                time72Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_selector));
                time48Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
                time24Btn.setBackground(getResources().getDrawable(R.drawable.chat_btn_noselect_selector));
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
        LineData data = new LineData(setChartData(entries1, getResources().getColor(R.color.chat_line_red_color)),
                setChartData(entries2, getResources().getColor(R.color.chat_line_blue_color)),
                setChartData(entries3, getResources().getColor(R.color.chat_line_green_color)),
                setChartData(entries4, getResources().getColor(R.color.chat_line_yellow_color)));
        mChart.getXAxis().setValueFormatter(new MyAxisValueFormatter(screenMonitorObjects));
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
        chart.setNoDataTextColor(Color.WHITE);
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
        xAxis.setDrawGridLines(false);
        // 设置x轴数据的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(Color.TRANSPARENT);
        xAxis.setTextColor(getResources().getColor(R.color.chat_xy_text_color));
        xAxis.setTextSize(12);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisLineColor(getResources().getColor(R.color.chat_xy_text_color));
        // 设置x轴数据偏移量
        xAxis.setYOffset(5);

        YAxis yAxis = chart.getAxisLeft();
        // 不显示y轴
//        yAxis.setDrawAxisLine(false);
        // 设置y轴数据的位置
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        // 不从y轴发出横向直线
        yAxis.setDrawGridLines(true);
        yAxis.setTextColor(getResources().getColor(R.color.chat_xy_text_color));
        yAxis.setAxisLineColor(getResources().getColor(R.color.chat_xy_text_color));
        yAxis.setGridColor(getResources().getColor(R.color.chat_xy_text_color));
        yAxis.setTextSize(12);
        // 设置y轴数据偏移量
        yAxis.setXOffset(5);
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
    public LineDataSet setChartData(List<Entry> values, int color) {
        LineDataSet lineDataSet;

        lineDataSet = new LineDataSet(values, "");
        // 设置曲线颜色
        lineDataSet.setColor(color);
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

    private class MyAxisValueFormatter implements IAxisValueFormatter {
        ArrayList<ScreenMonitorObject> screenMonitorObjects;

        public MyAxisValueFormatter(ArrayList<ScreenMonitorObject> screenMonitorObjects) {
            this.screenMonitorObjects = screenMonitorObjects;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            final ArrayList<ScreenMonitorObject> list = screenMonitorObjects;
            int i = (int) value;
            if (list != null && list.size() > i) {
                return list.get(i).getDate();
            }
            return value + "";
        }
    }

    ;
}
