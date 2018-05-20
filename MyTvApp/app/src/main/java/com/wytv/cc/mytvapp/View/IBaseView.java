package com.wytv.cc.mytvapp.View;

import android.content.Context;

public interface IBaseView {
    public void init(Context context);
    public void refresh(long currentTime);
    public void loadData();

}
