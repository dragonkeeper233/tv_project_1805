package com.wytv.cc.mytvapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.CommonUtils;

public class StartActivity extends ComonActivity {

    @Override
    protected int getContentViewID() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gotoHome();
    }

    private void gotoHome(){
        Intent intent = new Intent();
        intent.setClass(this, MyMainActivity.class);
        startActivity(intent);
        finish();
    }

}
