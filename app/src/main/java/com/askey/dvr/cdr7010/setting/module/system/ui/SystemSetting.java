package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RecoverySystem;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.controller.SdcardFormatAsyncTask;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.SdcardUtil;
import com.askey.dvr.cdr7010.setting.widget.CommDialog;
import com.askey.platform.AskeySettings;

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

public class SystemSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener ,SdcardFormatAsyncTask.PartitionCallback{
    private static final String TAG = "SystemSetting";
    private String[] secondMenuItem;

    private Boolean isExist = false;
    private SdcardFormatAsyncTask sdcardFormatAsyncTask;
    private CommDialog commDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.tv_system_settings), R.drawable.icon_submenu_setting, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isExist = SdcardUtil.checkSdcardExist();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i("SetingActivity", "=position==" + position);
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_system_settings_notification_sound_volume))) {
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
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_wizard))) {
            ContentResolver contentResolver = getContentResolver();
            Const.SET_WIZARD=true;
            Settings.Global.putInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
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
            //恢复出厂设置
            showDialog(this, getResources().getString(R.string.sure_to_restore_factory_Settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else if (clickItem.equals(getResources().getString(R.string.sdcard_setting_information)) && isExist) {
            startActivity(new Intent(mContext, SdcardInformation.class));
        } else if (clickItem.equals(getResources().getString(R.string.sdcard_setting_initialization))) {
            showDialog(this, getResources().getString(R.string.sdcard_init_prompt), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    jvcRelativeLayout.setBack_visible(false);
                    jvcRelativeLayout.setTop_visible(false);
                    jvcRelativeLayout.setBottom_visible(false);
                    showFormatResultDialog(0,getResources().getString(R.string.sdcard_init_ongoing));
                    doSdcardformat();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_update))) {
            showDialog(this, getString(R.string.sure_to_update), okListener, cancelListener);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_information))) {
            startActivity(new Intent(this, SystemInformation.class));
        }
//        else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_2nd_camera))) {
//            String[] secondCameraMenuItem = getResources().getStringArray(R.array.secend_camera_array);
//            setViewAndData(list_view, vp_progress, secondCameraMenuItem);
//        }
    }

//    private void showDialog(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(getResources().getString(R.string.sure_to_restore_factory_Settings));
//        builder.setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
//            }
//        });
//        builder.setCancelable(true);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    private void showDialog(Context context, String content, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        CommDialog commDialog = new CommDialog(context);
        commDialog.setMessage(content);
        commDialog.setDialogWidth(280);
        commDialog.setDialogHeight(200);
        commDialog.setPositiveButtonListener(okListener);
        commDialog.setNegativeButtonListener(cancelListener);
        commDialog.setCancelable(true);
        commDialog.show();
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

    private void showFormatResultDialog(int type,String title) {
        showDialog(this,type, title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                jvcRelativeLayout.setBack_visible(true);
                jvcRelativeLayout.setTop_visible(true);
                jvcRelativeLayout.setBottom_visible(true);
            }
        });
    }

    private void showDialog(Context context,int type, String content, DialogInterface.OnClickListener okListener) {
        if(commDialog!=null){
            commDialog.cancel();
        }
        commDialog = new CommDialog(context);
        commDialog.setMessage(content);
        commDialog.setDialogWidth(280);
        commDialog.setDialogHeight(200);
        commDialog.setPositiveButtonListener(okListener);
        commDialog.setCancelable(true);
        commDialog.setCanceledOnTouchOutside(false);
        commDialog.setType(type);
        commDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
                    if(commDialog!=null && commDialog.isShowing()){
                        return true;
                    }
                }
                return false;
            }
        });
        commDialog.show();
    }

    private void doSdcardformat() {
        sdcardFormatAsyncTask = new SdcardFormatAsyncTask(this);
        sdcardFormatAsyncTask.execute();
    }

    DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent("com.jvckenwood.versionup.UPDATE_START");
            sendBroadcast(intent);
            dialog.dismiss();
//            FileUtils.writeFile(Const.COMMAND_PATH, "--update_package=/sdcard/update.zip");
//
//            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            if (powerManager != null && FileUtils.isFileExist(Const.COMMAND_PATH)) {
//                powerManager.reboot("recovery-update");
//            }
        }
    };
    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    @Override
    public void onPostExecute(boolean ready) {
        if(ready){
            showFormatResultDialog(1,getResources().getString(R.string.sdcard_init_success));
        }else{
            showFormatResultDialog(1,getResources().getString(R.string.sdcard_init_fail));
        }
    }
}
