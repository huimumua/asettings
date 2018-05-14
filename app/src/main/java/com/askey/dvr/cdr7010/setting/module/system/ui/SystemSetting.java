package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.FileUtils;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

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
        initView(getResources().getString(R.string.tv_system_settings), menuInfo, R.layout.second_menu_layout);
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
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_preview))) {
            Intent intent = new Intent(mContext, PreviewActivity.class);
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
            PreferencesUtils.put(mContext,Const.SETTTING_FIRST_INIT,true);
            Intent intent = new Intent(mContext, SetWizardHelpActivity.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_2nd_camera))) {
            String[] secondCameraMenuItem = getResources().getStringArray(R.array.secend_camera_array);
            setViewAndData(list_view, vp_progress, secondCameraMenuItem);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_update))) {
            if (FileUtils.isFileExist(Const.OTA_PACKAGE_PATH)) {
                showDialog(this, "Sure to update ?", okListener, cancelListener);
            } else {
                Toast.makeText(this, "Package is not exist", Toast.LENGTH_SHORT).show();
            }
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_information))) {
            startActivity(new Intent(this, SystemInformation.class));
        }
    }

    private void showDialog(Context context) {
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

    private void showDialog(Context context, String content, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(content);
        builder.setPositiveButton("OK", okListener);
        builder.setNegativeButton("Cancel", cancelListener);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showLoadingDialog(Context context) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage(getString(R.string.check_update));
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.show();
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RecoverySystem.verifyPackage(new File(Const.OTA_PACKAGE_PATH), new RecoverySystem.ProgressListener() {
                        @Override
                        public void onProgress(final int progress) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.setProgress(progress);
                                    if (100 == progress) {
                                        dialog.dismiss();
                                    }
                                }
                            });
                        }
                    }, null);
                } catch (GeneralSecurityException | IOException e) {
                    Log.i("====", "doesImageMatchProduct(): verify Package failed!" + "," + e.getMessage());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Toast.makeText(SystemSetting.this, "Package is not exist or verify failed", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SystemSetting.this, Const.OTA_PACKAGE_PATH, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                try {
                    RecoverySystem.installPackage(mContext, new File(Const.OTA_PACKAGE_PATH));
                } catch (IOException e) {
                    Log.i("========failed", e.getMessage());
                }
            }
        }).start();

    }

    DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            FileUtils.writeFile(Const.COMMAND_PATH, "--update_package=/sdcard/update.zip");

            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (powerManager != null && FileUtils.isFileExist(Const.COMMAND_PATH)) {
                powerManager.reboot("recovery-update");
            }
//            showLoadingDialog(SystemSetting.this);
        }
    };
    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };
}
