package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.ScreenBaseObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class HometitleView extends BaseView implements IBaseView, View.OnClickListener {

    private TitleItemView packageTv, packCountTv, txtTv, zhiwenTv;
    private Button refreshBtn, leftBtn, rightBtn;

    public HometitleView(Context context) {
        super(context);
        init(context);
    }

    public HometitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HometitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        View.inflate(context, R.layout.layout_home_title, this);
        packageTv = (TitleItemView) findViewById(R.id.title_package);
        packageTv.setImage(R.drawable.title_wenjian);
        packageTv.setTitle(getResources().getString(R.string.title_package));
        packCountTv = (TitleItemView) findViewById(R.id.title_package_cout);
        packCountTv.setTitle(getResources().getString(R.string.title_pack_count));
        packCountTv.setImage(R.drawable.title_wenjianshu);
        txtTv = (TitleItemView) findViewById(R.id.title_content_cout);
        txtTv.setTitle(getResources().getString(R.string.title_pack_change));
        txtTv.setImage(R.drawable.title_19_icon);
        zhiwenTv = (TitleItemView) findViewById(R.id.title_zhiwen_cout);
        txtTv.setImage(R.drawable.title_icon_14);
        zhiwenTv.setTitle(getResources().getString(R.string.title_zhiwen));
        refreshBtn = (Button) findViewById(R.id.title_refresh_btn);
        refreshBtn.setOnClickListener(this);
        leftBtn = (Button) findViewById(R.id.title_left_btn);
        leftBtn.setOnClickListener(this);
        rightBtn = (Button) findViewById(R.id.title_right_btn);
        rightBtn.setOnClickListener(this);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getScreenBase(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_screen_base) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                ScreenBaseObject result = ScreenBaseObject.getObj(reson);
                if (result != null) {
                    sendSuccessMessage(result, currentTime);
                }
            }
        });

    }

    @Override
    public void loadData() {

    }

    //    dir_num：文件夹数量
//    file_num：文件数量
//    article_num：文章数量
//    hash_num：指纹数量
    @Override
    public void handleSuccess(Object obj, long currentTime) {
        ScreenBaseObject screenBaseObject = (ScreenBaseObject) obj;
        if (screenBaseObject == null)
            return;
        if (packageTv != null)
            packageTv.setCount(screenBaseObject.getDir_num() + "");
        if (packCountTv != null)
            packCountTv.setCount(screenBaseObject.getFile_num() + "");
        if (txtTv != null)
            txtTv.setCount(screenBaseObject.getArticle_num() + "");
        if (zhiwenTv != null)
            zhiwenTv.setCount(screenBaseObject.getHash_num() + "");
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_refresh_btn:
                if (activity != null)
                    activity.refresh();
                break;
        }
    }
}
