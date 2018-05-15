package com.askey.dvr.cdr7010.setting.module.movie.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.platform.AskeySettings;

public class ImpactSensitivityActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener {

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
        initView(getResources().getString(R.string.imapct_sensitivity), menuInfo, R.layout.second_menu_layout);
        jvcRelativeLayout.setMarquee_visible(true);
        jvcRelativeLayout.setMarquee_text(getString(R.string.movie_record_marqueeText_impact));
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 2);//DEFAULT 3
        if (settingValue == 0) {//1
            focusPosition = 0;
        } else if (settingValue == 1) {//2
            focusPosition = 1;
        } else if (settingValue == 2) {//3
            focusPosition = 2;
        } else if (settingValue == 3) {//4
            focusPosition = 3;
        } else if (settingValue == 4) {//5
            focusPosition = 4;
        }
        list_view.setSelection(focusPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals("1")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 0);
        } else if (clickItem.equals("2")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 1);
        } else if (clickItem.equals("3")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 2);
        } else if (clickItem.equals("4")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 3);
        } else if (clickItem.equals("5")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 4);
        }
    }
}
