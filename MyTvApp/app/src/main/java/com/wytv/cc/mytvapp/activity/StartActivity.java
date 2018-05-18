package com.wytv.cc.mytvapp.activity;

import android.content.Intent;

import com.wytv.cc.mytvapp.MainActivity;
import com.wytv.cc.mytvapp.R;

public class StartActivity extends ComonActivity {

    @Override
    protected int getContentViewID() {
        return R.layout.activity_start;
    }

    private void gotoHome(){
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

}
