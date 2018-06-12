package com.askey.dvr.cdr7010.setting.module.emergency.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

public class EmergencySwitchActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    private String switch_tag;
    private ContentResolver contentResolver;
    private int settingValue;
    private int focusPosition = 0;
    private String[] menuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        contentResolver = getContentResolver();
        menuInfo = getResources().getStringArray(R.array.all_switch_item);
        switch_tag = getResources().getString(R.string.emergency_automatic_setting);
        initView(switch_tag, R.drawable.icon_submenu_audio_guide,menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
