package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.CameraBaseActivity;
import com.askey.dvr.cdr7010.setting.module.driving.ui.AdasSettingStatus;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.widget.JVCMarqueeTextView;
import com.askey.platform.AskeySettings;

public class RangeSettingActivity extends CameraBaseActivity {

    private static final String TAG = "RangeSettingActivity";
    private static final int DEFAULT_SKYLINE = 360;
    private static final int DEFAULT_BONNET_Y = 534;
    private static final int DEFAULT_CENTER_X = 640;

    /**
     * Just for ADAS_X_SCALE / ADAS_Y_SCALE
     */
    private static final int ADAS_IMAGE_WIDTH = 1280;
    private static final int ADAS_IMAGE_HEIGHT = 720;
    private static final int LCD_WIDTH = 320;
    private static final int LCD_HEIGHT = 240;

    /** X/Y scale is Image Resolution divide by the LCD resolution
     * ADAS Image Resolution = 1280x720
     * LCD resolution = 320x240
     */
    private static final int ADAS_X_SCALE = ADAS_IMAGE_WIDTH / LCD_WIDTH;
    private static final int ADAS_Y_SCALE = ADAS_IMAGE_HEIGHT / LCD_HEIGHT;

    private SurfaceView preview;
    private int previewHeight, previewWidth;
    private View line, line_center;

    private ViewGroup.MarginLayoutParams marginTopLayoutParams, marginLeftLayoutParams;
    private FrameLayout.LayoutParams layoutParams;
    private int fullLineCurrentMarginTop, dottedLineCurrentMarginTop, lineCurrentMarginLeft;
    private AdasSettingStatus status;
    private JVCMarqueeTextView notify_msg;
    private ContentResolver contentResolver;

    private RadioButton right_top,right_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_setting);

        right_top = (RadioButton) findViewById(R.id.top_btn);
        right_bottom = (RadioButton) findViewById(R.id.bottom_btn);

        status = AdasSettingStatus.SETTING_HORIZON;
        startPreview();
        preview = (SurfaceView) findViewById(R.id.preview);
        line = findViewById(R.id.line);
        notify_msg = (JVCMarqueeTextView) findViewById(R.id.notify_msg);
        marginTopLayoutParams = new ViewGroup.MarginLayoutParams(line.getLayoutParams());
        line_center = findViewById(R.id.line_center);
        marginLeftLayoutParams = new ViewGroup.MarginLayoutParams(line_center.getLayoutParams());
        contentResolver = getContentResolver();

        ViewTreeObserver treeObserver = preview.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                preview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                previewWidth = preview.getWidth();
                previewHeight = preview.getHeight();
                Log.i("height", previewHeight + "");
                //注意这里设置的是上外边距，设置下外边距貌似没用
                fullLineCurrentMarginTop = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_SKYLINE_RANGE, DEFAULT_SKYLINE) / ADAS_Y_SCALE;
                Log.d("full", fullLineCurrentMarginTop + "");
                setLineMarginTop(fullLineCurrentMarginTop);

                dottedLineCurrentMarginTop = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_BONNETY, DEFAULT_BONNET_Y) / ADAS_Y_SCALE;
                Log.d("dotted", dottedLineCurrentMarginTop + "");
            }
        });


        lineCurrentMarginLeft = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_CENTERX, DEFAULT_CENTER_X) / ADAS_X_SCALE;

        notify_msg.setContentText(getString(R.string.driving_setting_range_horizon));

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                switch (status) {
                    case SETTING_HORIZON:
                        Log.i("down_SETTING_HORIZON", fullLineCurrentMarginTop + "");
                        if (fullLineCurrentMarginTop < previewHeight - 1) {
                            fullLineCurrentMarginTop += 1;
                            setLineMarginTop(fullLineCurrentMarginTop);
                        }
                        break;
                    case SETTING_HOOD:
                        Log.i("down_SETTING_HOOD", dottedLineCurrentMarginTop + "");
                        if (dottedLineCurrentMarginTop < previewHeight - 1) {
                            dottedLineCurrentMarginTop += 1;
                            setLineMarginTop(dottedLineCurrentMarginTop);
                        }
                        break;
                    case SETTING_CENTER:
                        Log.i("right_SETTING_CENTER", lineCurrentMarginLeft + "");
                        if (lineCurrentMarginLeft > 1) {
                            lineCurrentMarginLeft -= 1;
                            setLineMarginLeft(lineCurrentMarginLeft);
                        }
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                switch (status) {
                    case SETTING_HORIZON:
                        Log.i("top_up", fullLineCurrentMarginTop + "");
                        if (fullLineCurrentMarginTop > 1) {
                            fullLineCurrentMarginTop -= 1;
                            setLineMarginTop(fullLineCurrentMarginTop);
                        }
                        break;
                    case SETTING_HOOD:
                        Log.i("top_up", dottedLineCurrentMarginTop + "");
                        if (dottedLineCurrentMarginTop > 1) {
                            dottedLineCurrentMarginTop -= 1;
                            setLineMarginTop(dottedLineCurrentMarginTop);
                        }
                        break;
                    case SETTING_CENTER:
                        Log.i("right", lineCurrentMarginLeft + "");
                        if (lineCurrentMarginLeft < previewWidth - 1) {
                            lineCurrentMarginLeft += 1;
                            setLineMarginLeft(lineCurrentMarginLeft);
                        }
                        break;
                }
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                Logg.i(TAG, "===KeyEvent.KEYCODE_ENTER===");

                switch (status) {
                    //地平线标记线
                    case SETTING_HORIZON:
                        status = AdasSettingStatus.SETTING_HOOD;
                        line.setBackgroundResource(R.drawable.range_setting_hood_line);
                        setLineMarginTop(dottedLineCurrentMarginTop);
                        notify_msg.stopScroll();
                        notify_msg.setContentText(getString(R.string.driving_setting_range_hood));
                        break;
                    //引擎盖标记线
                    case SETTING_HOOD:
                        status = AdasSettingStatus.SETTING_CENTER;
                        line.setVisibility(View.GONE);
                        line_center.setVisibility(View.VISIBLE);
                        setLineMarginLeft(lineCurrentMarginLeft);
                        notify_msg.stopScroll();
                        notify_msg.setContentText(getString(R.string.driving_setting_range_center));
                        setRightView(R.drawable.tag_menu_sub_right,R.drawable.tag_menu_sub_left);
                        break;
                    //车道中线标记线
                    case SETTING_CENTER:

                        Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_SKYLINE_RANGE, fullLineCurrentMarginTop * ADAS_Y_SCALE);
                        Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_BONNETY, dottedLineCurrentMarginTop * ADAS_Y_SCALE);
                        Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_CENTERX, lineCurrentMarginLeft * ADAS_X_SCALE);

