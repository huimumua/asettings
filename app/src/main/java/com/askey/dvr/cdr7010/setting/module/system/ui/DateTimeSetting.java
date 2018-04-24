package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:35
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:35
 * 修改备注：
 */
public class DateTimeSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_settings);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        list_view = (ListView) findViewById(R.id.list_view);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);
        list_view.setOnItemClickListener(this);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        setViewAndData(list_view, vp_progress, menuInfo);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_automatic))) {
            //自动校时
        } else if (clickItem.equals(getResources().getString(R.string.tv_manual))) {
            Intent intent = new Intent();
            intent.setClass(DateTimeSetting.this, ManualDateTimeSetting.class);
            startActivity(intent);
        }
    }
}
