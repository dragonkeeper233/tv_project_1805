package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.PhotoBaseObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.activity.PhotoActivity;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class PhotoTitleView extends BasetitleView implements IBaseView {

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public int[] getTitleDrawable() {
        return null;
    }

    @Override
    public String[] getContentTitle() {
        return new String[]{getResources().getString(R.string.photo_count), getResources().getString(R.string.today_count),
                getResources().getString(R.string.yesteday_count), getResources().getString(R.string.week_count),
                getResources().getString(R.string.month_count)};
    }

    public PhotoTitleView(Context context) {
        super(context);
        init(context);
    }

    public PhotoTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PhotoTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        titleTv.setText(R.string.photo_title);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getPhotoBase(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_photo_base) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                PhotoBaseObject result = PhotoBaseObject.getObj(reson);
                if (result != null) {
                    sendSuccessMessage(result, currentTime);
                } else {
                    sendFailedMessage("数据解析失败", currentTime);
                }
            }
        });

    }

    @Override
    public void handleSuccess(Object obj, long currentTime) {
        PhotoBaseObject photoBaseObject = (PhotoBaseObject) obj;
        if (photoBaseObject == null)
            return;
        setTitleContent(photoBaseObject.getImage_num() + "", photoBaseObject.getToday() + "",
                photoBaseObject.getYesterday() + "", photoBaseObject.getWeek() + "", photoBaseObject.getMonth() + "");
        if (activity instanceof PhotoActivity)
            ((PhotoActivity) activity).setPhotoTimeTvText(photoBaseObject.getTime());
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }
}
