package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.sdcard.controller.SdcardFormatAsyncTask;

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
        title = (TextView) this.findViewById(R.id.sdcard_init_title);
        cancel = (Button) this.findViewById(R.id.sdcard_init_cancel_bt);
        ok = (Button) this.findViewById(R.id.sdcard_init_ok_bt);
        mProgressBar = (ProgressBar) this.findViewById(R.id.sdcard_init_progress);
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
        sdcardFormatAsyncTask = new SdcardFormatAsyncTask(this);
        sdcardFormatAsyncTask.execute();
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
