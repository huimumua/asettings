package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.askey.dvr.cdr7010.setting.R;

import java.io.IOException;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/6/5 17:35
 * 修改人：skysoft
 * 修改时间：2018/6/5 17:35
 * 修改备注：
 */
public class CameraBaseActivity extends BaseActivity implements SurfaceHolder.Callback{
    private final  String  TAG = "CameraBaseActivity";
    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private boolean isPreviewing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void startPreview(){
        if((SurfaceView) this.findViewById(R.id.preview)!=null){
            surfaceHolder = ((SurfaceView) findViewById(R.id.preview)).getHolder();
            surfaceHolder.addCallback(this);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        preview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void preview() {
        if (null != camera || isPreviewing) {
            stopPreview();
        }
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            isPreviewing = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void stopPreview() {
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

}
