package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.base.CameraBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Logg;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/5/14 9:26
 * 修改人：skysoft
 * 修改时间：2018/5/14 9:26
 * 修改备注：
 */
public class PreviewActivity extends CameraBaseActivity {
    private static final String TAG = "PreviewActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        setRightView(false,true,false);

        startPreview();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logg.i(TAG,"=====onDestroy=======");
        //取消注册
        stopPreview();
    }

}
