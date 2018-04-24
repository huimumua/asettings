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
public class NotificationSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    private String switch_tag;//把所有的开关界面通用化，用来区别不同的开关界面

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_menu_layout);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.main_menu_nsg),menuInfo);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(NotificationSetting.this, NotifySwitchActivity.class);
        switch (position) {
            case 0:
                switch_tag = menuInfo[0];
                break;
            case 1:
                switch_tag = menuInfo[1];
                break;
            case 2:
                switch_tag = menuInfo[2];
                break;
            case 3:
                switch_tag = menuInfo[3];
                break;
            case 4:
                switch_tag = menuInfo[4];
                break;
            case 5:
                switch_tag = menuInfo[5];
                break;
            case 6:
                switch_tag = menuInfo[6];
                break;
            case 7:
                switch_tag = menuInfo[7];
                break;
            case 8:
                switch_tag = menuInfo[8];
                break;
            case 9:
                switch_tag = menuInfo[9];
                break;
            case 10:
                switch_tag = menuInfo[10];
                break;
            case 11:
                switch_tag = menuInfo[11];
                break;
            case 12:
                switch_tag = menuInfo[12];
                break;
            case 13:
                switch_tag = menuInfo[13];
                break;
            case 14:
                switch_tag = menuInfo[14];
                break;
        }
        intent.putExtra("switch_tag", switch_tag);
        startActivity(intent);
    }
}
