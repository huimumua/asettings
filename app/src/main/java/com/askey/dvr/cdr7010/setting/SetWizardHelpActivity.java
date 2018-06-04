package com.askey.dvr.cdr7010.setting;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.askey.dvr.cdr7010.dashcam.ICommunication;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.dirving.ui.RangeSettingActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.LevelerDetailActivity;
import com.askey.dvr.cdr7010.setting.module.system.ui.MountingPositionSetting;
import com.askey.dvr.cdr7010.setting.module.vehicle.ui.VehicleTypeSetting;
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
    private ICommunication iCommunication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wizard);

        setWizardhelp = (TextView) this.findViewById(R.id.set_wizard_help_context);
        setRightView(false,true,false);
        contentResolver = getContentResolver();

        Intent intent = new Intent();
        intent.setAction("jvcmodule.local.CommuicationService");
        intent.setPackage("com.askey.dvr.cdr7010.dashcam");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        currentUi = getIntent().getStringExtra("set_wizard_help_index");
        String indexStr = "";
        if(null == currentUi || currentUi.equals("")){
            indexStr = getResources().getString(R.string.set_wizard_help_context_start_setting);
            currentUi = "set_wizard_help_start_setting";
            setBottomView(false,R.drawable.tag_menu_sub_skip);
        }else if(currentUi.equals("set_wizard_help_context_leveler")){
            indexStr = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
            setBottomView(true,R.drawable.tag_menu_sub_skip);
        }else if(currentUi.equals("set_wizard_help_context_vehicle_type")){
            indexStr = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
            setBottomView(true,R.drawable.tag_menu_sub_skip);
        }else if(currentUi.equals("set_wizard_help_context_mounting_position")){
            indexStr = getResources().getString(R.string.set_wizard_help_context_mounting_position);
            setBottomView(true,R.drawable.tag_menu_sub_skip);
        }else if(currentUi.equals("set_wizard_help_context_range")){
            indexStr = getResources().getString(R.string.set_wizard_help_context_range);
            setBottomView(true,R.drawable.tag_menu_sub_skip);
        }else if(currentUi.equals("set_wizard_help_finish")){
            indexStr = getResources().getString(R.string.set_wizard_help_finish);
            setBottomView(true,R.drawable.tag_menu_sub_skip);
        }

        setWizardhelp.setText(indexStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(currentUi.equals("set_wizard_help_start_setting")){
               return true;
            }else if(currentUi.equals("set_wizard_help_context_leveler")){
                currentUi = "set_wizard_help_context_vehicle_type";
                String str = getResources().getString(R.string.set_wizard_help_context_vehicle_type);
                setWizardhelp.setText(str);
            }else if(currentUi.equals("set_wizard_help_context_vehicle_type")){
                currentUi = "set_wizard_help_context_mounting_position";
                String str = getResources().getString(R.string.set_wizard_help_context_mounting_position);
                setWizardhelp.setText(str);
            }else if(currentUi.equals("set_wizard_help_context_mounting_position")){
                currentUi = "set_wizard_help_context_range";
                String str = getResources().getString(R.string.set_wizard_help_context_range);
                setWizardhelp.setText(str);
            }else if(currentUi.equals("set_wizard_help_context_range")){
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 0);
                Intent intent = new Intent(mContext,SettingsActivity.class);
                startActivity(intent);
                finish();
            }
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_ENTER){
            if(currentUi.equals("set_wizard_help_start_setting")){
                startActivity(new Intent(mContext, LevelerDetailActivity.class));
                finish();
            }else if(currentUi.equals("set_wizard_help_context_vehicle_type")){
                String[] secondMenuItem = getResources().getStringArray(R.array.vehicle_type);
                Intent intent = new Intent(mContext, VehicleTypeSetting.class);
                intent.putExtra("menu_item", secondMenuItem);
                startActivity(intent);
            }else if(currentUi.equals("set_wizard_help_context_mounting_position")){
                String[] secondMenuItem = getResources().getStringArray(R.array.mounting_position);
                Intent intent = new Intent(mContext, MountingPositionSetting.class);
                intent.putExtra("menu_item", secondMenuItem);
                startActivity(intent);
            }else if(currentUi.equals("set_wizard_help_context_range")){
                Intent intent = new Intent(mContext,RangeSettingActivity.class);
                startActivity(intent);
            }else if(currentUi.equals("set_wizard_help_finish")){
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 0);
                Intent intent = new Intent(mContext,SettingsActivity.class);
                startActivity(intent);
                if (null != iCommunication) {
                    try {
                        iCommunication.endInitialSetup();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                SettingApplication.finishActivity(SetWizardHelpActivity.class);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iCommunication = ICommunication.Stub.asInterface(service);
            if (null != iCommunication) {
                try {
                    iCommunication.startInitialSetup();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
