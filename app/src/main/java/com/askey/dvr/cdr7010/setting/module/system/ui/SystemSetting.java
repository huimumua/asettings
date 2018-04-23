package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:31
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:31
 * 修改备注：
 */

public class SystemSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private TextView tv_title;
    private String[] secondMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(getResources().getString(R.string.tv_system_settings));
        list_view = (ListView) findViewById(R.id.list_view);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);
        list_view.setOnItemClickListener(this);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        setViewAndData(list_view, vp_progress, menuInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i("SetingActivity", "=position==" + position);
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_system_settings_date_time))) {
            secondMenuItem = getResources().getStringArray(R.array.date_time);
            Intent intent = new Intent(SystemSetting.this, DateTimeSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_notification_sound_volume))) {
            Intent intent = new Intent(SystemSetting.this, NotificationSoundSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_playback_volume))) {
            Intent intent = new Intent(SystemSetting.this, PlaybackSoundSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_lcd_brightness))) {
            Intent intent = new Intent();
            intent.setClass(SystemSetting.this, LCDBrightnessSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_screen_power_saving))) {
            secondMenuItem = getResources().getStringArray(R.array.montior);
            Intent intent = new Intent(SystemSetting.this, MonitorScreenPowerSavingSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_tool))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_bluetooth))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_infrared_led))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_setting_initialization))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_2nd_camera))) {
            String[] secondCameraMenuItem = getResources().getStringArray(R.array.secend_camera_array);
            setViewAndData(list_view, vp_progress, secondCameraMenuItem);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_update))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_information))) {
            startActivity(new Intent(this, SystemInformation.class));
        }
    }
}
