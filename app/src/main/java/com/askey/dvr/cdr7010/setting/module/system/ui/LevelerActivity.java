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
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.base.CameraBaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.leveler.SpiritView;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;
import com.askey.dvr.cdr7010.setting.widget.MarqueeTextView;
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
//    private int MAX_ANGLE = 60;
    private int MAX_ANGLE = 90;
    //定义Sensor管理器
    private SensorManager sensorManager;
    private MarqueeTextView marqueeTextView;
    private int screenWidth ,screenHeight;
    private  ContentResolver contentResolver;
    private float yawAngle,pitchAngle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_installation_leveler);

        String title = getResources().getString(R.string.installation_tool_leveler);
        setTitleView(title);
        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee_text);
        marqueeTextView.setText(getString(R.string.system_setting_install_leveler));

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                cameraInit();
            }
        }).start();

        int pitchAngle = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_PITCH_ANGLE, -1);
        int yawAngle = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_YAW_ANGLE, -1);
        Logg.i(TAG,"=====pitchAngle==="+pitchAngle);
        Logg.i(TAG,"=====yawAngle==="+yawAngle);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float values[] = sensorEvent.values;
        //获取传感器的类型
        int sensorType = sensorEvent.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                //获取与Y轴的夹角
//                yaw = event.values[0];
//                pitch = event.values[1];
//                roll = event.values[2];
                yawAngle = values[2];
                pitchAngle = values[1];
                float zAngle = values[1];
                //获取与Z轴的夹角
                float yAngle = values[2];
                Logg.i(TAG,"=onSensorChanged=yawAngle="+yawAngle+"==pitchAngle=="+pitchAngle);
                //气泡位于中间时（水平仪完全水平）
                int x = spiritView.bubbleBitmap.getWidth()/2;
                int y = spiritView.bubbleBitmap.getHeight()/2;
                x= screenWidth/2-x;
                y = screenHeight/2-y;
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
                    x = screenWidth - spiritView.bubbleBitmap.getWidth();
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
                    y = screenHeight - spiritView.bubbleBitmap.getHeight();
                }
                //如果与Y轴的倾斜角已经小于负的Max_ANGLE,气泡应到最上边
                else {
                    y = 0;
                }
                //如果计算出来的X，Y坐标还位于水平仪的仪表盘之内，则更新水平仪气泡坐标
                if (true) {
                    spiritView.bubbleX = x;
                    spiritView.bubbleY = y;
                }
//                Logg.i(TAG,"onDraw==bubbleX="+x +"====bubbleY===="+y);
                //通知组件更新
                spiritView.postInvalidate();
                //show.invalidate();
                break;
        }
    }

    private boolean isContain(int x, int y) {
        //计算气泡的圆心坐标X，y
        int bubbleCx = x + spiritView.bubbleBitmap.getWidth() / 2;
        int bubbleCy = y + spiritView.bubbleBitmap.getWidth() / 2;
        //计算水平仪仪表盘圆心的坐标
        int backCx = spiritView.backBitmap.getWidth() / 2;
        int backCy = spiritView.backBitmap.getWidth() / 2;
        //计算气泡的圆心与水平仪表盘的圆心之间的距离
        double distance = Math.sqrt((bubbleCx - backCx) * (bubbleCx * backCx) +
                (bubbleCy - backCy) * (bubbleCy - backCy));
        //若两圆心的距离小于他们的半径差，即可认为处于该点的气泡任然位于仪表盘内
        if (distance < (spiritView.backBitmap.getWidth() - spiritView.bubbleBitmap.getWidth())) {
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
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_YAW_ANGLE, (int) yawAngle);
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_PITCH_ANGLE, (int) pitchAngle);
            int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
            if (car_type==1) {
                Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
                intent.putExtra("set_wizard_help_index", "set_wizard_help_context_vehicle_type");
                startActivity(intent);
                finish();
                return true;
            }
            finish();
        }if (keyCode == KeyEvent.KEYCODE_BACK) {
            int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
            if (car_type==1) {
                startActivity(new Intent(mContext, LevelerDetailActivity.class));
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }



}
