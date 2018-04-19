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
        setContentView(R.layout.activity_device_install);

        initView();
    }

    private void initView() {
        list_view = findViewById(R.id.list_view);
        vp_progress = findViewById(R.id.vp_progress);

        list_view.setOnItemClickListener(this);

        menuInfo = getResources().getStringArray(R.array.device_install);

        setViewAndData(list_view, vp_progress, menuInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
