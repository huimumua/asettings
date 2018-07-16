package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
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
public class VehicleTypeSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {

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
        initView(getResources().getString(R.string.vehicle_type), R.drawable.icon_submenu_car_types, menuInfo, R.layout.second_menu_layout);
        jvcRelativeLayout.setMarquee_visible(true);
        jvcRelativeLayout.setMarquee_text(getString(R.string.driving_setting_marqueeText_vehicle));
        list_view.setOnItemClickListener(this);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.vehicle_type_mini_sedan))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 0);
        } else if (clickItem.equals(getResources().getString(R.string.vehicle_type_minivan))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 1);
        } else if (clickItem.equals(getResources().getString(R.string.vehicle_type_medium_sedan))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 2);
        } else if (clickItem.equals(getResources().getString(R.string.vehicle_type_rv))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 3);
        } else if (clickItem.equals(getResources().getString(R.string.vehicle_type_large_sedan))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 4);
        } else if (clickItem.equals(getResources().getString(R.string.vehicle_type_suv))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 5);
        } else if (clickItem.equals(getResources().getString(R.string.vehicle_type_others))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.CAR_TYPE, 6);
        }

        ContentResolver contentResolver = getContentResolver();
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
        if (car_type==1) {
            Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
            intent.putExtra("set_wizard_help_index", "set_wizard_help_context_range");
            startActivity(intent);
            finish();
        }
        finish();
    }



}