//                        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
                        if (Const.SET_WIZARD) {
                            Intent intent = new Intent(mContext, SetWizardHelpActivity.class);
                            intent.putExtra("set_wizard_help_index", "set_wizard_help_finish");
                            startActivity(intent);
                        }
                        finish();
                        break;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                Logg.i(TAG, "===KeyEvent.KEYCODE_BACK===");
                switch (status) {
                    //地平线标记线
                    case SETTING_HORIZON:
                        finish();
                        return true;
                    //引擎盖标记线
                    case SETTING_HOOD:
                        status = AdasSettingStatus.SETTING_HORIZON;
                        line.setBackgroundResource(R.drawable.range_setting_horizon_line);
                        setLineMarginTop(fullLineCurrentMarginTop);
                        notify_msg.stopScroll();
                        notify_msg.setContentText(getString(R.string.driving_setting_range_horizon));
                        setRightView(R.drawable.tag_menu_main_moveup,R.drawable.tag_menu_main_movedown);
                        return true;
                    //车道中线标记线
                    case SETTING_CENTER:
                        status = AdasSettingStatus.SETTING_HOOD;
                        line_center.setVisibility(View.GONE);
                        line.setVisibility(View.VISIBLE);
                        line.setBackgroundResource(R.drawable.range_setting_hood_line);
                        setLineMarginTop(dottedLineCurrentMarginTop);
                        notify_msg.stopScroll();
                        notify_msg.setContentText(getString(R.string.driving_setting_range_hood));
                        setRightView(R.drawable.tag_menu_main_moveup,R.drawable.tag_menu_main_movedown);
                        return true;
                }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void setLineMarginTop(int lineCurrentMarginTop) {
        marginTopLayoutParams.setMargins(marginTopLayoutParams.leftMargin, lineCurrentMarginTop, marginTopLayoutParams.rightMargin, marginTopLayoutParams.bottomMargin);
        layoutParams = new FrameLayout.LayoutParams(marginTopLayoutParams);
        line.setLayoutParams(layoutParams);
    }

    private void setLineMarginLeft(int lineCurrentMarginLeft) {
        marginLeftLayoutParams.setMargins(lineCurrentMarginLeft, marginLeftLayoutParams.topMargin, marginLeftLayoutParams.rightMargin, marginLeftLayoutParams.bottomMargin);
        layoutParams = new FrameLayout.LayoutParams(marginLeftLayoutParams);
        line_center.setLayoutParams(layoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        Logg.i(TAG, "=====onPause=======");
        stopPreview();
    }

    private void setRightView(int top,int bottom) {
        right_top.setBackgroundResource(top);
        right_bottom.setBackgroundResource(bottom);
    }
}
