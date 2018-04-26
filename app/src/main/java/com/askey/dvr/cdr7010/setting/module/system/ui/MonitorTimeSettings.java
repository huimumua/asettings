package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

public class MonitorTimeSettings extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        contentResolver = getContentResolver();
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_monitor_time_settings), menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        int monitorTime = Settings.Global.getInt(contentResolver, "SYSSET_powersave_time", 10);
        int position = 0;
        if (monitorTime == 10) {
            position = 0;
        } else if (monitorTime == 60) {
            position = 1;
        } else if (monitorTime == 180) {
            position = 2;
        }
        list_view.setSelection(position);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_monitor_time_10sec))) {
            Settings.Global.putInt(contentResolver, "SYSSET_powersave_time", 10);
            if (Settings.Global.getInt(contentResolver, "SYSSET_powersave_action", 0) == 1) {
                Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, 10 * 1000);
            }
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_time_1min))) {
            Settings.Global.putInt(contentResolver, "SYSSET_powersave_time", 60);
            if (Settings.Global.getInt(contentResolver, "SYSSET_powersave_action", 0) == 1) {
                Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, 60 * 1000);
            }
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_time_3min))) {
            Settings.Global.putInt(contentResolver, "SYSSET_powersave_time", 180);
            if (Settings.Global.getInt(contentResolver, "SYSSET_powersave_action", 0) == 1) {
                Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, 180 * 1000);
            }
        }
    }
}
