package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:40
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:40
 * 修改备注：
 */
public class MonitorScreenPowerSavingSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private TextView tv_title;
    private String[] secondMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_menu_layout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_system_settings_screen_power_saving),menuInfo);
        list_view.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_monitor_time_settings))) {
            secondMenuItem = getResources().getStringArray(R.array.monitor_time_settings);
            Intent intent = new Intent(MonitorScreenPowerSavingSetting.this, MonitorTimeSettings.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_settings))) {
            secondMenuItem = getResources().getStringArray(R.array.monitor_operation_settings);
            Intent intent = new Intent(MonitorScreenPowerSavingSetting.this, MonitorOperationSettings.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }
    }
}
