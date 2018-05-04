package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;

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
        int monitorOperation = Settings.Global.getInt(contentResolver, Const.SYSSET_powersave_action, 0);
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
            Settings.Global.putInt(contentResolver, Const.SYSSET_powersave_action, 0);
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, Integer.MAX_VALUE);
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_off))) {
            Settings.Global.putInt(contentResolver, Const.SYSSET_powersave_action, 1);
            int monitorTime = Settings.Global.getInt(contentResolver, Const.SYSSET_powersave_time, 10) * 1000;
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, monitorTime);
            Settings.System.putInt(getContentResolver(), "screen_dim_timeout", 0);   //設成0表示要進入DIM且要關屏
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_dim))) {
            Settings.Global.putInt(contentResolver, Const.SYSSET_powersave_action, 2);
            int monitorTime = Settings.Global.getInt(contentResolver, Const.SYSSET_powersave_time, 10) * 1000;
            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, monitorTime);
            Settings.System.putInt(getContentResolver(), "screen_dim_timeout", 1);   //設成1表示要進入DIM不關屏
        }
    }
}
