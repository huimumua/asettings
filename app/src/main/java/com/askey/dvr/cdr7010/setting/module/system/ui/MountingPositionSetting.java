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
import com.askey.dvr.cdr7010.setting.util.Const;
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
        initView(getResources().getString(R.string.mounting_position),R.drawable.icon_submenu_setting,menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        contentResolver = getContentResolver();
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        jvcRelativeLayout.setMarquee_visible(true);
        jvcRelativeLayout.setMarquee_text(getString(R.string.driving_setting_marqueeText_install));
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.ADAS_MOUNT_POSITION, 1);
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

        ContentResolver contentResolver = getContentResolver();
//        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
        if (Const.SET_WIZARD) {
            Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
            intent.putExtra("set_wizard_help_index", "set_wizard_help_context_leveler");
            startActivity(intent);
            finish();
        }
        finish();
    }


}
