package com.wytv.cc.mytvapp.Utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static boolean isTv(Context context) {
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

    public static void changeDensity(Context context, float desiredDensity) {
        //desiredDensity : ldpi = 0.75 (120dpi) , mdpi = 1 (160dpi), hdpi = 1.5 (240dpi), xhdpi = 2.0 (320dpi)
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();

        metrics.density = desiredDensity;
        metrics.xdpi = desiredDensity * 160;
        metrics.ydpi = desiredDensity * 160;
        metrics.densityDpi = (int) (desiredDensity * 160);

        context.getResources().updateConfiguration(null, null);
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
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
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 得到屏幕宽度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenWidth(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth();
    }

    /**
     * 得到屏幕高度
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getHeight();
    }

    public static long getTimeLong(String time, boolean hasSS) {
        SimpleDateFormat ssformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat nossformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date currentDate = hasSS ? ssformat.parse(time) : nossformat.parse(time);
            return currentDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 读取assets下的txt文件，返回utf-8 String
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    public static String readAssetsTxt(Context context,String fileName){
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName+".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }
}
