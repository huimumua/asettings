package com.askey.dvr.cdr7010.setting.module.movie.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.platform.AskeySettings;

public class InformationStampActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private ContentResolver contentResolver;
    private String[] menuInfo;
    private int settingValue;
    private int focusPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);
        contentResolver = getContentResolver();
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.information_stamp_record), menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.RECSET_INFO_STAMP, 1);
        if (settingValue == 0) {
            focusPosition = 1;//OFF
        } else if (settingValue == 1) {//ON
            focusPosition = 0;
        }
        list_view.setSelection(focusPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(Const.ON)) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_INFO_STAMP, 1);
        } else if (clickItem.equals(Const.OFF)) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_INFO_STAMP, 0);
        }
    }
}
