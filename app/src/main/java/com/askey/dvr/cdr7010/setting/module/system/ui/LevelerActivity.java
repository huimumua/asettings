package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.leveler.SpiritView;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;

import java.io.IOException;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/24 15:38
 * 修改人：skysoft
 * 修改时间：2018/4/24 15:38
 * 修改备注：
 */


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
public class LevelerActivity extends BaseActivity implements SensorEventListener , SurfaceHolder.Callback {
    private static final String TAG = "LevelerActivity";
    //定义水平仪的仪表盘
    private SpiritView spiritView;
    //定义水平仪能处理的最大倾斜角度，超过该角度气泡直接位于边界
    private int MAX_ANGLE = 30;
    //定义Sensor管理器
    private SensorManager sensorManager;
    private Camera camera;
    private SurfaceView preview;
    private SurfaceHolder surfaceHolder;
    private boolean isPreviewing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_installation_leveler);

        //获取水平仪的主组件
        spiritView = (SpiritView) findViewById(R.id.show);
        //获取传感器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        preview = (SurfaceView) findViewById(R.id.preview);

        new Thread(new Runnable() {
            @Override
            public void run() {
                cameraInit();
            }
        }).start();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float values[] = sensorEvent.values;
        //获取传感器的类型
        int sensorType = sensorEvent.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                //获取与Y轴的夹角
                float yAngle = values[1];
                //获取与Z轴的夹角
                float zAngle = values[2];
                //气泡位于中间时（水平仪完全水平）
                int x = (spiritView.back.getWidth() - spiritView.bubble.getWidth()) / 2;
                int y = (spiritView.back.getHeight() - spiritView.bubble.getHeight()) / 2;
                //如果与Z轴的倾斜角还在最大角度之内
                if (Math.abs(zAngle) <= MAX_ANGLE) {
                    //根据与Z轴的倾斜角度计算X坐标轴的变化值
                    int deltaX = (int) ((spiritView.back.getWidth() - spiritView.bubble.getWidth()) / 2
                            * zAngle / MAX_ANGLE);
                    x += deltaX;
                }
                //如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
                else if (zAngle > MAX_ANGLE) {
                    x = 0;
                }
                //如果与Z轴的倾斜角已经小于负的Max_ANGLE,气泡应到最右边
                else {
                    x = spiritView.back.getWidth() - spiritView.bubble.getWidth();
                }

                //如果与Y轴的倾斜角还在最大角度之内
                if (Math.abs(yAngle) <= MAX_ANGLE) {
                    //根据与Z轴的倾斜角度计算X坐标轴的变化值
                    int deltaY = (int) ((spiritView.back.getHeight() - spiritView.bubble.getHeight()) / 2
                            * yAngle / MAX_ANGLE);
                    y += deltaY;
                }
                //如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
                else if (yAngle > MAX_ANGLE) {
                    y = spiritView.back.getHeight() - spiritView.bubble.getHeight();
                }
                //如果与Y轴的倾斜角已经小于负的Max_ANGLE,气泡应到最上边
                else {
                    y = 0;
                }
                //如果计算出来的X，Y坐标还位于水平仪的仪表盘之内，则更新水平仪气泡坐标
                if (true) {
                    spiritView.bubbleX = x;
                    spiritView.bubbleY = y;
                    //Toast.makeText(Spirit.this, "在仪表盘内", Toast.LENGTH_SHORT).show();
                }
                //通知组件更新
                spiritView.postInvalidate();
                //show.invalidate();
                break;
        }
    }

    private boolean isContain(int x, int y) {
        //计算气泡的圆心坐标X，y
        int bubbleCx = x + spiritView.bubble.getWidth() / 2;
        int bubbleCy = y + spiritView.bubble.getWidth() / 2;
        //计算水平仪仪表盘圆心的坐标
        int backCx = spiritView.back.getWidth() / 2;
        int backCy = spiritView.back.getWidth() / 2;
        //计算气泡的圆心与水平仪表盘的圆心之间的距离
        double distance = Math.sqrt((bubbleCx - backCx) * (bubbleCx * backCx) +
                (bubbleCy - backCy) * (bubbleCy - backCy));
        //若两圆心的距离小于他们的半径差，即可认为处于该点的气泡任然位于仪表盘内
        if (distance < (spiritView.back.getWidth() - spiritView.bubble.getWidth())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    protected void onStart() {
        Logg.i(TAG,"=====onStart=======");
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Logg.i(TAG,"=====onResume=======");

    }

    private void cameraInit() {
        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        Sensor mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //注册

        sensorManager.registerListener(this,mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        Logg.i(TAG,"=====onPause=======");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logg.i(TAG,"=====onStop=======");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logg.i(TAG,"=====onDestroy=======");
        //取消注册
        stopPreview();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            startActivity(new Intent(mContext, LevelerDetailActivity.class));
            finish();
            return true;
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
