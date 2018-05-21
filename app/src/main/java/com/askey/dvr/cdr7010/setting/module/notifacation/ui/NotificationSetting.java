package com.askey.dvr.cdr7010.setting.module.notifacation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:25
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:25
 * 修改备注：
 */
public class NotificationSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    private String switch_tag;//把所有的开关界面通用化，用来区别不同的开关界面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.main_menu_nsg),R.drawable.icon_submenu_audio_guide, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        Intent intent = new Intent(NotificationSetting.this, NotifySwitchActivity.class);
        String[] secondMenuItem = getResources().getStringArray(R.array.all_switch_item);
        intent.putExtra("menu_item", secondMenuItem);
        if (clickItem.equals(getResources().getString(R.string.reverse_run_detection))) {
            switch_tag = menuInfo[0];
        } else if (clickItem.equals(getResources().getString(R.string.speed_regulation_area))) {
            switch_tag = menuInfo[1];
        } else if (clickItem.equals(getResources().getString(R.string.pause))) {
            switch_tag = menuInfo[2];
        } else if (clickItem.equals(getResources().getString(R.string.accident_frequently_occurring_area))) {
            switch_tag = menuInfo[3];
        } else if (clickItem.equals(getResources().getString(R.string.driving_time))) {
            switch_tag = menuInfo[4];
        } else if (clickItem.equals(getResources().getString(R.string.rapid_acceleration_sudden_deceleration))) {
            switch_tag = menuInfo[5];
        } else if (clickItem.equals(getResources().getString(R.string.handling))) {
            switch_tag = menuInfo[6];
        } else if (clickItem.equals(getResources().getString(R.string.fluctuation_detection))) {
            switch_tag = menuInfo[7];
        } else if (clickItem.equals(getResources().getString(R.string.driving_outside_the_designated_area))) {
            switch_tag = menuInfo[8];
        } else if (clickItem.equals(getResources().getString(R.string.driving_report))) {
            switch_tag = menuInfo[9];
        } else if (clickItem.equals(getResources().getString(R.string.advice_before_driving))) {
            switch_tag = menuInfo[10];
        } else if (clickItem.equals(getResources().getString(R.string.notification))) {
            switch_tag = menuInfo[11];
        } else if (clickItem.equals(getResources().getString(R.string.weather_information))) {
            switch_tag = menuInfo[12];
        } else if (clickItem.equals(getResources().getString(R.string.road_kill))) {
            switch_tag = menuInfo[13];
        } else if (clickItem.equals(getResources().getString(R.string.location_information))) {
            switch_tag = menuInfo[14];
        }
        intent.putExtra("switch_tag", switch_tag);
        startActivity(intent);
    }
}
