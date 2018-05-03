package com.askey.dvr.cdr7010.setting.module.notifacation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;

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
        initView(getResources().getString(R.string.main_menu_nsg), menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        Intent intent = new Intent(NotificationSetting.this, NotifySwitchActivity.class);
        if (clickItem.equals(Const.REVERSE_RUN_DETECTION)) {
            switch_tag = menuInfo[0];
        } else if (clickItem.equals(Const.SPEED_REGULATION_AREA)) {
            switch_tag = menuInfo[1];
        } else if (clickItem.equals(Const.PAUSE)) {
            switch_tag = menuInfo[2];
        } else if (clickItem.equals(Const.ACCIDENT_FREQUENTLY_OCCURRING_AREA)) {
            switch_tag = menuInfo[3];
        } else if (clickItem.equals(Const.DRIVING_TIME)) {
            switch_tag = menuInfo[4];
        } else if (clickItem.equals(Const.RAPID_ACCELERATION_SUDDEN_DECELERATION)) {
            switch_tag = menuInfo[5];
        } else if (clickItem.equals(Const.HANDLING)) {
            switch_tag = menuInfo[6];
        } else if (clickItem.equals(Const.FLUCTUATION_DETECTION)) {
            switch_tag = menuInfo[7];
        } else if (clickItem.equals(Const.DRIVING_OUTSIDE_THE_DESIGNATED_AREA)) {
            switch_tag = menuInfo[8];
        } else if (clickItem.equals(Const.DRIVING_REPORT)) {
            switch_tag = menuInfo[9];
        } else if (clickItem.equals(Const.ADVICE_BEFORE_DRIVING)) {
            switch_tag = menuInfo[10];
        } else if (clickItem.equals(Const.NOTIFICATION)) {
            switch_tag = menuInfo[11];
        } else if (clickItem.equals(Const.WEATHER_INFORMATION)) {
            switch_tag = menuInfo[12];
        } else if (clickItem.equals(Const.ROAD_KILL)) {
            switch_tag = menuInfo[13];
        } else if (clickItem.equals(Const.LOCATION_INFORMATION)) {
            switch_tag = menuInfo[14];
        }
        intent.putExtra("switch_tag", switch_tag);
        startActivity(intent);
    }
}
