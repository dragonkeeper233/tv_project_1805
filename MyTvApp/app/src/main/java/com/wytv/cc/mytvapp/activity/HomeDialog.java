package com.wytv.cc.mytvapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.wytv.cc.mytvapp.Object.DialogFileObject;
import com.wytv.cc.mytvapp.R;

public class HomeDialog extends Dialog {

    private TextView tvTitle;
    private ListView listView;
    private RecyclerView recyclerView;

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
        setContentView(R.layout.layout_dialog_home);

        tvTitle = (TextView) findViewById(R.id.dialog_title);
        listView = findViewById(R.id.dialog_content_lv);
        recyclerView = findViewById(R.id.dialog_content_rv);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = getContext().getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);
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
    }
}
