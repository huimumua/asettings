package com.askey.dvr.cdr7010.setting.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by skysoft on 2018/4/12.
 */

public class Utils {

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 150) {       //150毫秒內按鈕無效，這樣可以控制快速點擊，自己調整頻率
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
