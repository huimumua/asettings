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
        if (switch_tag.equals(getResources().getString(R.string.reverse_run_detection))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_REVERSE_RUN, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.speed_regulation_area))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_SPEED_LIMIT_AREA, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.pause))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_STOP, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.accident_frequently_occurring_area))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_FREQ_ACCIDENT_AREA, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.driving_time))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_TIME, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.rapid_acceleration_sudden_deceleration))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_INTENSE_DRIVING, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.handling))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_ABNORMAL_HANDING, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.fluctuation_detection))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_FLUCTUATION_DETECTION, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.driving_outside_the_designated_area))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_OUT_OF_AREA, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.driving_report))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_REPORT, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.advice_before_driving))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_ADVICE, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.notification))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_NOTIFICATION, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.weather_information))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_WEATHER_INFO, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.road_kill))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.NOTIFY_ROAD_KILL, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.location_information))) {
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
        if (switch_tag.equals(getResources().getString(R.string.reverse_run_detection))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_REVERSE_RUN, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_REVERSE_RUN, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.speed_regulation_area))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_SPEED_LIMIT_AREA, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_SPEED_LIMIT_AREA, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.pause))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_STOP, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_STOP, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.accident_frequently_occurring_area))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FREQ_ACCIDENT_AREA, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FREQ_ACCIDENT_AREA, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.driving_time))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_TIME, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_TIME, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.rapid_acceleration_sudden_deceleration))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_INTENSE_DRIVING, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_INTENSE_DRIVING, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.handling))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ABNORMAL_HANDING, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ABNORMAL_HANDING, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.fluctuation_detection))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FLUCTUATION_DETECTION, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_FLUCTUATION_DETECTION, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.driving_outside_the_designated_area))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_OUT_OF_AREA, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_OUT_OF_AREA, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.driving_report))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_REPORT, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_DRIVING_REPORT, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.advice_before_driving))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ADVICE, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ADVICE, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.notification))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_NOTIFICATION, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_NOTIFICATION, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.weather_information))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_WEATHER_INFO, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_WEATHER_INFO, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.road_kill))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ROAD_KILL, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_ROAD_KILL, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.location_information))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_LOCATION_INFO, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.NOTIFY_LOCATION_INFO, 0);
            }
        }
    }
}
