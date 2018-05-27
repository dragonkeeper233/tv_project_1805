package com.wytv.cc.mytvapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wytv.cc.mytvapp.R;

public class StartActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        gotoHome();
    }

    private void gotoHome() {
        Intent intent = new Intent();
        intent.setClass(this, VideoActivity.class);
        startActivity(intent);
        finish();
    }


}
