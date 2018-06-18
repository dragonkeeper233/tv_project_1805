package com.wytv.cc.mytvapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.wytv.cc.mytvapp.Object.DialogFileObject;
import com.wytv.cc.mytvapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HomeDialog extends Dialog {
    private TextView tvTitle;
    private ListView listView;
    private ListView rightLv;

    public DialogFileObject getDialogFileObject() {
        return dialogFileObject;
    }

    public void setDialogFileObject(DialogFileObject dialogFileObject) {
        this.dialogFileObject = dialogFileObject;
    }

    private DialogFileObject dialogFileObject;

    public HomeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_home);

        tvTitle = (TextView) findViewById(R.id.dialog_title);
        listView = findViewById(R.id.dialog_content_lv);
        listView.setFocusable(true);
        listView.setItemsCanFocus(true);
        rightLv = findViewById(R.id.dialog_content_rv);
        rightLv.setFocusable(true);
        rightLv.setItemsCanFocus(false);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.7);
        lp.height = (int) (d.heightPixels * 0.6);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        updateData();
    }

    public void updateData() {
        if (dialogFileObject == null)
            return;
        if (tvTitle != null) {
            tvTitle.setText(dialogFileObject.getTitle());
        }
        String firstKey = "";
        if (listView != null) {
            if (dialogFileObject.getField() == null) {
                listView.setVisibility(View.GONE);
            } else {
                listView.setVisibility(View.VISIBLE);
                if (getContext() != null) {
                    ArrayList<String> result = toArray(dialogFileObject.getField());
                    if (result != null && result.size() > 0)
                        firstKey = result.get(0);
                    DialogLeftListAdapter dialogLeftListAdapter = new DialogLeftListAdapter(dialogFileObject.getField(),
                            result, getContext());
                    dialogLeftListAdapter.setOnMySelectedListener(new DialogLeftListAdapter.OnMySelectedListener() {
                        @Override
                        public void onSelect(String key, int position) {
                            updateRecycle(TextUtils.isEmpty(key) ? "default" : key);
                        }
                    });
                    listView.setAdapter(dialogLeftListAdapter);
                }
            }
        }
        updateRecycle(TextUtils.isEmpty(firstKey) ? "default" : firstKey);
    }

    private void updateRecycle(String key) {
        if (TextUtils.isEmpty(key))
            return;
        if (rightLv == null)
            return;
        if (getContext() == null)
            return;
        if (dialogFileObject.getContent() == null)
            return;
        DialogFileObject.Item item = dialogFileObject.getContent().get(key);
        if (item == null || item.getData() == null || item.getWidth() == null)
            return;
        rightLv.setAdapter(new DialogRightListAdapter(item, key, getContext()));
    }

    private ArrayList<String> toArray(final HashMap<String, String> items) {
        if (items == null || items.size() == 0)
            return null;
        ArrayList<String> result = new ArrayList<>();
        Iterator it = items.entrySet().iterator();
        String key;
        while (it.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            key = (String) entry.getKey();
            result.add(key);
        }
        return result;
    }
}
