package com.askey.dvr.cdr7010.setting.module.user.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

public class UserSelectActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        //数据暂无
//        menuInfo = getIntent().getStringArrayExtra("menu_item");
//        initView("Driving Setting",menuInfo,R.layout.second_menu_layout);
//        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
