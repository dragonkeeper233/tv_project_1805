package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wytv.cc.mytvapp.Object.DatabaseObject;
import com.wytv.cc.mytvapp.Object.DengerObject;
import com.wytv.cc.mytvapp.R;

public class DataBaseItemView {
    private View view;
    private Context context;
    private TextView titleTv, contentTv, timeTv;

    public View init(Context context) {
        this.context = context;
        view = View.inflate(context, R.layout.layout_home_database_item, null);
        titleTv = view.findViewById(R.id.database_item_title);
        contentTv = view.findViewById(R.id.database_item_content);
        timeTv = view.findViewById(R.id.database_item_time);
        return view;
    }

    public void setUI(Object data, String time, String last) {
        if (data == null)
            return;
        setTime(time, last);
        if (data instanceof DatabaseObject.Data) {
            DatabaseObject.Data dbData = (DatabaseObject.Data) data;
            setTitle(dbData.getTable_name());
            setContent(dbData.getInsert(), dbData.getUpdate(), dbData.getDelete());
        } else if (data instanceof DengerObject.DangerData) {
            DengerObject.DangerData dangerData = (DengerObject.DangerData) data;
            setTitle(dangerData.getName());
            setContent(dangerData.getAdd() + "", dangerData.getUpdate() + "", dangerData.getDelete() + "");
        }
    }

    public void setTitle(String str) {
        if (titleTv != null) {
            titleTv.setText(str);
        }
    }

    public void setContent(String add, String change, String delete) {
        if (contentTv != null) {
            String result = "";
            if (!TextUtils.isEmpty(add))
                result = " " + context.getResources().getString(R.string.add) + add;
            if (!TextUtils.isEmpty(change))
                result = result + " " + context.getResources().getString(R.string.update) + change;
            if (!TextUtils.isEmpty(delete))
                result = result + " " + context.getResources().getString(R.string.delete) + delete;
            contentTv.setText(result);
        }
    }

    public void setTime(String time, String last) {
        if (timeTv != null) {
            timeTv.setText(time + " " + last);
        }
    }
}
