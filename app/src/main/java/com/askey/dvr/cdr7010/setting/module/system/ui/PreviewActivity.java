package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.CameraBaseActivity;

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
    private int cameraId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        cameraId = getIntent().getIntExtra("cameraId",0);
        setRightView(false,true,false);
        Log.d(TAG, "onCreate: "+ Camera.getNumberOfCameras());

    }

    @Override
    protected void onResume() {
        super.onResume();
        startPreview(cameraId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPreview();
    }

    @Override
    public void onKeyShortPressed(int keyCode) {
        super.onKeyShortPressed(keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                finish();
                break;
        }
    }
}
