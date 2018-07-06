package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.platform.AskeySettings;

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
    private int oldBrightness = 0;
    private ImageView brightness0, brightness1, brightness2, brightness3, brightness4, brightness5,
            brightness6, brightness7, brightness8, brightness9, brightness10;
    private ContentResolver contentResolver;
    private TextView title;
    private Resources r;
    private Drawable[] layers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcd_brightness);
        contentResolver = getContentResolver();
        initView();
        getScreenBrightness();
        oldBrightness = currentBrightness;
        refresh(currentBrightness, "current");
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_tv);
        title.setText(getResources().getString(R.string.tv_brightness_title));
        brightness0 = (ImageView) findViewById(R.id.iv_brightness0);
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
        r = getResources();
        layers = new Drawable[2];
        layers[1] = r.getDrawable(R.drawable.img_brightness_selected);
        setRightView(true, true, true);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(true, R.drawable.tag_menu_sub_increase, true, R.drawable.tag_menu_sub_ok, true, R.drawable.tag_menu_sub_decrease);
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
                currentBrightness = (currentBrightness + 25);
                setScreenBrightness(currentBrightness);
                refresh(currentBrightness, "up");
                break;
            case KeyEvent.KEYCODE_ENTER:
                setScreenBrightness(currentBrightness);
                finish();
                break;
            case KeyEvent.KEYCODE_BACK:
                setScreenBrightness(oldBrightness);
                finish();
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
        int process = currentBrightness * 10 / 250;
        Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_MONITOR_BRIGHTNESS, process);
        if (type.equals("down")) {
            if (process == 9) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_9);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness9.setImageDrawable(layerDrawable);
                brightness10.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 8) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_8);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness8.setImageDrawable(layerDrawable);
                brightness9.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 7) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_7);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness7.setImageDrawable(layerDrawable);
                brightness8.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 6) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_6);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness6.setImageDrawable(layerDrawable);
                brightness7.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 5) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_5);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness5.setImageDrawable(layerDrawable);
                brightness6.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 4) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_4);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness4.setImageDrawable(layerDrawable);
                brightness5.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 3) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_3);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness3.setImageDrawable(layerDrawable);
                brightness4.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 2) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_2);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness2.setImageDrawable(layerDrawable);
                brightness3.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 1) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_1);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness1.setImageDrawable(layerDrawable);
                brightness2.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            } else if (process == 0) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_0);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness0.setImageDrawable(layerDrawable);
                brightness1.setImageDrawable(getDrawable(R.drawable.bg_brightness_none));
            }
        } else if (type.equals("up")) {
            if (process == 10) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_10);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness10.setImageDrawable(layerDrawable);
                brightness9.setImageDrawable(getDrawable(R.drawable.img_brightness_9));
            } else if (process == 9) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_9);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness9.setImageDrawable(layerDrawable);
                brightness8.setImageDrawable(getDrawable(R.drawable.img_brightness_8));
            } else if (process == 8) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_8);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness8.setImageDrawable(layerDrawable);
                brightness7.setImageDrawable(getDrawable(R.drawable.img_brightness_7));
            } else if (process == 7) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_7);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness7.setImageDrawable(layerDrawable);
                brightness6.setImageDrawable(getDrawable(R.drawable.img_brightness_6));
            } else if (process == 6) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_6);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness6.setImageDrawable(layerDrawable);
                brightness5.setImageDrawable(getDrawable(R.drawable.img_brightness_5));
            } else if (process == 5) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_5);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness5.setImageDrawable(layerDrawable);
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
            } else if (process == 4) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_4);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness4.setImageDrawable(layerDrawable);
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
            } else if (process == 3) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_3);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness3.setImageDrawable(layerDrawable);
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
            } else if (process == 2) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_2);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness2.setImageDrawable(layerDrawable);
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
            } else if (process == 1) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_1);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness1.setImageDrawable(layerDrawable);
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 0) {
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            }
        } else if (type.equals("current")) {
            if (process == 10) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_10);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness10.setImageDrawable(layerDrawable);
                brightness9.setImageDrawable(getDrawable(R.drawable.img_brightness_9));
                brightness8.setImageDrawable(getDrawable(R.drawable.img_brightness_8));
                brightness7.setImageDrawable(getDrawable(R.drawable.img_brightness_7));
                brightness6.setImageDrawable(getDrawable(R.drawable.img_brightness_6));
                brightness5.setImageDrawable(getDrawable(R.drawable.img_brightness_5));
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 9) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_9);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness9.setImageDrawable(layerDrawable);
                brightness8.setImageDrawable(getDrawable(R.drawable.img_brightness_8));
                brightness7.setImageDrawable(getDrawable(R.drawable.img_brightness_7));
                brightness6.setImageDrawable(getDrawable(R.drawable.img_brightness_6));
                brightness5.setImageDrawable(getDrawable(R.drawable.img_brightness_5));
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 8) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_8);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness8.setImageDrawable(layerDrawable);
                brightness7.setImageDrawable(getDrawable(R.drawable.img_brightness_7));
                brightness6.setImageDrawable(getDrawable(R.drawable.img_brightness_6));
                brightness5.setImageDrawable(getDrawable(R.drawable.img_brightness_5));
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 7) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_7);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness7.setImageDrawable(layerDrawable);
                brightness6.setImageDrawable(getDrawable(R.drawable.img_brightness_6));
                brightness5.setImageDrawable(getDrawable(R.drawable.img_brightness_5));
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 6) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_6);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness6.setImageDrawable(layerDrawable);
                brightness5.setImageDrawable(getDrawable(R.drawable.img_brightness_5));
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 5) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_5);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness5.setImageDrawable(layerDrawable);
                brightness4.setImageDrawable(getDrawable(R.drawable.img_brightness_4));
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 4) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_4);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness4.setImageDrawable(layerDrawable);
                brightness3.setImageDrawable(getDrawable(R.drawable.img_brightness_3));
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 3) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_3);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness3.setImageDrawable(layerDrawable);
                brightness2.setImageDrawable(getDrawable(R.drawable.img_brightness_2));
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 2) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_2);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness2.setImageDrawable(layerDrawable);
                brightness1.setImageDrawable(getDrawable(R.drawable.img_brightness_1));
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 1) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_1);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness1.setImageDrawable(layerDrawable);
                brightness0.setImageDrawable(getDrawable(R.drawable.img_brightness_0));
            } else if (process == 0) {
                layers[0] = r.getDrawable(R.drawable.img_brightness_0);
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                brightness0.setImageDrawable(layerDrawable);
            }
        }
    }
}
