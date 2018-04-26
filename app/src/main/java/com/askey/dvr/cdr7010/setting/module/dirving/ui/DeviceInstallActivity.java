package com.askey.dvr.cdr7010.setting.module.dirving.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

public class DeviceInstallActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        menuInfo = getResources().getStringArray(R.array.device_install);
        initView(getResources().getString(R.string.imapct_sensitivity),menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
