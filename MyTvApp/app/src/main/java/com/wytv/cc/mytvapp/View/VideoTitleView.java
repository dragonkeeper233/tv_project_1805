package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.PhotoBaseObject;
import com.wytv.cc.mytvapp.Object.VideoBaseObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.activity.PhotoActivity;
import com.wytv.cc.mytvapp.activity.VideoActivity;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class VideoTitleView extends BasetitleView implements IBaseView {

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
        return new String[]{getResources().getString(R.string.video_count), getResources().getString(R.string.today_count),
                getResources().getString(R.string.yesteday_count), getResources().getString(R.string.week_count),
                getResources().getString(R.string.month_count)};
    }

    public VideoTitleView(Context context) {
        super(context);
        init(context);
    }

    public VideoTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        titleTv.setText(R.string.video_title);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getVideoBase(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_video_base) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                VideoBaseObject result = VideoBaseObject.getObj(reson);
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
        VideoBaseObject videoBaseObject = (VideoBaseObject) obj;
        if (videoBaseObject == null)
            return;
        setTitleContent(videoBaseObject.getVideo_num() + "", videoBaseObject.getToday() + "",
                videoBaseObject.getYesterday() + "", videoBaseObject.getWeek() + "", videoBaseObject.getMonth() + "");
        if (activity instanceof VideoActivity)
            ((VideoActivity) activity).setPhotoTimeTvText(videoBaseObject.getTime());
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }
}
