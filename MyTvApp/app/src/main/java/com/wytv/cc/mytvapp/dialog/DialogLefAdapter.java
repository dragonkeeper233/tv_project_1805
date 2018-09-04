package com.wytv.cc.mytvapp.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wytv.cc.mytvapp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DialogLefAdapter extends RecyclerView.Adapter<DialogLefAdapter.ViewHolder> {
    private LinkedHashMap<String, String> items;
    public ArrayList<String> keys;
    private Context context;
    private LayoutInflater inflate;

    public DialogLefAdapter(LinkedHashMap<String, String> items, ArrayList<String> keys, Context context) {
        this.items = items;
        this.keys = keys;
        this.context = context;
        inflate = LayoutInflater.from(context);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        //在布局中找到所含有的UI组件
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.left_list_item_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflate.inflate(R.layout.layout_dailog_lsft_list_tiem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        final String key = keys.get(position);
        String value = null;
        if (!TextUtils.isEmpty(key) && items != null) {
            value = items.get(key);
        }
        holder.tv.setText(TextUtils.isEmpty(value) ? "" : value);
    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.size();
    }
}
