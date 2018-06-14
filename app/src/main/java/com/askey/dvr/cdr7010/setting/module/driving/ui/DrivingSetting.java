package com.askey.dvr.cdr7010.setting.module.driving.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:22
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:22
 * 修改备注：
 */
public class DrivingSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    private String switch_tag;//把所有的开关界面通用化，用来区别不同的开关界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.driving_setting), R.drawable.icon_submenu_driving_support, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }

//    private void initView() {
//        list_view = (ListView) findViewById(R.id.list_view);
//        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);
//
//        list_view.setOnItemClickListener(this);
//
//        menuInfo = getIntent().getStringArrayExtra("menu_item");
//
//        setViewAndData(list_view, vp_progress, menuInfo);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DrivingSetting.this, SwitchActivity.class);
        String[] secondMenuItem = getResources().getStringArray(R.array.all_switch_item);
        intent.putExtra("menu_item", secondMenuItem);
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.front_collision_warning))) {
            switch_tag = getResources().getString(R.string.front_collision_warning);
        } else if (clickItem.equals(getResources().getString(R.string.lane_departure_warning))) {
            switch_tag = getResources().getString(R.string.lane_departure_warning);
        } else if (clickItem.equals(getResources().getString(R.string.pedestrian_detection))) {
            switch_tag = getResources().getString(R.string.pedestrian_detection);
        } else if (clickItem.equals(getResources().getString(R.string.reverse_run_detection))) {
            switch_tag = getResources().getString(R.string.reverse_run_detection);
        } else if (clickItem.equals(getResources().getString(R.string.driving_outside_the_designated_area))) {
            switch_tag = getResources().getString(R.string.driving_outside_the_designated_area);
        } else if (clickItem.equals(getResources().getString(R.string.driving_report))) {
            switch_tag = getResources().getString(R.string.driving_report);
        } else if (clickItem.equals(getResources().getString(R.string.advice_before_driving))) {
            switch_tag = getResources().getString(R.string.advice_before_driving);
        } else if (clickItem.equals(getResources().getString(R.string.rapid_acceleration_sudden_deceleration))) {
            switch_tag = getResources().getString(R.string.rapid_acceleration_sudden_deceleration);
        } else if (clickItem.equals(getResources().getString(R.string.handling))) {
            switch_tag = getResources().getString(R.string.handling);
        } else if (clickItem.equals(getResources().getString(R.string.fluctuation_detection))) {
            switch_tag = getResources().getString(R.string.fluctuation_detection);
        } else if (clickItem.equals(getResources().getString(R.string.pause))) {
            switch_tag = getResources().getString(R.string.pause);
        } else if (clickItem.equals(getResources().getString(R.string.accident_frequently_occurring_area))) {
            switch_tag = getResources().getString(R.string.accident_frequently_occurring_area);
        } else if (clickItem.equals(getResources().getString(R.string.speed_regulation_area))) {
            switch_tag = getResources().getString(R.string.speed_regulation_area);
        } else if (clickItem.equals(getResources().getString(R.string.driving_time))) {
            switch_tag = getResources().getString(R.string.driving_time);
        } else if (clickItem.equals(getResources().getString(R.string.road_kill))) {
            switch_tag = getResources().getString(R.string.road_kill);
        } else if (clickItem.equals(getResources().getString(R.string.weather_information))) {
            switch_tag = getResources().getString(R.string.weather_information);
        } else if (clickItem.equals(getResources().getString(R.string.notification))) {
            switch_tag = getResources().getString(R.string.notification);
        } else if (clickItem.equals(getResources().getString(R.string.location_information))) {
            switch_tag = getResources().getString(R.string.location_information);
        }
        intent.putExtra("switch_tag", switch_tag);
        startActivity(intent);

//        switch (position) {
//            case 0:
//                switch_tag = getResources().getString(R.string.front_collision_warning);
//                break;
//            case 1:
//                switch_tag = getResources().getString(R.string.lane_departure_warning);
//                break;
//            case 2:
//                switch_tag = getResources().getString(R.string.pedestrian_detection);
//                break;
//            case 3:
//                switch_tag = getResources().getString(R.string.device_install_setting);
//                secondMenuItem = getResources().getStringArray(R.array.mounting_position);
//                intent = new Intent(DrivingSetting.this, MountingPositionSetting.class);
//                intent.putExtra("menu_item", secondMenuItem);
//                startActivity(intent);
//                return;
//            case 4:
//                switch_tag = getResources().getString(R.string.departure_delay_warning);
//                break;
//            case 5:
//                intent = new Intent(DrivingSetting.this, RangeSettingActivity.class);
//                startActivity(intent);
//                return;
//        }
//        intent.putExtra("switch_tag", switch_tag);
//        startActivity(intent);
    }
}
