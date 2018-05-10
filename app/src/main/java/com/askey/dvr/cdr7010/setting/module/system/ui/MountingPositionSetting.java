package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.module.dirving.ui.RangeSettingActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/28 10:24
 * 修改人：skysoft
 * 修改时间：2018/4/28 10:24
 * 修改备注：
 */
public class MountingPositionSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "MountingPositionSetting";
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.mounting_position),menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        contentResolver = getContentResolver();
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_MOUNT_POSITION, 0);
        list_view.setSelection(car_type);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.mounting_position_left)) ) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_MOUNT_POSITION, 0);
        }else if(clickItem.equals(getResources().getString(R.string.mounting_position_middle)) ) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_MOUNT_POSITION, 1);
        }else if(clickItem.equals(getResources().getString(R.string.mounting_position_right)) ) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.ADAS_MOUNT_POSITION, 2);
        }

        boolean isFirstInit = (boolean) PreferencesUtils.get(mContext, Const.SETTTING_FIRST_INIT,true);
        if(isFirstInit){
            Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
            intent.putExtra("set_wizard_help_index", "set_wizard_help_context_range");
            startActivity(intent);
            finish();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean isFirstInit = (boolean) PreferencesUtils.get(mContext, Const.SETTTING_FIRST_INIT, true);
            if (isFirstInit) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
