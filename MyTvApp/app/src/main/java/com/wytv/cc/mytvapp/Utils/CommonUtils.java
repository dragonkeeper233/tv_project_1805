package com.wytv.cc.mytvapp.Utils;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;

public class CommonUtils {

    public static boolean  isTv(Context context){
       String TAG = "DeviceTypeRuntimeCheck";
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;

    }


}
