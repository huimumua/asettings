package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

import com.askey.dvr.cdr7010.setting.util.Utils;

public class MyListView extends ListView {

    private static final String TAG = "MyListView";

    private long starTime, endTime;
    private boolean isFirstDownEvent = true;//用于标记第一次onKeyDown事件的时间

    private int downEventCount = 0;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event.startTracking();
        Log.d(TAG, "onKeyDown: "+downEventCount);
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (isFirstDownEvent) {
                    isFirstDownEvent = false;
                    starTime = System.currentTimeMillis();
                }
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_BACK:
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                if (downEventCount == 0 && isFirstDownEvent) {
                    isFirstDownEvent = false;
//                    if (super.onKeyDown(keyCode, event)) {
//                        Log.d(TAG, "onKeyDown: true");
//                        return true;
//                    } else {
//                        Log.d(TAG, "onKeyDown: false");
//                        return false;
//                    }
                    return super.onKeyDown(keyCode, event);
                } else if (downEventCount > 1) {
                    Log.d(TAG, "onKeyDown>1");
                    if (downEventCount == 2 && !Utils.isFastDoubleClick()) {
                        Log.d(TAG, "onKeyDown==2");
                        return super.onKeyDown(keyCode, event);
                    }
                    if (downEventCount == 3) {
                        Log.d(TAG, "onKeyDown==3");
                        return super.onKeyDown(keyCode, event);
                    }
//                    if (super.onKeyDown(keyCode, event)) {
//                        Log.d(TAG, "onKeyDown>1: true");
//                        return true;
//                    } else {
//                        Log.d(TAG, "onKeyDown>1: false");
//                        return false;
//                    }
//                    return super.onKeyDown(keyCode, event);
                }
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        endTime = System.currentTimeMillis();
        Log.d(TAG, "onKeyUp: " + (endTime - starTime));
        isFirstDownEvent = true;
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return super.onKeyUp(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                downEventCount = 0;
                break;
        }
        return (endTime - starTime) > 500 || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        downEventCount++;
        return super.onKeyLongPress(keyCode, event);
    }
}
