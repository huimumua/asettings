package com.askey.dvr.cdr7010.setting.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.askey.dvr.cdr7010.dashcam.ITTSAidlInterface;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.util.Logg;


public class TtsServer {
    private static final String LOG_TAG = "TtsServer";

    private static TtsServer mTtsServer;
    private ITTSAidlInterface mInterface;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logg.d(LOG_TAG, "onServiceConnected: ");
            mInterface = ITTSAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logg.d(LOG_TAG, "onServiceDisconnected: ");
            mInterface = null;
        }
    };

    private TtsServer() { }

    public static TtsServer getInstance(){
        if(mTtsServer == null)
            mTtsServer = new TtsServer();

        return mTtsServer;
    }

    public boolean bindTtsService() {
        Context appContext = SettingApplication.getAppContext();
        Intent bindIntent = new Intent();
        bindIntent.setAction("service.TTSSevice");
        bindIntent.setPackage("com.askey.dvr.cdr7010.dashcam");
        return appContext.bindService(bindIntent, mConn, Context.BIND_AUTO_CREATE);
    }

    public void unBindTtsService() {
        Context appContext = SettingApplication.getAppContext();
        appContext.unbindService(mConn);
    }

    public void startTtsMenuCursorMove() throws RemoteException {
        Logg.d(LOG_TAG, "startTtsMenuCursorMove：" );
        if (mInterface == null) {
            throw new RemoteException("No TTSService service.");
        }
        try {
            mInterface.ttsMenuCursorMove();
        } catch (Exception e) {
            Logg.e(LOG_TAG, "startTtsMenuCursorMove " + e.getMessage());
        }
    }

    public void startTtsMenuItemClick() throws RemoteException {
        Logg.d(LOG_TAG, "startTtsMenuItemClick：" );
        if (mInterface == null) {
            throw new RemoteException("No TTSService service.");
        }
        try {
            mInterface.ttsMenuItemClick();
        } catch (Exception e) {
            Logg.e(LOG_TAG, "startTtsMenuItemClick " + e.getMessage());
        }
    }

    public void startTtsMenuItemBack() throws RemoteException {
        Logg.d(LOG_TAG, "startTtsMenuItemBack：" );
        if (mInterface == null) {
            throw new RemoteException("No TTSService service.");
        }
        try {
            mInterface.ttsMenuItemBack();
        } catch (Exception e) {
            Logg.e(LOG_TAG, "startTtsMenuItemBack " + e.getMessage());
        }
    }
}
