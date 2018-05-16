package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.platform.AskeySettings;

import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

public class MonitorOperationSettings extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        contentResolver = getContentResolver();
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_monitor_operation_settings), menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        jvcRelativeLayout.setMarquee_visible(true);
        jvcRelativeLayout.setMarquee_text(getString(R.string.system_setting_monitor_operation));
        int monitorOperation = Settings.Global.getInt(contentResolver, AskeySettings.Global.SYSSET_POWERSAVE_ACTION, 0);
        int position = 0;
        if (monitorOperation == 0) {
            position = 0;
        } else if (monitorOperation == 1) {
            position = 1;
        } else if (monitorOperation == 2) {
            position = 2;
        }
        list_view.setSelection(position);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_on))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_POWERSAVE_ACTION, 0);
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, Integer.MAX_VALUE);
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_off))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_POWERSAVE_ACTION, 1);
            int monitorTime = Settings.Global.getInt(contentResolver, AskeySettings.Global.SYSSET_POWERSAVE_TIME, 10) * 1000;
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, monitorTime);
            Settings.System.putInt(getContentResolver(), "screen_dim_timeout", 0);   //設成0表示要進入DIM且要關屏
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_dim))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_POWERSAVE_ACTION, 2);
            int monitorTime = Settings.Global.getInt(contentResolver, AskeySettings.Global.SYSSET_POWERSAVE_TIME, 10) * 1000;
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, monitorTime);
            Settings.System.putInt(getContentResolver(), "screen_dim_timeout", 1);   //設成1表示要進入DIM不關屏
        }
    }
}
