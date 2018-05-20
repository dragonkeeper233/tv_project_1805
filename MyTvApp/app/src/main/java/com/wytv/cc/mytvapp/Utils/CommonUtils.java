package com.wytv.cc.mytvapp.Utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static boolean  isTv(Context context){
       String TAG = "DeviceTypeRuntimeCheck";
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;

    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static void changeDensity(Context context,float desiredDensity) {
        //desiredDensity : ldpi = 0.75 (120dpi) , mdpi = 1 (160dpi), hdpi = 1.5 (240dpi), xhdpi = 2.0 (320dpi)
        DisplayMetrics metrics =context. getResources().getDisplayMetrics();

        metrics.density = desiredDensity;
        metrics.xdpi = desiredDensity * 160;
        metrics.ydpi = desiredDensity * 160;
        metrics.densityDpi = (int) (desiredDensity * 160);

        context.getResources().updateConfiguration(null, null);
    }

    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
