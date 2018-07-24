package com.askey.dvr.cdr7010.setting.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

import com.askey.dvr.cdr7010.filemanagement.IFileManagerAidlInterface;
import com.askey.dvr.cdr7010.filemanagement.SdcardInfo;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.util.Logg;

import java.util.List;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/18 16:58
 * 修改人：skysoft
 * 修改时间：2018/4/18 16:58
 * 修改备注：
 */
public class FileManager {

    private static final String LOG_TAG = "FileManage";

    private static FileManager mFileManager;
    private IFileManagerAidlInterface mInterface;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logg.d(LOG_TAG, "onServiceConnected: ");
            mInterface = IFileManagerAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logg.d(LOG_TAG, "onServiceDisconnected: ");
            mInterface = null;
        }
    };

    private FileManager() { }

    public static FileManager getInstance(){
        if(mFileManager == null)
            mFileManager = new FileManager();

        return mFileManager;
    }

    public List<SdcardInfo> getSdcardInfo() {
        if(!waitFileServiceConnected())
            return null;
        try {
            return mInterface.getSdcardInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSdcardStatus() {
        if(!waitFileServiceConnected())
            return 2;
        try {
            return mInterface.checkSdcardAvailable();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 2;
    }

    public boolean bindFileManageService() {
        Context appContext = SettingApplication.getContext();
        Intent bindIntent = new Intent();
        bindIntent.setAction("com.askey.filemanagerservice.action");
        bindIntent.setPackage("com.askey.dvr.cdr7010.filemanagement");
        return appContext.bindService(bindIntent, mConn, Context.BIND_AUTO_CREATE);
    }

    public void unBindFileManageService() {
        Context appContext = SettingApplication.getContext();
        appContext.unbindService(mConn);
    }

    private boolean waitFileServiceConnected(){
        if(mInterface != null)
            return true;
        if (bindFileManageService()){
            Logg.d(LOG_TAG, "waitFileServiceConnected: start");
            long frontTime = SystemClock.elapsedRealtime();
            while(mInterface == null && SystemClock.elapsedRealtime() - frontTime <= 2000){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Logg.d(LOG_TAG, "waitFileServiceConnected: end");
            return (mInterface != null);
        }
        return false;
    }

}
