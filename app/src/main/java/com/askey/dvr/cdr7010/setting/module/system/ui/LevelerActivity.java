package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.CameraBaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.leveler.SpiritView;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.widget.JVCMarqueeTextView;
import com.askey.platform.AskeySettings;

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
public class LevelerActivity extends CameraBaseActivity implements SensorEventListener {
    private static final String TAG = "LevelerActivity";
    //定义水平仪的仪表盘
    private SpiritView spiritView;
    //定义水平仪能处理的最大倾斜角度，超过该角度气泡直接位于边界
//    private int MAX_ANGLE = 30;
    private int MAX_ANGLE = 60;
//    private int MAX_ANGLE = 90;
    //定义Sensor管理器
    private SensorManager sensorManager;
    private JVCMarqueeTextView marqueeTextView;
    private int screenWidth ,screenHeight;
    private  ContentResolver contentResolver;
    private float yawAngle,pitchAngle;
    public static int backBitmapX,backBitmapY;
    private int spBubbleBitmapWidth,spBubbleBitmapHeight;
    private int count = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_installation_leveler);

        String title = getResources().getString(R.string.installation_tool_leveler);
        setTitleView(title);
        marqueeTextView = (JVCMarqueeTextView) findViewById(R.id.marquee_text);
        marqueeTextView.setContentText(getString(R.string.system_setting_install_leveler));

        //获取水平仪的主组件
        spiritView = (SpiritView) findViewById(R.id.show);
        //获取传感器
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        startPreview();
        setRightView(false,0,true,R.drawable.tag_menu_sub_ok,false,0);

        contentResolver = getContentResolver();
        //获取窗口管理器
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的高度和宽度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        int with = spiritView.backBitmap.getWidth()/2;

        backBitmapX = screenWidth/2-with;
        backBitmapY = screenHeight/2-with;
        spBubbleBitmapWidth = spiritView.bubbleBitmap.getWidth()/2;
        spBubbleBitmapHeight = spiritView.bubbleBitmap.getHeight()/2;

        initSensorManager();

        int pitchAngle = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_PITCH_ANGLE, -1);
        int yawAngle = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_YAW_ANGLE, -1);
        Logg.i(TAG,"=====pitchAngle==="+pitchAngle);
        Logg.i(TAG,"=====yawAngle==="+yawAngle);
    }

    private Sensor accelSensor = null, compassSensor = null, orientSensor = null;
    private float[] accelValues = new float[3], compassValues = new float[3],orientValues = new float[3];
    private boolean ready = false; //检查传感器是否正常工作，即是否同时具有加速传感器和磁场传感器。
    private float[] inR = new float[9];
    private float[] inclineMatrix = new float[9];
    private float[] prefValues = new float[3];
    private double mInclination;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                for (int i = 0; i < 3; i++) {
                    accelValues[i] = sensorEvent.values[i];
                }
                if (compassValues[0] != 0) //如果accelerator和magnetic传感器都有数值，设置为真
                    ready = true;
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i = 0; i < 3; i++) {
                    compassValues[i] = sensorEvent.values[i];
                }
                if (accelValues[2] != 0) //检查accelerator和magnetic传感器都有数值，只是换一个轴向检查
                    ready = true;
                break;

            case Sensor.TYPE_ORIENTATION:
                for (int i = 0; i < 3; i++) {
                    orientValues[i] = sensorEvent.values[i];
                }
                break;
        }

        if (!ready)
            return;

        //【2】根据加速传感器的数值accelValues[3]和磁力感应器的数值compassValues[3]，进行矩阵计算，获得方位
        //【2.1】计算rotation matrix R(inR)和inclination matrix I(inclineMatrix)
        if (SensorManager.getRotationMatrix(inR, inclineMatrix, accelValues, compassValues)) {
            /* 【2.2】根据rotation matrix计算设备的方位。，范围数组：
            values[0]: azimuth, rotation around the Z axis.
            values[1]: pitch, rotation around the X axis.
            values[2]: roll, rotation around the Y axis.*/
            SensorManager.getOrientation(inR, prefValues);
            //【2.2】根据inclination matrix计算磁仰角，地球表面任一点的地磁场总强度的矢量方向与水平面的夹角。
            mInclination = SensorManager.getInclination(inclineMatrix);
            if(count++ % 10 == 0){
                doUpdate();
                count = 1;
            }

        }
    }

    private void doUpdate() {

        //preValues[0]是方位角，单位是弧度，范围是-pi到pi，通过Math.toDegrees()转换为角度
        float mAzimuth = (float)Math.toDegrees(prefValues[0]);
        /*//纠正为orientation的数值。
         * if(mAzimuth < 0)
            mAzimuth += 360.0;*/

        float zAngle = (float) Math.toDegrees(prefValues[1]);
        float yAngle = (float) Math.toDegrees(prefValues[2]);
//        float yawAngle = (float) Math.toDegrees(mInclination);


//        Logg.i(TAG,"=onSensorChanged=yawAngle="+yawAngle+"==pitchAngle=="+pitchAngle);
        //气泡位于中间时（水平仪完全水平）

        int x= screenWidth/2-spBubbleBitmapWidth;
        int y= screenHeight/2-spBubbleBitmapHeight;
//                Logg.i(TAG,"=onSensorChanged=x="+x+ "==y=="+y);
        //如果与Z轴的倾斜角还在最大角度之内
        if (Math.abs(zAngle) <= MAX_ANGLE) {
            //根据与Z轴的倾斜角度计算X坐标轴的变化值
            int deltaX = (int)(x * zAngle / MAX_ANGLE);
//                    Logg.i(TAG,"=onSensorChanged=deltaX="+deltaX);
            x += deltaX;
        }
        //如果与Z轴的倾斜角已经大于MAX_ANGLE，气泡应到最左边
        else if (zAngle > MAX_ANGLE) {
            x = 0;
        }
        //如果与Z轴的倾斜角已经小于负的Max_ANGLE,气泡应到最右边
        else {
            x = screenWidth - spBubbleBitmapWidth;
        }

        //如果与Y轴的倾斜角还在最大角度之内
        if (Math.abs(yAngle) <= MAX_ANGLE) {
            //根据与Z轴的倾斜角度计算X坐标轴的变化值
            int deltaY = (int)(y * yAngle / MAX_ANGLE);
//                    Logg.i(TAG,"=onSensorChanged=deltaY="+deltaY);
            y += deltaY;
        }
        //如果与Y轴的倾斜角已经大于MAX_ANGLE，气泡应到最下边
        else if (yAngle > MAX_ANGLE) {
            y = screenHeight - spBubbleBitmapHeight;
        }
        //如果与Y轴的倾斜角已经小于负的Max_ANGLE,气泡应到最上边
        else {
            y = 0;
        }
        //如果计算出来的X，Y坐标还位于水平仪的仪表盘之内，则更新水平仪气泡坐标
        if (/*isContain(x,y)*/true) {
            spiritView.bubbleX = x+8;
            spiritView.bubbleY = y;
        }
//                Logg.i(TAG,"onDraw==bubbleX="+x +"====bubbleY===="+y);
        //通知组件更新
        spiritView.postInvalidate();
//                spiritView.invalidate();


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

    private void initSensorManager() {
        //地磁场
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //加速度
        Sensor acceleromter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, acceleromter, SensorManager.SENSOR_DELAY_UI);

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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_YAW_ANGLE, (int) yawAngle);
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_PITCH_ANGLE, (int) pitchAngle);
//            int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
            if (Const.SET_WIZARD) {
                Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
                intent.putExtra("set_wizard_help_index", "set_wizard_help_context_vehicle_type");
                startActivity(intent);
                finish();
                return true;
            }
            finish();
        }if (keyCode == KeyEvent.KEYCODE_BACK) {
//            int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
            if (Const.SET_WIZARD) {
                startActivity(new Intent(mContext, LevelerDetailActivity.class));
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



}
