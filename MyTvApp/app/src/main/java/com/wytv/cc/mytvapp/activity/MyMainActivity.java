package com.wytv.cc.mytvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.wytv.cc.mytvapp.MyApp;
import com.wytv.cc.mytvapp.Object.DialogFileObject;
import com.wytv.cc.mytvapp.R;

import com.wytv.cc.mytvapp.Utils.CommonUtils;
import com.wytv.cc.mytvapp.Utils.MYSharePreference;
import com.wytv.cc.mytvapp.View.HomeDangerView;
import com.wytv.cc.mytvapp.View.HomeDatabaseView;
import com.wytv.cc.mytvapp.View.HomeDrawView;
import com.wytv.cc.mytvapp.View.HomeReportView;
import com.wytv.cc.mytvapp.View.HomeServerView;
import com.wytv.cc.mytvapp.View.HometitleView;
import com.wytv.cc.mytvapp.dialog.HomeDialog;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class MyMainActivity extends ComonActivity {

    private HometitleView hometitleView;
    private HomeServerView homeServerView;
    private HomeDrawView homeDrawView;
    private HomeDatabaseView homeDatabaseView;
    private HomeDangerView homeDangerView;

    private HomeReportView homeReportView;
    private LinearLayout second_ly ;

    @Override
    protected long getMillisInFutureTime() {
        return MYSharePreference.getInstance().getHomeTimeSp();
    }

    @Override
    protected int getViewCount() {
        return 6;
    }


    @Override
    protected int getContentViewID() {
        return R.layout.activity_main_new;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        second_ly = (LinearLayout)findViewById(R.id.second_ly) ;
//        second_ly.getLayoutParams().height =(int) (CommonUtils.getScreenHeight(this)/3.5);
        hometitleView = (HometitleView) findViewById(R.id.screen_title);
        hometitleView.activity = this;
        homeServerView = (HomeServerView) findViewById(R.id.screen_sever);
        homeServerView.activity = this;
        homeDrawView = (HomeDrawView) findViewById(R.id.screen_monitor);
        homeDrawView.activity = this;

        homeDangerView = (HomeDangerView) findViewById(R.id.screen_danger);
        homeDangerView.activity = this;

        homeDatabaseView = (HomeDatabaseView) findViewById(R.id.screen_database);
        homeDatabaseView.activity = this;
        homeReportView = (HomeReportView) findViewById(R.id.screen_report);
        homeReportView.activity = this;
        setFirstFocus(getIntent());
        refresh();
    }


    @Override
    public void refreshByTime(long time) {
        if (hometitleView != null)
            hometitleView.refresh(time);
        if (homeServerView != null)
            homeServerView.refresh(time);
        if (homeDrawView != null)
            homeDrawView.refresh(time);
        if (homeDangerView != null)
            homeDangerView.refresh(time);
        if (homeDatabaseView != null)
            homeDatabaseView.refresh(time);
        if (homeReportView != null)
            homeReportView.refresh(time);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setFirstFocus(intent);
    }
    private void setFirstFocus(Intent intent){
        String name = intent.getStringExtra("name");
        if ("toRight".equals(name)) {
            if (hometitleView != null)
                hometitleView.setFocus(false);
        } else if ("toLeft".equals(name)) {
            if (hometitleView != null)
                hometitleView.setFocus(true);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (homeServerView != null) {
            homeServerView.setCountDownTimeTv((int) millisUntilFinished / 1000);
        }
    }

    @Override
    public void toRight() {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("name", "toRight");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void toLeft() {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("name", "toLeft");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Intent intent = new Intent(this, TestEditActivity.class);
            startActivity(intent);
            return false;
        } else if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            MyApp.getInstance().clearActivity();
            android.os.Process.killProcess(android.os.Process.myPid());//获取PID
            System.exit(0);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public static final int DATA_TYPE_FILE = 3;
    public static final int DATA_TYPE_FILE_MORE = 6;
    public static final int DATA_TYPE_REPORT = 4;
    public static final int DATA_TYPE_DATABASE = 5;
    public static final int DATA_TYPE_DATABASE_MORE = 7;
    public static final int DATA_TYPE_REPORT_MORE = 8;

    public void showMyDialog( final int dataType, String type,final String id) {
        MyHttp.MyHttpCallback callback = new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {

            }

            @Override
            public void onSuccess(String result) {
                DialogFileObject dialogFileObject = DialogFileObject.getObj(result);
                if (dialogFileObject != null) {
                    if (TextUtils.isEmpty(dialogFileObject.getTitle())) {
                        if (dataType == DATA_TYPE_DATABASE_MORE) {
                            dialogFileObject.setTitle("数据库更多");
                        } else if (dataType == DATA_TYPE_FILE_MORE) {
                            dialogFileObject.setTitle("文件更多");
                        } else if (dataType == DATA_TYPE_REPORT_MORE) {
                            dialogFileObject.setTitle("报告更多-" + id);
                        }
                    }
                    Message message = Message.obtain();
                    message.what = DIALOG_MESSAGE_SUCCESS;
                    message.obj = dialogFileObject;
                    handler.sendMessage(message);
                }
            }
        };
        if (dataType == DATA_TYPE_FILE) {
            MyHttpInterfae.getDialogFile(callback, id);
        } else if (dataType == DATA_TYPE_REPORT) {
            MyHttpInterfae.getDialogReport(callback, id, type);
        } else if (dataType == DATA_TYPE_DATABASE) {
            MyHttpInterfae.getDialogDatabase(callback, id);
        } else if (dataType == DATA_TYPE_DATABASE_MORE) {
            MyHttpInterfae.getDialogDatabaseMore(callback);
        } else if (dataType == DATA_TYPE_FILE_MORE) {
            MyHttpInterfae.getDialogFileMore(callback);
        }else if (dataType == DATA_TYPE_REPORT_MORE){
            MyHttpInterfae.getDialogReportMore(callback, type);
        }
    }

    HomeDialog homeDialog;

    private static int DIALOG_MESSAGE_SUCCESS = 3232;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DIALOG_MESSAGE_SUCCESS) {
                DialogFileObject dialogFileObject = (DialogFileObject) msg.obj;
                if (homeDialog == null) {
                    homeDialog = new HomeDialog(MyMainActivity.this);
                } else {
                    homeDialog.dismiss();
                    homeDialog.reset();
                }
                homeDialog.setDialogFileObject(dialogFileObject);
                homeDialog.show();
            }
        }
    };

}
