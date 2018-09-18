package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.wytv.cc.mytvapp.Object.NewsContentObject;
import com.wytv.cc.mytvapp.R;

import java.util.LinkedHashMap;
import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {
    private List<NewsContentObject.NewsObject> newsObjects;
    private LinkedHashMap<String, NewsContentObject.NewsDate> dateHashMap;
    private Context context;
    private LayoutInflater inf;
    private int currentShow;
    private RecyclerViewTV recyclerViewTV;

    public NewsItemAdapter(List<NewsContentObject.NewsObject> newsObjects,
                           LinkedHashMap<String, NewsContentObject.NewsDate> dateHashMap, Context context, RecyclerViewTV recyclerViewTV) {
        this.dateHashMap = dateHashMap;
        this.newsObjects = newsObjects;
        this.context = context;
        this.recyclerViewTV = recyclerViewTV;
        inf = LayoutInflater.from(context);
    }

    public void setCurrentShow(int show) {
        currentShow = show;
        if (recyclerViewTV.getScrollState() == RecyclerView.SCROLL_STATE_IDLE && (recyclerViewTV.isComputingLayout() == false)) {
            notifyDataSetChanged();
        }
    }

    public int getCurrentShow() {
        return currentShow;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateTv, titleIndexTv, tltleTv, titleDateTv, contentTv;
        ImageView dateDivide;
        RelativeLayout contentLy;

        //在布局中找到所含有的UI组件
        public ViewHolder(View itemView) {
            super(itemView);
            dateTv = (TextView) itemView.findViewById(R.id.date_text);
            titleIndexTv = (TextView) itemView.findViewById(R.id.title_index);
            tltleTv = (TextView) itemView.findViewById(R.id.title_content);
            titleDateTv = (TextView) itemView.findViewById(R.id.title_date);
            contentTv = (TextView) itemView.findViewById(R.id.content);
            dateDivide = (ImageView) itemView.findViewById(R.id.date_divide);
            contentLy = itemView.findViewById(R.id.content_ly);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inf.inflate(R.layout.layout_news_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (position == currentShow) {
            holder.contentTv.setVisibility(View.VISIBLE);
        } else {
            holder.contentTv.setVisibility(View.GONE);
        }
        NewsContentObject.NewsObject newsObject = newsObjects.get(position);
        if (newsObject != null) {
            holder.titleIndexTv.setText(newsObject.getIndex() + "");
            holder.tltleTv.setText(newsObject.getTitle());
            holder.titleDateTv.setText(newsObject.getDate());
            holder.contentTv.setText(newsObject.getDescription());
            if (newsObject.getLog_type().equals(NewsContentObject.NewsObject.TYPE_UPDATE)) {
                holder.contentLy.setBackgroundResource(R.color.news_content_yellow);
            } else if (newsObject.getLog_type().equals(NewsContentObject.NewsObject.TYPE_DELETE)) {
                holder.contentLy.setBackgroundResource(R.color.news_content_red);
            } else {
                holder.contentLy.setBackgroundResource(R.color.news_banner_blue);
            }
        } else {
            holder.titleIndexTv.setText("");
            holder.tltleTv.setText("");
            holder.titleDateTv.setText("");
            holder.contentTv.setText("");
            holder.contentLy.setBackgroundResource(R.color.news_banner_blue);
        }
        NewsContentObject.NewsDate newsDate = null;
        if (position != 0) {
            NewsContentObject.NewsObject lastObj = newsObjects.get(position - 1);
            if (lastObj != null) {
                String lastDate = lastObj.getDate();
                if (!TextUtils.isEmpty(newsObject.getDate()) &&
                        !newsObject.getDate().equals(lastDate)) {
                    newsDate = dateHashMap.get(newsObject.getDate());
                }
            }
        } else {
            newsDate = dateHashMap.get(newsObject.getDate());
        }
        if (newsDate != null) {
            holder.dateTv.setVisibility(View.VISIBLE);
            holder.dateDivide.setVisibility(View.VISIBLE);
            holder.dateTv.setText(newsObject.getDate()
                    + "（" + "共计" + newsDate.getTotal() + "条，新增" +
                    newsDate.getInsert() + "条，编辑" + newsDate.getUpdate() + "条" + "）");
        } else {
            holder.dateTv.setText("");
            holder.dateTv.setVisibility(View.GONE);
            holder.dateDivide.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return newsObjects == null ? 0 : newsObjects.size();
    }
}
