package com.askey.dvr.cdr7010.setting.module.sdcard.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.storage.StorageManager;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:28
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:28
 * 修改备注：
 */
public class SdcardManager {

    private static final String SdcardManager = SdcardFormatAsyncTask.class.getSimpleName();

    /**
     * mPreferUsbPath是你需要格式化的外部存储路径
     * */
    public void diskFormat(Context mContext, String mPreferUsbPath) {
        ComponentName formatter = new ComponentName("android", "com.android.internal.os.storage.ExternalStorageFormatter");
        Intent intent = new Intent("com.android.internal.os.storage.FORMAT_ONLY"); // ExternalStorageFormatter.FORMAT_ONLY
        intent.setComponent(formatter);
        Parcelable sv = getStoragePath(mContext,mPreferUsbPath);
        intent.putExtra("storage_volume", sv);  // StorageVolume.EXTRA_STORAGE_VOLUME
        mContext.startService(intent);
    }

    // 获取 StorageVolume 对象
    private Parcelable getStoragePath(Context mContext,String mPreferUsbPath) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (removable && path.equals(mPreferUsbPath)) {
                    return (Parcelable) storageVolumeElement;
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
