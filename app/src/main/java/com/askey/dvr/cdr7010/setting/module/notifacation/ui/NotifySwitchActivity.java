package com.askey.dvr.cdr7010.setting.module.notifacation.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.platform.AskeySettings;

public class NotifySwitchActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    private String switch_tag;
    private ContentResolver contentResolver;
    private int settingValue;
    private int focusPosition = 0;
    private String[] menuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        contentResolver = getContentResolver();
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        switch_tag = getIntent().getStringExtra("switch_tag");
        initView(switch_tag, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        contentResolver = getContentResolver();
        if (switch_tag.equals(Const.REVERSE_RUN_DETECTION)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_REVERSE_RUN, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.SPEED_REGULATION_AREA)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_SPEED_LIMIT_AREA, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.PAUSE)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_STOP, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.ACCIDENT_FREQUENTLY_OCCURRING_AREA)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_FREQ_ACCIDENT_AREA, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.DRIVING_TIME)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_TIME, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.RAPID_ACCELERATION_SUDDEN_DECELERATION)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_INTENSE_DRIVING, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.HANDLING)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_ABNORMAL_HANDING, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.FLUCTUATION_DETECTION)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_FLUCTUATION_DETECTION, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.DRIVING_OUTSIDE_THE_DESIGNATED_AREA)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_OUT_OF_AREA, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.DRIVING_REPORT)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_REPORT, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.ADVICE_BEFORE_DRIVING)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_ADVICE, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.NOTIFICATION)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_NOTIFICATION, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.WEATHER_INFORMATION)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_WEATHER_INFO, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.ROAD_KILL)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_ROAD_KILL, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.LOCATION_INFORMATION)) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_LOCATION_INFO, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        }
        list_view.setSelection(focusPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (switch_tag.equals(Const.REVERSE_RUN_DETECTION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_REVERSE_RUN, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_REVERSE_RUN, 0);
            }
        } else if (switch_tag.equals(Const.SPEED_REGULATION_AREA)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_SPEED_LIMIT_AREA, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_SPEED_LIMIT_AREA, 0);
            }
        } else if (switch_tag.equals(Const.PAUSE)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_STOP, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_STOP, 0);
            }
        } else if (switch_tag.equals(Const.ACCIDENT_FREQUENTLY_OCCURRING_AREA)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FREQ_ACCIDENT_AREA, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FREQ_ACCIDENT_AREA, 0);
            }
        } else if (switch_tag.equals(Const.DRIVING_TIME)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_TIME, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_TIME, 0);
            }
        } else if (switch_tag.equals(Const.RAPID_ACCELERATION_SUDDEN_DECELERATION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_INTENSE_DRIVING, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_INTENSE_DRIVING, 0);
            }
        } else if (switch_tag.equals(Const.HANDLING)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ABNORMAL_HANDING, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ABNORMAL_HANDING, 0);
            }
        } else if (switch_tag.equals(Const.FLUCTUATION_DETECTION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FLUCTUATION_DETECTION, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FLUCTUATION_DETECTION, 0);
            }
        } else if (switch_tag.equals(Const.DRIVING_OUTSIDE_THE_DESIGNATED_AREA)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_OUT_OF_AREA, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_OUT_OF_AREA, 0);
            }
        } else if (switch_tag.equals(Const.DRIVING_REPORT)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_REPORT, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_REPORT, 0);
            }
        } else if (switch_tag.equals(Const.ADVICE_BEFORE_DRIVING)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ADVICE, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ADVICE, 0);
            }
        } else if (switch_tag.equals(Const.NOTIFICATION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_NOTIFICATION, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_NOTIFICATION, 0);
            }
        } else if (switch_tag.equals(Const.WEATHER_INFORMATION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_WEATHER_INFO, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_WEATHER_INFO, 0);
            }
        } else if (switch_tag.equals(Const.ROAD_KILL)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ROAD_KILL, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ROAD_KILL, 0);
            }
        } else if (switch_tag.equals(Const.LOCATION_INFORMATION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_LOCATION_INFO, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_LOCATION_INFO, 0);
            }
        }
    }
}
