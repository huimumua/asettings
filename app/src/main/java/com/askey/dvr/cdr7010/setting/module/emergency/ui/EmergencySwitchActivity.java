package com.askey.dvr.cdr7010.setting.module.emergency.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;

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
        jvcRelativeLayout.setMarquee_visible(true);
        jvcRelativeLayout.setMarquee_text("OFFに設定すると、衝撃を検知したときでもコールセンターに自動通報しません");
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(Const.ON)) {

        } else if (clickItem.equals(Const.OFF)) {

        }
    }
}
