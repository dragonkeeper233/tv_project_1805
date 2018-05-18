package com.wytv.cc.mytvapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.Utils.MYSharePreference;
import com.wytv.cc.mytvapp.http.UrlUtils;

public class TestEditActivity extends ComonActivity implements View.OnClickListener {
   private EditText tokenEt,urlEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewID());
        tokenEt = (EditText) findViewById(R.id.token_edt);
        urlEt = (EditText) findViewById(R.id.url_edt);
        findViewById(R.id.ok).setOnClickListener(this);
        urlEt.setText(UrlUtils.BASE_URL);
        tokenEt.setText(UrlUtils.TOKEN);
    }
    @Override
    protected int getContentViewID() {
        return R.layout.activity_edit_test;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ok:
                change();
                break;
        }

    }

    private void change(){
        String newUrl,newToken;
        newUrl = urlEt.getText().toString();
        newToken = tokenEt.getText().toString();
        if (!TextUtils.isEmpty(newUrl)){
            MYSharePreference.getInstance().setBaseUrl(newUrl);
            UrlUtils.BASE_URL =   MYSharePreference.getInstance().getBaseUrl();
            Toast.makeText(this, "修改成功",Toast.LENGTH_LONG).show();
        }
        if (!TextUtils.isEmpty(newToken)){
            MYSharePreference.getInstance().setToken(newToken);
            UrlUtils.TOKEN =   MYSharePreference.getInstance().getToken();
            Toast.makeText(this, "修改成功",Toast.LENGTH_LONG).show();
        }
        finish();
    }
}
