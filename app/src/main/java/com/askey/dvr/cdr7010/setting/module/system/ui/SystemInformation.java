package com.askey.dvr.cdr7010.setting.module.system.ui;

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
 * 创建时间：2018/4/8 13:49
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:49
 * 修改备注：
 */
public class SystemInformation extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_menu_layout);
        menuInfo = getResources().getStringArray(R.array.system_information);
        initView(menuInfo);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, SystemInfoDetailActivity.class);

        if (menuInfo[position].equals(menuInfo[0])) {
            intent.putExtra("info_type","version");
        }
        if (menuInfo[position].equals(menuInfo[1])) {
            intent.putExtra("info_type","SIM");
        }
        if (menuInfo[position].equals(menuInfo[2])) {
            intent.putExtra("info_type","open");
        }
        startActivity(intent);
    }
}
