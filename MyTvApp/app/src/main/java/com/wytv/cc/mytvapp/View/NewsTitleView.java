package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toast;

import com.wytv.cc.mytvapp.Object.NewsBaseObject;
import com.wytv.cc.mytvapp.Object.VideoBaseObject;
import com.wytv.cc.mytvapp.R;
import com.wytv.cc.mytvapp.activity.VideoActivity;
import com.wytv.cc.mytvapp.http.MyHttp;
import com.wytv.cc.mytvapp.http.MyHttpInterfae;

public class NewsTitleView extends BasetitleView implements IBaseView {

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public int[] getTitleDrawable() {
        return new int[]{R.drawable.title_19_icon, R.drawable.title_19_icon,
                R.drawable.title_19_icon, R.drawable.title_19_icon, R.drawable.title_19_icon
        };
    }

    @Override
    public String[] getContentTitle() {
        return new String[]{getResources().getString(R.string.today_count),
                getResources().getString(R.string.yesteday_count), getResources().getString(R.string.week_count),
                getResources().getString(R.string.month_count), getResources().getString(R.string.all_count)};
    }

    public NewsTitleView(Context context) {
        super(context);
        init(context);
    }

    public NewsTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewsTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        titleTv.setText(R.string.news_title);
    }

    @Override
    public void refresh(final long currentTime) {
        MyHttpInterfae.getNewsBase(new MyHttp.MyHttpCallback() {
            @Override
            public void onFailure(int code, String reson) {
                String sp = TextUtils.isEmpty(reson) ? getResources().getString(R.string.toast_error_news_base) : reson;
                sendFailedMessage(sp, currentTime);
            }

            @Override
            public void onSuccess(String reson) {
                NewsBaseObject result = NewsBaseObject.getObj(reson);
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
        NewsBaseObject newsBaseObject = (NewsBaseObject) obj;
        if (newsBaseObject == null)
            return;
        setTitleContent(newsBaseObject.getToday() + "", newsBaseObject.getYesterday() + "",
                newsBaseObject.getWeek() + "", newsBaseObject.getMonth() + "", newsBaseObject.getTotal() + "");
        alreadyRefresh(currentTime);
    }

    @Override
    public void handleFailed(String str, long currentTime) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        alreadyRefresh(currentTime);
    }
}
