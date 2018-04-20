package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;

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
    protected static Context appContext = SettingApplication.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nemu);

        mContext = this;
    }


}
