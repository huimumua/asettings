package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.util.Logg;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 11:17
 * 修改人：skysoft
 * 修改时间：2018/4/8 11:17
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity{
    private final  String  TAG = "BaseActivity";
    protected static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
    }


    private int keydowmRepeatCount =0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        keydowmRepeatCount = event.getRepeatCount();
        switch (event.getRepeatCount()){
            case 10:
                onKeyHoldHalfASecond(keyCode);
                break;
            case 20:
                onKeyHoldOneSecond(keyCode);
                break;
            case 60:
                onKeyHoldThreeSecond(keyCode);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keydowmRepeatCount < 10){
            onKeyShortPressed(keyCode);
        }
        return super.onKeyUp(keyCode, event);
    }


    public void onKeyShortPressed(int keyCode) {

    }

    public  void onKeyHoldHalfASecond(int keyCode){

    }

    public  void onKeyHoldOneSecond(int keyCode){

    }

    public  void onKeyHoldThreeSecond(int keyCode){

    }


}
