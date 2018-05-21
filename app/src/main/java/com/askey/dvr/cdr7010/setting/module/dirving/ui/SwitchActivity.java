package com.askey.dvr.cdr7010.setting.module.dirving.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.platform.AskeySettings;

public class SwitchActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    private String switch_tag;
    private String[] menuInfo;
    private ContentResolver contentResolver;
    private int settingValue;
    private int focusPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        contentResolver = getContentResolver();
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        switch_tag = getIntent().getStringExtra("switch_tag");
        initView(switch_tag,R.drawable.icon_submenu_driving_support, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        jvcRelativeLayout.setMarquee_visible(true);
        if (switch_tag.equals(getResources().getString(R.string.lane_departure_warning))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_LDS, 0);
            jvcRelativeLayout.setMarquee_text(getString(R.string.driving_setting_marqueeText_lane));
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.front_collision_warning))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_FCWS, 0);
            jvcRelativeLayout.setMarquee_text(getString(R.string.driving_setting_marqueeText_front));
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.pedestrian_detection))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_PEDESTRIAN_COLLISION, 0);
            jvcRelativeLayout.setMarquee_text(getString(R.string.driving_setting_marqueeText_pedestrain));
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(getResources().getString(R.string.departure_delay_warning))) {
            settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_DELAY_START, 0);
            jvcRelativeLayout.setMarquee_text(getString(R.string.driving_setting_marqueeText_departure));
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
        if (switch_tag.equals(getResources().getString(R.string.lane_departure_warning))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_LDS, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_LDS, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.front_collision_warning))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_FCWS, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_FCWS, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.pedestrian_detection))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_PEDESTRIAN_COLLISION, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_PEDESTRIAN_COLLISION, 0);
            }
        } else if (switch_tag.equals(getResources().getString(R.string.departure_delay_warning))) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_DELAY_START, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_DELAY_START, 0);
            }
        }
    }
}
