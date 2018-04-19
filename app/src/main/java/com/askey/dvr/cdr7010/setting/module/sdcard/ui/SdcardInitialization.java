package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.askey.android.platform_library.DiskInfoExtend;
import com.askey.android.platform_library.PlatformLibrary;
import com.askey.android.platform_library.StorageUtils;
import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.sdcard.controller.SdcardFormatAsyncTask;
import com.askey.dvr.cdr7010.setting.util.Logg;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/17 15:50
 * 修改人：skysoft
 * 修改时间：2018/4/17 15:50
 * 修改备注：
 */
public class SdcardInitialization extends BaseActivity implements SdcardFormatAsyncTask.PartitionCallback{

    private static final String TAG = "SdcardInitialization";
    private TextView title;
    private Button cancel, ok;
    private SdcardFormatAsyncTask sdcardFormatAsyncTask;
    private boolean doFormat = false;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_init);

        initView();


    }

    private void initView() {
        title = this.findViewById(R.id.sdcard_init_title);
        cancel = this.findViewById(R.id.sdcard_init_cancel_bt);
        ok = this.findViewById(R.id.sdcard_init_ok_bt);
        mProgressBar = this.findViewById(R.id.sdcard_init_progress);
        mProgressBar.setVisibility(View.GONE);
        cancel.setSelected(true);
        ok.setSelected(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!doFormat){
                    //这里需要停止sdcard读写操作：
                    mProgressBar.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.GONE);
                    ok.setVisibility(View.GONE);
                    title.setText(getResources().getString(R.string.sdcard_init_ongoing));
                    doFormat = true;
                    doSdcardformat();
                }else{
                    finish();
                }
            }
        });
    }

    @Override
    public void onPostExecute(boolean ready) {
        mProgressBar.setVisibility(View.GONE);
        if(ready){
            //格式化成功
            cancel.setVisibility(View.GONE);
            ok.setVisibility(View.VISIBLE);
            title.setText(getResources().getString(R.string.sdcard_init_success));
        }else{
            //格式化失败
            cancel.setVisibility(View.GONE);
            ok.setVisibility(View.VISIBLE);
            title.setText(getResources().getString(R.string.sdcard_init_fail));
        }
    }

    private void doSdcardformat() {
        PlatformLibrary mPlatformLibrary = new  PlatformLibrary(mContext);
        StorageUtils mStorageUtils = mPlatformLibrary.getStorageManager();
        for (final DiskInfoExtend disk : mStorageUtils.getDisksExtend()) {
            //Logg.d(LOG_TAG, "formatSDCard: disk " + disk.getSysPath());
            if (disk.isSd()) {
                sdcardFormatAsyncTask = new SdcardFormatAsyncTask(this);
                sdcardFormatAsyncTask.execute();
                return;
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);

        if(keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            foucusChenged();
            return true;
        }
        if(keyCode == KeyEvent.KEYCODE_DPAD_UP){
            foucusChenged();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void foucusChenged() {
        if(cancel.isSelected()){
            cancel.setSelected(false);
            ok.setSelected(true);
            cancel.setFocusable(false);
            ok.setFocusable(true);
        }else if(ok.isSelected()){
            cancel.setSelected(true);
            ok.setSelected(false);
            cancel.setFocusable(true);
            ok.setFocusable(false);
        }
    }


    @Override
    public void onBackPressed() {
        return  ;
    }
}
