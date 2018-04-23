package com.askey.dvr.cdr7010.setting.module.movie.ui;

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
 * 创建时间：2018/4/8 13:24
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:24
 * 修改备注：
 */
public class MovieRecordSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_menu_layout);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(menuInfo);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.audio_recording))) {
            startActivity(new Intent(MovieRecordSetting.this, AudioRecordActivity.class));
        }
        if(clickItem.equals(getResources().getString(R.string.impact_detection_sensitivity))) {
            startActivity(new Intent(MovieRecordSetting.this, ImpactSensitivityActivity.class));
        }
        if(clickItem.equals(getResources().getString(R.string.information_stamp_record))) {
            startActivity(new Intent(MovieRecordSetting.this, InformationStampActivity.class));
        }
    }
}
