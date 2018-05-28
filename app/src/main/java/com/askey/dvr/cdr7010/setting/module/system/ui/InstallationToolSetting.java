package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:41
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:41
 * 修改备注：
 */
public class InstallationToolSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "InstallationToolSetting";
    private TextView tv_title;
    private String[] secondMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_system_settings_installation_tool),R.drawable.icon_submenu_setting,menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.installation_tool_leveler))) {
            //水准器
            startActivity(new Intent(mContext, LevelerActivity.class));
        }
        if(clickItem.equals(getResources().getString(R.string.installation_tool_satellite))) {
            //卫星收信状态
            startActivity(new Intent(mContext, SatelliteReceptionStatus.class));
        }


    }




}
