package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytv.cc.mytvapp.Object.ScreenReportObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

import java.util.HashMap;

public class RrportItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static enum ITEM_TYPE {
        ITEM_TYPE_FIRST,
        ITEM_TYPE_LEFT,
        ITEM_TYPE_TOP,
        ITEM_TYPE_CONTENT;
    }

    private ScreenReportObject screenReportObject;
    private Context context;
    private LayoutInflater inf;
    private int width;

    public RrportItemAdapter(ScreenReportObject screenReportObject, Context context) {
        this.screenReportObject = screenReportObject;
        this.context = context;
        inf = LayoutInflater.from(context);
        if (screenReportObject != null && screenReportObject.getReportByDates() != null && screenReportObject.getReportByDates().size() > 0) {
            width = (CommonUtils.getScreenWidth(context) - CommonUtils.dip2px(context, 80)) / (screenReportObject.getReportByDates().size() + 1);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return ITEM_TYPE.ITEM_TYPE_FIRST.ordinal();
        if (screenReportObject != null && screenReportObject.getReportByDates() != null &&
                position < screenReportObject.getReportByDates().size() + 1)
            return ITEM_TYPE.ITEM_TYPE_TOP.ordinal();
        if (screenReportObject != null && screenReportObject.getReportByDates() != null &&
                position % (screenReportObject.getReportByDates().size() + 1) == 0)
            return ITEM_TYPE.ITEM_TYPE_LEFT.ordinal();
        return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_LEFT.ordinal()) {
            return new LeftViewHolder(inf.inflate(R.layout.layout_report_title, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_TOP.ordinal()) {
            return new TopViewHolder(inf.inflate(R.layout.layout_report_title, parent, false), context);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_FIRST.ordinal()) {
            return new FirstViewHolder(inf.inflate(R.layout.layout_report_title, parent, false), context);
        } else {
            return new ContentViewHolder(inf.inflate(R.layout.layout_report_itme, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.getLayoutParams().width = width;
        if (holder instanceof FirstViewHolder) {
            TextView top = ((FirstViewHolder) holder).tv_first;
            top.setText("报告");
        } else if (holder instanceof TopViewHolder) {
            String title = "";
            if (screenReportObject != null && screenReportObject.getReportByDates() != null
                    && screenReportObject.getReportByDates().size() > position - 1
                    && screenReportObject.getReportByDates().get(position - 1) != null) {
                title = screenReportObject.getReportByDates().get(position - 1).getDate();
            }
            ((TopViewHolder) holder).tv_top.setText(title);
        } else if (holder instanceof LeftViewHolder) {
            String left = "";
            if (screenReportObject != null && screenReportObject.getField() != null && screenReportObject.getReportByDates() != null
                    && screenReportObject.getReportByDates().size() > 0
                    && (position % (screenReportObject.getReportByDates().size() + 1)) < screenReportObject.getField().size() + 1) {
                String key = screenReportObject.getField().get(position / (screenReportObject.getReportByDates().size() + 1) - 1);
                if (screenReportObject.getField_text() != null)
                    left = screenReportObject.getField_text().get(key);
            }
            ((LeftViewHolder) holder).tv_left.setText(left);
        } else if (holder instanceof ContentViewHolder) {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            ScreenReportObject.ReportItem reportItem = null;
            if (screenReportObject == null || screenReportObject.getReportByDates() == null || screenReportObject.getField() == null)
                return;
            int line = position / (screenReportObject.getReportByDates().size() + 1);
            int count = (position % (screenReportObject.getReportByDates().size() + 1)) - 1;
            String itemKey = screenReportObject.getField().get(line - 1);
            if (screenReportObject.getReportByDates().get(count) != null && screenReportObject.getReportByDates().get(count).getReportItem() != null)
                reportItem = screenReportObject.getReportByDates().get(count).getReportItem().get(itemKey);
            if (reportItem != null) {
                String tlStr = "";
                if (reportItem.getAdd() > 0)
                    tlStr = tlStr + "增加" + reportItem.getAdd();
                if (reportItem.getUpdate() > 0)
                    tlStr = tlStr + "更新" + reportItem.getUpdate();
                if (reportItem.getDelete() > 0)
                    tlStr = tlStr + "删除" + reportItem.getDelete();
                contentViewHolder.content_tv.setText(tlStr);
                if (reportItem.getStatus() == 3) {
                    contentViewHolder.content_tv.setTextColor(context.getResources().getColor(R.color.chat_line_red_color));
                    contentViewHolder.content_Img0.setVisibility(View.VISIBLE);
                    contentViewHolder.content_Img0.setImageDrawable(new ColorDrawable(context.getResources().getColor(R.color.chat_line_red_color)));
                    contentViewHolder.content_Img1.setVisibility(View.GONE);
                } else if (reportItem.getStatus() == 2) {
                    contentViewHolder.content_tv.setTextColor(context.getResources().getColor(R.color.chat_line_red_color));
                    contentViewHolder.content_Img0.setVisibility(View.VISIBLE);
                    contentViewHolder.content_Img0.setImageDrawable(new ColorDrawable(context.getResources().getColor(R.color.chat_line_blue_color)));
                    contentViewHolder.content_Img1.setVisibility(View.VISIBLE);
                    contentViewHolder.content_Img1.setImageDrawable(new ColorDrawable(context.getResources().getColor(R.color.chat_line_red_color)));
                    if (reportItem.getPercent() > 0 && reportItem.getPercent() < 1) {
                        double scale = (1 - reportItem.getPercent()) / reportItem.getPercent();
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) contentViewHolder.content_Img1.getLayoutParams();
                        layoutParams.weight = (float) scale;
                    }
                } else if (reportItem.getStatus() == 1) {
                    contentViewHolder.content_tv.setTextColor(context.getResources().getColor(R.color.white));
                    contentViewHolder.content_Img0.setVisibility(View.VISIBLE);
                    contentViewHolder.content_Img0.setImageDrawable(new ColorDrawable(context.getResources().getColor(R.color.chat_line_blue_color)));
                    contentViewHolder.content_Img1.setVisibility(View.GONE);
                } else {
                    contentViewHolder.content_tv.setTextColor(context.getResources().getColor(R.color.white));
                    contentViewHolder.content_Img0.setVisibility(View.VISIBLE);
                    contentViewHolder.content_Img0.setImageResource(R.drawable.report_0_shap);
                    contentViewHolder.content_Img1.setVisibility(View.GONE);
                }
            } else {

            }
        }
    }

    @Override
    public int getItemCount() {
        if (screenReportObject == null || screenReportObject.getField() == null || screenReportObject.getField().size() == 0 ||
                screenReportObject.getReportByDates() == null || screenReportObject.getReportByDates().size() == 0)
            return 0;
        return (screenReportObject.getField().size() + 1) * (screenReportObject.getReportByDates().size() + 1);
    }

    public static class FirstViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_first;

        public FirstViewHolder(View v, Context context) {
            super(v);
            tv_first = v.findViewById(R.id.item_report_title);
            tv_first.setPadding(0, CommonUtils.dip2px(context, 5), 0, CommonUtils.dip2px(context, 5));
            tv_first.setBackgroundResource(R.color.report_title_color);
        }
    }

    public static class LeftViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_left;

        public LeftViewHolder(View v) {
            super(v);
            tv_left = v.findViewById(R.id.item_report_title);
        }
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_top;

        public TopViewHolder(View v, Context context) {
            super(v);
            tv_top = v.findViewById(R.id.item_report_title);
            tv_top.setPadding(0, CommonUtils.dip2px(context, 5), 0, CommonUtils.dip2px(context, 5));
            tv_top.setBackgroundResource(R.color.report_title_color);
        }
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        public TextView content_tv;
        public ImageView content_Img0, content_Img1;

        public ContentViewHolder(View v) {
            super(v);
            content_tv = v.findViewById(R.id.item_report_tv);
            content_Img0 = v.findViewById(R.id.item_report_img_0);
            content_Img1 = v.findViewById(R.id.item_report_img_1);
        }
    }

}
