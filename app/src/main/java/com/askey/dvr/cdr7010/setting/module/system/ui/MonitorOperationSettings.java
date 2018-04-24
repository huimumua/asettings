package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import static android.provider.Settings.System.SCREEN_OFF_TIMEOUT;

public class MonitorOperationSettings extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_menu_layout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_monitor_time_settings),menuInfo);
        list_view.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_on))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_off))) {
//            Settings.System.putInt(getContentResolver(), SCREEN_OFF_TIMEOUT, 1 * 10 * 1000);
        } else if (clickItem.equals(getResources().getString(R.string.tv_monitor_operation_dim))) {

        }
    }
}
