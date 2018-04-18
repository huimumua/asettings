package com.askey.dvr.cdr7010.setting.module.parking.ui;

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
 * 创建时间：2018/4/8 13:26
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:26
 * 修改备注：
 */
public class ParkingRecordSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_record_settings);

        initView();
    }

    private void initView() {
        list_view = findViewById(R.id.list_view);
        vp_progress = findViewById(R.id.vp_progress);

        list_view.setOnItemClickListener(this);

        menuInfo = getIntent().getStringArrayExtra("menu_item");

        setViewAndData(list_view, vp_progress, menuInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.parking_record_rt))) {
            startActivity(new Intent(ParkingRecordSetting.this,RecordingTimeActivity.class));
        }
    }

}
