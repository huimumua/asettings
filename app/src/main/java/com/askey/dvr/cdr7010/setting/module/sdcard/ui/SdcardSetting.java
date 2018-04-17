package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.askey.dvr.cdr7010.setting.base.BaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:29
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:29
 * 修改备注：
 */
public class SdcardSetting extends BaseActivity {

    private static final String TAG = "SdcardSetting";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(mContext, SdcardInformation.class));
        startActivity(new Intent(mContext, SdcardInitialization.class));

}


}
