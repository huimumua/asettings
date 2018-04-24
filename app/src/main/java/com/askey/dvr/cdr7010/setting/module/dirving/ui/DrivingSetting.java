package com.askey.dvr.cdr7010.setting.module.dirving.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:22
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:22
 * 修改备注：
 */
public class DrivingSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    private String switch_tag;//把所有的开关界面通用化，用来区别不同的开关界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_menu_layout);

        initView();

    }

    private void initView() {
        list_view = (ListView) findViewById(R.id.list_view);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);

        list_view.setOnItemClickListener(this);

        menuInfo = getIntent().getStringArrayExtra("menu_item");

        setViewAndData(list_view, vp_progress, menuInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(DrivingSetting.this, SwitchActivity.class);
        Log.i("position",menuInfo[position]);
        switch (position) {
            case 0:
                switch_tag = Const.FRONT_COLLISION_WARING;
                break;
            case 1:
                switch_tag = Const.LANE_DEPARTURE;
                break;
            case 2:
                switch_tag = Const.PEDESTRIAN_DETECTION;
                break;
            case 3:
                switch_tag = Const.DEVICE_INSTALL_SETTING;
                intent = new Intent(DrivingSetting.this, DeviceInstallActivity.class);
                startActivity(intent);
                return;
            case 4:
                switch_tag = Const.DEPARTURE_DELAY_WARNING;
                break;
            case 5:
                intent = new Intent(DrivingSetting.this, RangeSettingActivity.class);
                startActivity(intent);
                return;
        }
        intent.putExtra("switch_tag", switch_tag);
        startActivity(intent);
    }
}
