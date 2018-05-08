package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.SystemDateTime;
import com.askey.platform.AskeySettings;

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
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_date_time), menuInfo, R.layout.second_menu_layout);
        contentResolver = getContentResolver();
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        boolean isAuto = SystemDateTime.isDateTimeAuto();
        Log.i("DateTimeSetting", "=isAuto==" + isAuto);
//        int datetime = Settings.Global.getInt(contentResolver, Const.SYSSET_auto_datetime, 1);
        int position = 0;
//        if (datetime == 1) {
//            position = 0;
//        } else if (datetime == 0) {
//            position = 1;
//        }
        if (isAuto) {
            position = 0;
        } else {
            position = 1;
        }
        list_view.setSelection(position);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_automatic))) {
            //自动校时
            SystemDateTime.setAutoDateTime(1);
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_AUTO_DATETIME, 1);
        } else if (clickItem.equals(getResources().getString(R.string.tv_manual))) {
            SystemDateTime.setAutoDateTime(0);
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_AUTO_DATETIME, 0);
            Intent intent = new Intent();
            intent.setClass(DateTimeSetting.this, ManualDateTimeSetting.class);
            startActivity(intent);
        }
    }
}
