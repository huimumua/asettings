package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.DialogActivity;
import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.controller.FileManager;
import com.askey.dvr.cdr7010.setting.util.Const;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:31
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:31
 * 修改备注：
 */

public class SystemSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "SystemSetting";
    private String[] secondMenuItem;
    private SDcardReceiver sdCardReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        if (Camera.getNumberOfCameras() == 1) {
            menuInfo = delete(menuInfo, 4);
        }
        initView(getResources().getString(R.string.tv_system_settings), R.drawable.icon_submenu_setting, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action_sdcard_status");
        sdCardReceiver = new SDcardReceiver();
        registerReceiver(sdCardReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sdCardReceiver);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i("SetingActivity", "=position==" + position);
        int sdCardStatus = FileManager.getInstance().getSdcardStatus(this);
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_system_settings_notification_sound_volume))) {
            Intent intent = new Intent(mContext, NotificationSoundSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_playback_volume))) {
            Intent intent = new Intent(mContext, PlaybackSoundSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_preview))) {
            Intent intent = new Intent(mContext, PreviewActivity.class);
            intent.putExtra("cameraId",0);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_preview_back))) {
            Intent intent = new Intent(mContext, PreviewActivity.class);
            intent.putExtra("cameraId",1);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_lcd_brightness))) {
            Intent intent = new Intent();
            intent.setClass(mContext, LCDBrightnessSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_screen_power_saving))) {
            secondMenuItem = getResources().getStringArray(R.array.montior);
            Intent intent = new Intent(mContext, MonitorScreenPowerSavingSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_wizard))) {
//            ContentResolver contentResolver = getContentResolver();
            Const.SET_WIZARD = true;
//            Settings.Global.putInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
            Intent intent = new Intent(mContext, SetWizardHelpActivity.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_vehicle_type))) {
            secondMenuItem = getResources().getStringArray(R.array.vehicle_type);
            Intent intent = new Intent(mContext, VehicleTypeSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.system_range_of))) {
            startActivity(new Intent(SystemSetting.this, RangeSettingActivity.class));
        } else if (clickItem.equals(getResources().getString(R.string.device_install_setting))) {
            secondMenuItem = getResources().getStringArray(R.array.mounting_position);
            Intent intent = new Intent(SystemSetting.this, MountingPositionSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_tool))) {
            secondMenuItem = getResources().getStringArray(R.array.system_settings_installation_tool);
            Intent intent = new Intent(mContext, InstallationToolSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_setting_initialization))) {
            Intent intent = new Intent(mContext, DialogActivity.class);
            intent.putExtra("index_dialog", "system_init");
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.sdcard_setting_information))) {
            if (!(sdCardStatus == Const.SDCARD_NOT_SUPPORT || sdCardStatus == Const.SDCARD_UNRECOGNIZABLE || sdCardStatus == Const.SDCARD_NOT_EXIST)) {
                startActivity(new Intent(mContext, SdcardInformation.class));
            }
        } else if (clickItem.equals(getResources().getString(R.string.sdcard_setting_initialization))) {
            if (!(sdCardStatus == Const.SDCARD_NOT_SUPPORT || sdCardStatus == Const.SDCARD_UNRECOGNIZABLE || sdCardStatus == Const.SDCARD_NOT_EXIST) || sdCardStatus == Const.SDCARD_NOT_SUPPORT) {
                Intent intent = new Intent(mContext, DialogActivity.class);
                intent.putExtra("index_dialog", "sdcard_init");
                startActivity(intent);
            }
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_update))) {
            if (!(sdCardStatus == Const.SDCARD_NOT_SUPPORT || sdCardStatus == Const.SDCARD_UNRECOGNIZABLE || sdCardStatus == Const.SDCARD_NOT_EXIST)) {
                Intent intent = new Intent(mContext, DialogActivity.class);
                intent.putExtra("index_dialog", "system_update");
                startActivity(intent);
            }
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_information))) {
            startActivity(new Intent(this, SystemInformation.class));
        }
//        else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_2nd_camera))) {
//            String[] secondCameraMenuItem = getResources().getStringArray(R.array.secend_camera_array);
//            setViewAndData(list_view, vp_progress, secondCameraMenuItem);
//        }
    }

    class SDcardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            myAdapter.notifyDataSetChanged();
        }
    }

    public static String[] delete(String a[], int index) {
        String b[] = new String[a.length - 1];
        for (int i = 0; i < b.length; i++) {
            if (i < index - 1) {
                b[i] = a[i];
            } else {
                b[i] = a[i + 1];
            }
        }
        return b;
    }


}
