package com.wytv.cc.mytvapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.wytv.cc.mytvapp.Object.DialogFileObject;
import com.wytv.cc.mytvapp.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class HomeDialog extends Dialog implements RecyclerViewTV.OnItemListener, RecyclerViewTV.OnItemClickListener {
    private TextView tvTitle;
    private RecyclerViewTV listView;
    private RecyclerViewTV rightLv;
    private ImageButton closeBtn;

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

    public void reset(){
        listView.setAdapter(null);
        rightLv.setAdapter(null);
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
        LinearLayoutManager leftLm = new LinearLayoutManager(getContext());
        listView.setLayoutManager(leftLm);
        listView.setSelectedItemAtCentered(true);
        listView.setOnItemListener(this);
        listView.setOnItemClickListener(this);
        rightLv = findViewById(R.id.dialog_content_rv);
        LinearLayoutManager rightLm = new LinearLayoutManager(getContext());
        rightLv.setLayoutManager(rightLm);
        rightLv.setSelectedItemAtCentered(true);
        closeBtn = (ImageButton) findViewById(R.id.dialog_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.9);
        lp.height = (int) (d.heightPixels * 0.7);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        updateData();
    }

    private DialogLefAdapter dialogLefAdapter;

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
                    dialogLefAdapter = new DialogLefAdapter(dialogFileObject.getField(),
                            result, getContext());
                    listView.setAdapter(dialogLefAdapter);
                    listView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (listView!=null&&listView.getChildCount()!=0){
                                listView.getChildAt(0).requestFocus();
                            }
                        }
                    });
                }
            }
        }
        updateRecycle(TextUtils.isEmpty(firstKey) ? "default" : firstKey);
    }

    private void updateRecycle(String key) {
        if (TextUtils.isEmpty(key)){
            rightLv.setAdapter(null);
            return;
        }
        if (rightLv == null){
            rightLv.setAdapter(null);
            return;
        }
        if (getContext() == null){
            rightLv.setAdapter(null);
            return;
        }
        if (dialogFileObject.getContent() == null){
            rightLv.setAdapter(null);
            return;
        }
        DialogFileObject.Item item = dialogFileObject.getContent().get(key);
        if (item == null || item.getData() == null || item.getWidth() == null) {
            rightLv.setAdapter(null);
            return;
        }
        rightLv.setAdapter(new DialogRightAdapter(item, key, getContext()));
    }

    private ArrayList<String> toArray(final LinkedHashMap<String, String> items) {
        if (items == null || items.size() == 0)
            return null;
        ArrayList<String> result = new ArrayList<>();
        Iterator it = items.entrySet().iterator();
        String key;
        while (it.hasNext()) {
            LinkedHashMap.Entry entry = (LinkedHashMap.Entry) it.next();
            key = (String) entry.getKey();
            result.add(key);
        }
        return result;
    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        itemView.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        itemView.setBackgroundResource(R.color.news_content_yellow);
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        itemView.setBackgroundResource(R.color.news_content_yellow);
    }

    @Override
    public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
        itemView.setBackgroundResource(R.color.news_content_yellow);
        if (dialogLefAdapter != null && dialogLefAdapter.keys != null && dialogLefAdapter.keys.size() > position) {
            String key = dialogLefAdapter.keys.get(position);
            updateRecycle(TextUtils.isEmpty(key) ? "default" : key);
        }
    }
}
