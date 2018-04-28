package com.askey.dvr.cdr7010.setting.module.dirving.ui;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.util.AppUtil;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;

import java.io.IOException;

public class RangeSettingActivity extends BaseActivity implements SurfaceHolder.Callback {
    private static final String TAG = "RangeSettingActivity";
    private SurfaceView preview;
    private int previewHeight;
    private SurfaceHolder surfaceHolder;
    private View line;
    private Camera camera;
    private boolean isPreviewing = false;
    private ViewGroup.MarginLayoutParams marginLayoutParams;
    private FrameLayout.LayoutParams layoutParams;
    private int lineCurrentMarginTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_setting);

        preview = (SurfaceView) findViewById(R.id.preview);
        line = findViewById(R.id.line);
        marginLayoutParams = new ViewGroup.MarginLayoutParams(line.getLayoutParams());

        ViewTreeObserver treeObserver = preview.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                preview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                previewHeight = preview.getHeight();
                Log.i("height", previewHeight+"");
            }
        });

        //注意这里设置的是上边外距，设置下外边距貌似没用
        lineCurrentMarginTop = 150;
        setLineMarginTop(lineCurrentMarginTop);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.i("top_down",lineCurrentMarginTop+"");
                if (lineCurrentMarginTop < previewHeight-1) {
                    lineCurrentMarginTop += 1;
                    setLineMarginTop(lineCurrentMarginTop);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.i("top_up",lineCurrentMarginTop+"");
                if (lineCurrentMarginTop > 1) {
                    lineCurrentMarginTop -= 1;
                    setLineMarginTop(lineCurrentMarginTop);
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                Logg.i(TAG,"===KeyEvent.KEYCODE_ENTER===");
                boolean isFirstInit = (boolean) PreferencesUtils.get(mContext,Const.SETTTING_FIRST_INIT,true);
                if(isFirstInit){
                    PreferencesUtils.put(mContext,Const.SETTTING_FIRST_INIT,false);
                    AppUtil.startActivity(mContext,Const.DVR_MAIN_PAKAGE, Const.DVR_MAIN_CLASS,true);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
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
                camera=null;
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

    private void setLineMarginTop(int lineCurrentMarginTop){
        marginLayoutParams.setMargins(marginLayoutParams.leftMargin,lineCurrentMarginTop,marginLayoutParams.rightMargin,marginLayoutParams.bottomMargin);
        layoutParams = new FrameLayout.LayoutParams(marginLayoutParams);
        line.setLayoutParams(layoutParams);
    }

    @Override
    protected void onPause() {
        //取消注册
        Logg.i(TAG,"=====onPause=======");
        stopPreview();
        super.onPause();
    }

}
