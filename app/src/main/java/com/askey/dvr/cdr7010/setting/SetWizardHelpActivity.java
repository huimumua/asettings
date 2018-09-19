package com.askey.dvr.cdr7010.setting;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.LevelerActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.MountingPositionSetting;
import com.askey.dvr.cdr7010.setting.module.system.ui.RangeSettingActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.VehicleTypeDetailActivity;
import com.askey.dvr.cdr7010.setting.util.AppUtil;
import com.askey.dvr.cdr7010.setting.util.Const;
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
    private static final String TAG = "SetWizardHelpActivity";
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
        setBottomView(false, R.drawable.tag_menu_main_back);

        String indexStr = "";
        if (null == currentUi || currentUi.equals("")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_start_setting);
            setWizardhelp.setGravity(Gravity.CENTER);
            currentUi = "set_wizard_help_start_setting";
            if (Const.SET_WIZARD) {
                setBottomView(true, R.drawable.tag_menu_main_back);
            }

        } else if (currentUi.equals("set_wizard_help_context_leveler")) {
            indexStr = getResources().getString(R.string.leveler_detail_context1);
//            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.leveler_detail_title);
        } else if (currentUi.equals("set_wizard_help_context_vehicle_type")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
//            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.vehicle_type);
        } else if (currentUi.equals("set_wizard_help_context_mounting_position")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_mounting_position);
//            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.mounting_position);
        } else if (currentUi.equals("set_wizard_help_context_range")) {
            indexStr = getResources().getString(R.string.set_wizard_help_context_range);
//            setBottomView(true, R.drawable.tag_menu_sub_skip);
            title = getResources().getString(R.string.system_range_of);
        } else if (currentUi.equals("set_wizard_help_finish")) {
            indexStr = getResources().getString(R.string.set_wizard_help_finish);
//            setBottomView(false, 0);
            setWizardhelp.setGravity(Gravity.CENTER);
        }
        setTitleView(title);
        setWizardhelp.setText(indexStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //第一次启动设备的设置向导，首页提示不能返回
            if (!Const.SET_WIZARD) {
                return true;
            }

            if (currentUi.equals("set_wizard_help_start_setting")) {
                finishSetWizard();
                return true;
            }
//            else if (currentUi.equals("set_wizard_help_context_leveler")) {
//                currentUi = "set_wizard_help_context_range";
//                String str = getResources().getString(R.string.set_wizard_help_context_range);
//                setWizardhelp.setText(str);
//                title = getResources().getString(R.string.system_range_of);
//                setTitleView(title);
//            } else if (currentUi.equals("set_wizard_help_context_vehicle_type")) {
////                currentUi = "set_wizard_help_context_range";
//                currentUi = "set_wizard_help_context_mounting_position";
////                String str = getResources().getString(R.string.set_wizard_help_context_range);
//                String str = getResources().getString(R.string.set_wizard_help_context_mounting_position);
//                setWizardhelp.setText(str);
////                title = getResources().getString(R.string.system_range_of);
//                title = getResources().getString(R.string.mounting_position);
//                setTitleView(title);
//            } else if (currentUi.equals("set_wizard_help_context_range")) {
////                currentUi = "set_wizard_help_context_mounting_position";
////                String str = getResources().getString(R.string.set_wizard_help_context_mounting_position);
////                setWizardhelp.setText(str);
////                title = getResources().getString(R.string.mounting_position);
////                setTitleView(title);
//                finishSetWizard();
//            } else if (currentUi.equals("set_wizard_help_context_mounting_position")) {
//                currentUi = "set_wizard_help_context_leveler";
//                String str = getResources().getString(R.string.leveler_detail_context1);
//                setWizardhelp.setText(str);
//                title = getResources().getString(R.string.leveler_detail_title);
//                setTitleView(title);
//            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (currentUi.equals("set_wizard_help_start_setting")) {
                Const.SET_WIZARD = true;//为了让第一次启动时能够跳过一些选项
                startActivity(new Intent(mContext, VehicleTypeDetailActivity.class));
            } else if (currentUi.equals("set_wizard_help_context_leveler")) {
                Intent intent = new Intent(mContext, LevelerActivity.class);
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
        return super.onKeyUp(keyCode, event);
    }

    private void finishSetWizard(){
        ContentResolver contentResolver = getContentResolver();
        //1表示没有经过设置向导，0表示已经走过设置向导
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
        Const.SET_WIZARD = false;
        if (car_type == 1) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 0);
            String packageName = "com.askey.dvr.cdr7010.dashcam";
            String className = "com.askey.dvr.cdr7010.dashcam.ui.MainActivity";
            AppUtil.startActivity(mContext, packageName, className, true);
        } else {
            SettingApplication.finishActivity(SetWizardHelpActivity.class);
        }
    }
}
