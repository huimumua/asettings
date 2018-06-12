package com.askey.dvr.cdr7010.setting.module.emergency.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:33
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:33
 * 修改备注：
 */
public class EmergencySetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "SdcardSetting";
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        contentResolver = getContentResolver();
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.CAR_TYPE, 2);
        setIndex(car_type);
        initView(getResources().getString(R.string.main_menu_em),R.drawable.icon_submenu_car_types, menuInfo, R.layout.second_menu_layout);
        jvcRelativeLayout.setMarquee_visible(false);
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.emergency_contacts))) {
//            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 0);
            Toast.makeText(this, clickItem, Toast.LENGTH_SHORT).show();
        } else if (clickItem.equals(getResources().getString(R.string.emergency_automatic_setting))) {
//            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 1);
            startActivity(new Intent(EmergencySetting.this, EmergencySwitchActivity.class));
        }

//        ContentResolver contentResolver = getContentResolver();
//        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
//        if (car_type==1) {
//            Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
//            intent.putExtra("set_wizard_help_index", "set_wizard_help_context_mounting_position");
//            startActivity(intent);
//            finish();
//        }

    }



}
