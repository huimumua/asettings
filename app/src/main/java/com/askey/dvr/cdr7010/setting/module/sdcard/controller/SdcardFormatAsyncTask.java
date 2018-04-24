package com.askey.dvr.cdr7010.setting.module.sdcard.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.askey.android.platform_library.DiskInfoExtend;
import com.askey.android.platform_library.PlatformLibrary;
import com.askey.android.platform_library.StorageUtils;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.util.Logg;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/19 15:04
 * 修改人：skysoft
 * 修改时间：2018/4/19 15:04
 * 修改备注：
 */
public class SdcardFormatAsyncTask extends AsyncTask<Void, Integer, Boolean> {
    private static final String LOG_TAG = SdcardFormatAsyncTask.class.getSimpleName();
    private final PartitionCallback mCallback;

    public SdcardFormatAsyncTask(PartitionCallback callback){
        this.mCallback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Context appContext = SettingApplication.getContext();
        //这里需要停止sdcard读写操作：

//        RecoderingInfoManager.getInstance(appContext).deleteAll();
//        EventInfoManager.getInstance(appContext).deleteAll();
//        PhotoInfoManager.getInstance(appContext).deltetAll();
//        Storage.onDestroy();
//        StoragePhoto.onDestroy();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        PlatformLibrary mPlatformLibrary = new  PlatformLibrary(appContext);
        StorageUtils mStorageUtils = mPlatformLibrary.getStorageManager();
        for (final DiskInfoExtend disk : mStorageUtils.getDisksExtend()) {
            Logg.d(LOG_TAG, "doInBackground: disk " + disk.getSysPath());
            if (disk.isSd()) {
                Logg.d(LOG_TAG, "doInBackground: sdcard disk, volumeCount = " + disk.getvolumeCount() + ", size = " + disk.getSize());
                try {
                    mStorageUtils.partitionPublic(disk.getId());
                    return true;
                } catch (Exception e) {
                    Logg.w(LOG_TAG, "doInBackground: format thread error. " + e.getMessage());
                }
            }
        }

        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean ready) {
        try{
            if(mCallback != null)
                mCallback.onPostExecute(ready);
        }catch (Exception e){
            Logg.e(LOG_TAG, "onPostExecute: " + e.getMessage());
        }
        super.onPostExecute(ready);
    }

    public interface PartitionCallback{
        void onPostExecute(boolean ready);
    }
}
