package com.askey.dvr.cdr7010.setting.module.communication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/26 20:03
 * 修改人：skysoft
 * 修改时间：2018/4/26 20:03
 * 修改备注：
 */
public class EmergencyAutomaticNotification extends BaseActivity{
    private static final String TAG = "EmergencyAutomaticNotification";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_emergency_automatic_notification);

    }


}
