package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;

import com.askey.dvr.cdr7010.setting.controller.TtsServer;
import com.askey.dvr.cdr7010.setting.util.Utils;

public class MyListView extends ListView {

    private static final String TAG = "MyListView";

    private long starTime, endTime;
    private boolean isFirstDownEvent = true;//用于标记第一次onKeyDown事件的时间
    private TtsServer ttsServer;
    private int downEventCount = 0;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSoundEffectsEnabled(false);//关闭listview按键音
        ttsServer = TtsServer.getInstance();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event.startTracking();
        Log.d(TAG, "onKeyDown: "+downEventCount);
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                try {
                    ttsServer.startTtsMenuItemClick();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (isFirstDownEvent) {
                    isFirstDownEvent = false;
                    starTime = System.currentTimeMillis();
                }
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_BACK:
                try {
                    ttsServer.startTtsMenuItemBack();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return super.onKeyDown(keyCode, event);
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_UP:
                try {
                    ttsServer.startTtsMenuCursorMove();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                if (downEventCount == 0 && isFirstDownEvent) {
                    isFirstDownEvent = false;
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
        return (endTime - starTime) > 3000 || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        downEventCount++;
        return super.onKeyLongPress(keyCode, event);
    }
}
