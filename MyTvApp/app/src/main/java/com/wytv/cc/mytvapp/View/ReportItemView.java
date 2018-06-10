package com.wytv.cc.mytvapp.View;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ReportItemView extends View {

    private float scale;

    public ReportItemView(Context context) {
        super(context);
    }

    public ReportItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
