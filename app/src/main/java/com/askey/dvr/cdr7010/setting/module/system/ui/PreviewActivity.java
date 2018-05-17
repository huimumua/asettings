package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.leveler.SpiritView;
import com.askey.dvr.cdr7010.setting.util.Logg;

import java.io.IOException;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/5/14 9:26
 * 修改人：skysoft
 * 修改时间：2018/5/14 9:26
 * 修改备注：
 */
public class PreviewActivity extends BaseActivity implements SurfaceHolder.Callback{
    private static final String TAG = "PreviewActivity";
    private Camera camera;
    private SurfaceView preview;
    private SurfaceHolder surfaceHolder;
    private boolean isPreviewing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        preview = (SurfaceView) findViewById(R.id.preview);
        setRightView(false,true,false);

        cameraInit();
    }

    private void cameraInit() {
        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreview();
    }

    private void startPreview() {
        if (null != camera || isPreviewing) {
            stopPreview();
        }
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(0);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            isPreviewing = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview() {
        try {
            if(camera!=null){
                camera.setPreviewDisplay(null);
                camera.stopPreview();
                camera.release();
                camera = null ;
                isPreviewing = false;
            }
            if(surfaceHolder!=null){
                surfaceHolder.getSurface().release();
                surfaceHolder =null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logg.i(TAG,"=====onDestroy=======");
        //取消注册
        stopPreview();
    }

}
