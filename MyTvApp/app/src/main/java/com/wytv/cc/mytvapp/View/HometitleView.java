package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.ScreenBaseObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class HometitleView extends BasetitleView implements IBaseView {

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public int[] getTitleDrawable() {
        return new int[]{R.drawable.title_wenjian, R.drawable.title_wenjianshu,
                R.drawable.title_19_icon, R.drawable.title_icon_14
        };
    }

    @Override
    public String[] getContentTitle() {
        return new String[]{getResources().getString(R.string.title_package), getResources().getString(R.string.title_pack_count),
                getResources().getString(R.string.title_pack_change), getResources().getString(R.string.title_zhiwen)};
    }

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
        super.init(context);
        titleTv.setText(R.string.home_title);
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
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        });

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
        setTitleContent(screenBaseObject.getDir_num() + "", screenBaseObject.getFile_num() + "",
                screenBaseObject.getArticle_num() + "", screenBaseObject.getHash_num() + "");
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }

}
