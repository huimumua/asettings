package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

public class MyListView extends ListView {

    private static final String TAG = "MyListView";

    private long starTime, endTime;
    private boolean isFirstDownEvent = true;//用于标记第一次onKeyDown事件的时间

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: ");
        if (isFirstDownEvent) {
            isFirstDownEvent = false;
            starTime = System.currentTimeMillis();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        endTime = System.currentTimeMillis();
        Log.d(TAG, "onKeyUp: " + (endTime - starTime));
        isFirstDownEvent = true;
        return (endTime - starTime) > 500 || super.onKeyUp(keyCode, event);
    }
}
