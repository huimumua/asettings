package com.askey.dvr.cdr7010.setting.base;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.controller.TtsServer;

public class BaseKeyAudioActivity extends BaseActivity{

    private TtsServer ttsServer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ttsServer = TtsServer.getInstance();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                try {
                    ttsServer.startTtsMenuItemClick();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                try {
                    ttsServer.startTtsMenuCursorMove();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                try {
                    ttsServer.startTtsMenuItemBack();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
