package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_system_settings),menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i("SetingActivity", "=position==" + position);
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_system_settings_date_time))) {
            secondMenuItem = getResources().getStringArray(R.array.date_time);
            Intent intent = new Intent(mContext, DateTimeSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_notification_sound_volume))) {
            Intent intent = new Intent(mContext, NotificationSoundSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_playback_volume))) {
            Intent intent = new Intent(mContext, PlaybackSoundSetting.class);
            startActivity(intent);
        }else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_preview))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_lcd_brightness))) {
            Intent intent = new Intent();
            intent.setClass(mContext, LCDBrightnessSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_screen_power_saving))) {
            secondMenuItem = getResources().getStringArray(R.array.montior);
            Intent intent = new Intent(mContext, MonitorScreenPowerSavingSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_tool))) {
            secondMenuItem = getResources().getStringArray(R.array.system_settings_installation_tool);
            Intent intent = new Intent(mContext, InstallationToolSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_bluetooth))) {
//            startActivity(new Intent(this, BluetoothSetting.class));
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_infrared_led))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_setting_initialization))) {
            //恢复出厂设置
            showDialog(this);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_wizard))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_2nd_camera))) {
            String[] secondCameraMenuItem = getResources().getStringArray(R.array.secend_camera_array);
            setViewAndData(list_view, vp_progress, secondCameraMenuItem);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_update))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_information))) {
            startActivity(new Intent(this, SystemInformation.class));
        }
    }

    private void showDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure to restore factory Settings?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
