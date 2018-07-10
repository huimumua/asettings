package com.askey.dvr.cdr7010.setting;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.LevelerDetailActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.MountingPositionSetting;
import com.askey.dvr.cdr7010.setting.module.system.ui.RangeSettingActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.VehicleTypeSetting;
import com.askey.dvr.cdr7010.setting.util.AppUtil;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/5/9 18:41
 * 修改人：skysoft
 * 修改时间：2018/5/9 18:41
 * 修改备注：
 */
public class SetWizardHelpActivity extends BaseActivity {
    private final String LOG_TAG = "SetWizardHelpActivity";
    private String currentUi = "set_wizard_help_start_setting";
    private TextView setWizardhelp;
    private ContentResolver contentResolver;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wizard);

        setWizardhelp = (TextView) this.findViewById(R.id.set_wizard_help_context);
        setRightView(false, true, false);
        contentResolver = getContentResolver();

        currentUi = getIntent().getStringExtra("set_wizard_help_index");
        String indexStr = "";
        if (null == currentUi || currentUi.equals("")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_start_setting);
            currentUi = "set_wizard_help_start_setting";
            setBottomView(true, R.drawable.tag_menu_main_back);
        } else if (currentUi.equals("set_wizard_help_context_leveler")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.leveler_detail_title);
        } else if (currentUi.equals("set_wizard_help_context_vehicle_type")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.vehicle_type);
        } else if (currentUi.equals("set_wizard_help_context_mounting_position")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_mounting_position);
            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.mounting_position);
        } else if (currentUi.equals("set_wizard_help_context_range")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_range);
            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.system_range_of);
        } else if (currentUi.equals("set_wizard_help_finish")) {
            indexStr = getResources().getString(R.string.set_wizard_help_finish);
            setBottomView(false, 0);
        }
        setTitleView(title);
        setWizardhelp.setText(indexStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentUi.equals("set_wizard_help_start_setting")) {
                finishSetWizard();
                return true;
            } else if (currentUi.equals("set_wizard_help_context_leveler")) {
                currentUi = "set_wizard_help_context_vehicle_type";
                String str = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
                setWizardhelp.setText(str);
                title = getResources().getString(R.string.vehicle_type);
                setTitleView(title);
            } else if (currentUi.equals("set_wizard_help_context_vehicle_type")) {
                currentUi = "set_wizard_help_context_range";
                String str = getResources().getString(R.string.set_wizard_help_context_range);
                setWizardhelp.setText(str);
                title = getResources().getString(R.string.system_range_of);
                setTitleView(title);
            } else if (currentUi.equals("set_wizard_help_context_range")) {
                currentUi = "set_wizard_help_context_mounting_position";
                String str = getResources().getString(R.string.set_wizard_help_context_mounting_position);
                setWizardhelp.setText(str);
                title = getResources().getString(R.string.mounting_position);
                setTitleView(title);
            } else if (currentUi.equals("set_wizard_help_context_mounting_position")) {
                finishSetWizard();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (currentUi.equals("set_wizard_help_start_setting")) {
                startActivity(new Intent(mContext, LevelerDetailActivity.class));
            } else if (currentUi.equals("set_wizard_help_context_vehicle_type")) {
                String[] secondMenuItem = getResources().getStringArray(R.array.vehicle_type);
                Intent intent = new Intent(mContext, VehicleTypeSetting.class);
                intent.putExtra("menu_item", secondMenuItem);
                startActivity(intent);
            } else if (currentUi.equals("set_wizard_help_context_mounting_position")) {
                String[] secondMenuItem = getResources().getStringArray(R.array.mounting_position);
                Intent intent = new Intent(mContext, MountingPositionSetting.class);
                intent.putExtra("menu_item", secondMenuItem);
                startActivity(intent);
            } else if (currentUi.equals("set_wizard_help_context_range")) {
                Intent intent = new Intent(mContext, RangeSettingActivity.class);
                startActivity(intent);
            } else if (currentUi.equals("set_wizard_help_finish")) {
                finishSetWizard();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void finishSetWizard(){
        ContentResolver contentResolver = getContentResolver();
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
        if (car_type == 1 && !Const.SET_WIZARD) {
            String packageName = "com.askey.dvr.cdr7010.dashcam";
            String className = "com.askey.dvr.cdr7010.dashcam.ui.MainActivity";
            AppUtil.startActivity(mContext, packageName, className, true);
        } else {
            Const.SET_WIZARD = false;
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 0);
            SettingApplication.finishActivity(SetWizardHelpActivity.class);
        }
    }
}
