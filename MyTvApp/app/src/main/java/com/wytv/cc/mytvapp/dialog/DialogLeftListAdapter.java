package com.wytv.cc.mytvapp.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DialogLeftListAdapter extends BaseAdapter {
    private HashMap<String, String> items;
    private ArrayList<String> keys;
    private Context context;
    private LayoutInflater inflate;

    private OnMySelectedListener onMySelectedListener;

    public OnMySelectedListener getOnMySelectedListener() {
        return onMySelectedListener;
    }

    public void setOnMySelectedListener(OnMySelectedListener onMySelectedListener) {
        this.onMySelectedListener = onMySelectedListener;
    }


    public DialogLeftListAdapter(HashMap<String, String> items, ArrayList<String> keys, Context context) {
        this.items = items;
        this.keys = keys;
        this.context = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return keys == null ? 0 : keys.size();
    }

    @Override
    public Object getItem(int position) {
        return keys == null ? null : keys.get(position);
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
            convertView = inflate.inflate(R.layout.layout_dailog_lsft_list_tiem, parent, false);
            holder.tv = convertView.findViewById(R.id.left_list_item_tv);
            convertView.setTag(holder);
        } else {
            //直接通过holder获取下面三个子控件，不必使用findviewbyid，加快了 UI 的响应速度
            holder = (ViewHolder) convertView.getTag();
        }
        final String key = keys.get(position);
        String value = null;
        if (!TextUtils.isEmpty(key) && items != null) {
            value = items.get(key);
        }
        holder.tv.setText(TextUtils.isEmpty(value) ? "" : value);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMySelectedListener != null) {
                    onMySelectedListener.onSelect(key, position);
                }
            }
        });
//        convertView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus && onMySelectedListener != null) {
//                    onMySelectedListener.onSelect(key, position);
//                }
//            }
//        });
        return convertView;
    }

    static class ViewHolder {
        TextView tv;
    }

    public interface OnMySelectedListener {
        public void onSelect(String key, int position);
    }
}
