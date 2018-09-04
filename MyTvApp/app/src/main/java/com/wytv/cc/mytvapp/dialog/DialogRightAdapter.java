package com.wytv.cc.mytvapp.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytv.cc.mytvapp.Object.DialogFileObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

import java.util.ArrayList;

public class DialogRightAdapter extends RecyclerView.Adapter<DialogRightAdapter.ViewHolder> {
    private DialogFileObject.Item items;
    private String key;
    private Context context;
    private int mycount;


    public DialogRightAdapter(DialogFileObject.Item items, String keys, Context context) {
        this.items = items;
        this.key = key;
        this.context = context;
        if (items != null && items.getWidth() != null) {
            mycount = items.getWidth().length;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout root;
        ImageView leftDv, bottomDv;
        ArrayList<TextView> tvs = new ArrayList<>();

        //在布局中找到所含有的UI组件
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.right_item_content_ly);
            leftDv = itemView.findViewById(R.id.divide_left);
            bottomDv = itemView.findViewById(R.id.divide_bottom);
            for (int i = 0; i < mycount; i++) {
                TextView textView = new TextView(context);
                textView.setPadding(CommonUtils.dip2px(context, 2), CommonUtils.dip2px(context, 5), 0, CommonUtils.dip2px(context, 5));
                textView.setTextColor(context.getResources().getColor(R.color.white));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                textView.setGravity(Gravity.LEFT);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = items.getWidth()[i];
                root.addView(textView, layoutParams);
                tvs.add(textView);
                ImageView divide = new ImageView(context);
                divide.setBackgroundResource(R.color.news_content_line_color);
                LinearLayout.LayoutParams divideLayoutParams = new LinearLayout.LayoutParams(CommonUtils.dip2px(context, 1),
                        LinearLayout.LayoutParams.MATCH_PARENT);
                root.addView(divide, divideLayoutParams);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_dialog_right_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
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
        if (position == getItemCount() - 1) {
            holder.bottomDv.setVisibility(View.VISIBLE);
        } else {
            holder.bottomDv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items == null || items.getData() == null ? 0 : items.getData().size();
    }

}
