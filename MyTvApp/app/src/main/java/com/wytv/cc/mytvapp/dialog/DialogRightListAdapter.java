package com.wytv.cc.mytvapp.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytv.cc.mytvapp.Object.DialogFileObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogRightListAdapter extends BaseAdapter {
    private DialogFileObject.Item items;
    private String key;
    private Context context;
    private int mycount;


    public DialogRightListAdapter(DialogFileObject.Item items, String keys, Context context) {
        this.items = items;
        this.key = key;
        this.context = context;
        if (items != null && items.getWidth() != null) {
            mycount = items.getWidth().length;
        }
    }

    @Override
    public int getCount() {
        return items == null || items.getData() == null ? 0 : items.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return items == null || items.getData() == null ? null : items.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_right_item, parent, false);
            holder.root = convertView.findViewById(R.id.right_item_content_ly);
            holder.leftDv = convertView.findViewById(R.id.divide_left);
            holder.bottomDv = convertView.findViewById(R.id.divide_bottom);
            for (int i = 0; i < mycount; i++) {
                TextView textView = new TextView(context);
                textView.setPadding(CommonUtils.dip2px(context, 2), CommonUtils.dip2px(context, 5), 0, CommonUtils.dip2px(context, 5));
                textView.setTextColor(context.getResources().getColor(R.color.white));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                textView.setGravity(Gravity.LEFT);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = items.getWidth()[i];
                holder.root.addView(textView, layoutParams);
                holder.tvs.add(textView);
                if (items != null && items.getData() != null && items.getData().size() > position &&
                        items.getData().get(position) != null && items.getData().get(position).size() > i) {
                    textView.setText(items.getData().get(position).get(i));
                } else {
                    textView.setText("");
                }
                ImageView divide = new ImageView(context);
                divide.setBackgroundResource(R.color.news_content_line_color);
                LinearLayout.LayoutParams divideLayoutParams = new LinearLayout.LayoutParams(CommonUtils.dip2px(context, 1),
                        LinearLayout.LayoutParams.MATCH_PARENT);
                holder.root.addView(divide, divideLayoutParams);
            }
            convertView.setTag(holder);
        } else {
            //直接通过holder获取下面三个子控件，不必使用findviewbyid，加快了 UI 的响应速度
            holder = (ViewHolder) convertView.getTag();
            if (holder.tvs != null && holder.tvs.size() > 0) {
                for (int i = 0; i < mycount; i++) {
                    if (items != null && items.getData() != null && items.getData().size() > position &&
                            items.getData().get(position) != null && items.getData().get(position).size() > i) {
                        holder.tvs.get(i).setText(items.getData().get(position).get(i));
                    } else {
                        holder.tvs.get(i).setText("");
                    }
                }
            }
        }
        if (position == getCount() - 1) {
            holder.bottomDv.setVisibility(View.VISIBLE);
        } else {
            holder.bottomDv.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        LinearLayout root;
        ImageView leftDv, bottomDv;
        ArrayList<TextView> tvs = new ArrayList<>();
    }

}
