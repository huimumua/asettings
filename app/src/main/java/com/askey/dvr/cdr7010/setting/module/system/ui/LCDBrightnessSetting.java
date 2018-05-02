package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:39
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:39
 * 修改备注：
 */
public class LCDBrightnessSetting extends BaseActivity {
    private int currentBrightness = 0;
    private ImageView brightness1, brightness2, brightness3, brightness4, brightness5,
            brightness6, brightness7, brightness8, brightness9, brightness10;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcd_brightness);
        contentResolver = getContentResolver();
        initView();
        getScreenBrightness();
        refresh(currentBrightness, "current");
    }

    private void initView() {
        brightness1 = (ImageView) findViewById(R.id.iv_brightness1);
        brightness2 = (ImageView) findViewById(R.id.iv_brightness2);
        brightness3 = (ImageView) findViewById(R.id.iv_brightness3);
        brightness4 = (ImageView) findViewById(R.id.iv_brightness4);
        brightness5 = (ImageView) findViewById(R.id.iv_brightness5);
        brightness6 = (ImageView) findViewById(R.id.iv_brightness6);
        brightness7 = (ImageView) findViewById(R.id.iv_brightness7);
        brightness8 = (ImageView) findViewById(R.id.iv_brightness8);
        brightness9 = (ImageView) findViewById(R.id.iv_brightness9);
        brightness10 = (ImageView) findViewById(R.id.iv_brightness10);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                getScreenBrightness();
                currentBrightness = currentBrightness - 25;
                setScreenBrightness(currentBrightness);
                refresh(currentBrightness, "down");
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                getScreenBrightness();
                currentBrightness = (currentBrightness + 26);
                setScreenBrightness(currentBrightness);
                refresh(currentBrightness, "up");
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    //获取屏幕亮度值
    private int getScreenBrightness() {
        ContentResolver contentResolver = this.getContentResolver();
        int defVal = 125;
        currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, defVal);
        return currentBrightness;
    }

    /**
     * 设置屏幕的亮度
     */
    private void setScreenBrightness(int process) {
        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        if (process > 255) {
            process = 255;
        } else if (process < 0) {
            process = 0;
        }
        float f = process / 255.0F;
        localLayoutParams.screenBrightness = f;
        getWindow().setAttributes(localLayoutParams);
        //修改系统的亮度值,以至于退出应用程序亮度保持
        saveBrightness(getContentResolver(), process);
    }

    public static void saveBrightness(ContentResolver resolver, int brightness) {
        //设置为手动调节模式
        Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        //保存到系统中
        Uri uri = android.provider.Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        android.provider.Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
        resolver.notifyChange(uri, null);
    }

    private void refresh(int currentBrightness, String type) {
        if (currentBrightness > 255) {
            currentBrightness = 255;
        } else if (currentBrightness < 0) {
            currentBrightness = 0;
        }
        int process = currentBrightness * 10 / 255;
        Settings.Global.putInt(contentResolver, Const.SYSSET_monitor_brightness, process);
        if (type.equals("down")) {
            if (process == 9) {
                brightness10.setImageDrawable(getDrawable(R.drawable.unchecked10));
            } else if (process == 8) {
                brightness9.setImageDrawable(getDrawable(R.drawable.unchecked9));
            } else if (process == 7) {
                brightness8.setImageDrawable(getDrawable(R.drawable.unchecked8));
            } else if (process == 6) {
                brightness7.setImageDrawable(getDrawable(R.drawable.unchecked7));
            } else if (process == 5) {
                brightness6.setImageDrawable(getDrawable(R.drawable.unchecked6));
            } else if (process == 4) {
                brightness5.setImageDrawable(getDrawable(R.drawable.unchecked5));
            } else if (process == 3) {
                brightness4.setImageDrawable(getDrawable(R.drawable.unchecked4));
            } else if (process == 2) {
                brightness3.setImageDrawable(getDrawable(R.drawable.unchecked3));
            } else if (process == 1) {
                brightness2.setImageDrawable(getDrawable(R.drawable.unchecked2));
            } else if (process == 0) {
                brightness1.setImageDrawable(getDrawable(R.drawable.unchecked1));
            }
        } else if (type.equals("up")) {
            if (process == 10) {
                brightness10.setImageDrawable(getDrawable(R.drawable.checked10));
            } else if (process == 9) {
                brightness9.setImageDrawable(getDrawable(R.drawable.checked9));
            } else if (process == 8) {
                brightness8.setImageDrawable(getDrawable(R.drawable.checked8));
            } else if (process == 7) {
                brightness7.setImageDrawable(getDrawable(R.drawable.checked7));
            } else if (process == 6) {
                brightness6.setImageDrawable(getDrawable(R.drawable.checked6));
            } else if (process == 5) {
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
            } else if (process == 4) {
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
            } else if (process == 3) {
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
            } else if (process == 2) {
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
            } else if (process == 1) {
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            }
        } else if (type.equals("current")) {
            if (process == 10) {
                brightness10.setImageDrawable(getDrawable(R.drawable.checked10));
                brightness9.setImageDrawable(getDrawable(R.drawable.checked9));
                brightness8.setImageDrawable(getDrawable(R.drawable.checked8));
                brightness7.setImageDrawable(getDrawable(R.drawable.checked7));
                brightness6.setImageDrawable(getDrawable(R.drawable.checked6));
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 9) {
                brightness9.setImageDrawable(getDrawable(R.drawable.checked9));
                brightness8.setImageDrawable(getDrawable(R.drawable.checked8));
                brightness7.setImageDrawable(getDrawable(R.drawable.checked7));
                brightness6.setImageDrawable(getDrawable(R.drawable.checked6));
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 8) {
                brightness8.setImageDrawable(getDrawable(R.drawable.checked8));
                brightness7.setImageDrawable(getDrawable(R.drawable.checked7));
                brightness6.setImageDrawable(getDrawable(R.drawable.checked6));
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 7) {
                brightness7.setImageDrawable(getDrawable(R.drawable.checked7));
                brightness6.setImageDrawable(getDrawable(R.drawable.checked6));
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 6) {
                brightness6.setImageDrawable(getDrawable(R.drawable.checked6));
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 5) {
                brightness5.setImageDrawable(getDrawable(R.drawable.checked5));
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 4) {
                brightness4.setImageDrawable(getDrawable(R.drawable.checked4));
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 3) {
                brightness3.setImageDrawable(getDrawable(R.drawable.checked3));
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 2) {
                brightness2.setImageDrawable(getDrawable(R.drawable.checked2));
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            } else if (process == 1) {
                brightness1.setImageDrawable(getDrawable(R.drawable.checked1));
            }
        }
    }
}
