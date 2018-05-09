package com.askey.dvr.cdr7010.setting.module.vehicle.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.LevelerDetailActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.MountingPositionSetting;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;
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
        initView(getResources().getString(R.string.vehicle_type), menuInfo, R.layout.second_menu_layout);
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

        boolean isFirstInit = (boolean) PreferencesUtils.get(mContext, Const.SETTTING_FIRST_INIT, true);
        if (isFirstInit) {

            String[] secondMenuItem = getResources().getStringArray(R.array.mounting_position);
            Intent intent = new Intent(mContext, MountingPositionSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);

        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            return true;
        }else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {

            return true;
        }else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {

            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean isFirstInit = (boolean) PreferencesUtils.get(mContext, Const.SETTTING_FIRST_INIT, true);
            if (isFirstInit) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
