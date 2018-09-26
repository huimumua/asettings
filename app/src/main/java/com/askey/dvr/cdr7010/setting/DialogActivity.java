package com.askey.dvr.cdr7010.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.base.BaseKeyAudioActivity;
import com.askey.dvr.cdr7010.setting.module.system.controller.SdcardFormatAsyncTask;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.FileUtils;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.widget.CommDialog;
import com.askey.dvr.cdr7010.setting.widget.CommDialogNoButton;
import com.askey.platform.storage.AskeyStorageManager;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/7/2 11:09
 * 修改人：skysoft
 * 修改时间：2018/7/2 11:09
 * 修改备注：
 */
public class DialogActivity extends BaseKeyAudioActivity implements SdcardFormatAsyncTask.PartitionCallback {
    private static final String TAG = "DialogActivity";
    private SdcardFormatAsyncTask sdcardFormatAsyncTask;
    private CommDialog commDialog;
    private CommDialogNoButton commDialogNoButton;
    private boolean isPreparing = false;
    private VersionUpReceiver upReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(true, R.drawable.tag_menu_sub_right, true, R.drawable.tag_menu_sub_ok, true, R.drawable.tag_menu_sub_left);

        String index_dalog = getIntent().getStringExtra("index_dialog");
        if ("sdcard_init".equals(index_dalog)) {
            startSdcardInitDialog();
        } else if ("system_init".equals(index_dalog)) {
            startSytemInitDialog();
        } else if ("system_update".equals(index_dalog)) {
            startSytemUpdateDialog();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_VERSIONUP_CHECK);
        upReceiver = new VersionUpReceiver();
        registerReceiver(upReceiver, intentFilter);

//            jvcRelativeLayout.setBottom_img(R.drawable.tag_menu_sub_left);
//            jvcRelativeLayout.setTop_img(R.drawable.tag_menu_sub_right);
//            jvcRelativeLayout.setCenter_img(R.drawable.tag_menu_sub_ok);

//            jvcRelativeLayout.setBottom_img(R.drawable.tag_menu_main_movedown);
//            jvcRelativeLayout.setTop_img(R.drawable.tag_menu_main_moveup);
//            jvcRelativeLayout.setCenter_img(R.drawable.tag_menu_main_enter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(upReceiver);
    }

    private void startSytemUpdateDialog() {
        showDialog(this, getString(R.string.sure_to_update), okListener, cancelListener);
    }

    private void startSytemInitDialog() {
        //恢复出厂设置
        showDialog(this, getResources().getString(R.string.sure_to_restore_factory_Settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!Const.isBatteryConnected) {
                    finish();
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.deleteFile("/data/system/users/0/settings_global.xml");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                setRightView(false, true, false);
                                setBottomView(false, R.drawable.tag_menu_main_back);
                                showDialog(DialogActivity.this, CommDialog.TYPE_BUTTON_OK, getString(R.string.dialog_setting_init_finish), false, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(Intent.ACTION_REBOOT);
//                                        intent.putExtra("nowait", 1);
//                                        intent.putExtra("interval", 1);
//                                        intent.putExtra("window", 0);
//                                        sendBroadcast(intent);
//                                        finish();
                                        //恢复出厂设置
                                        sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                }).start();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void startSdcardInitDialog() {
        showDialog(this, getResources().getString(R.string.sdcard_init_prompt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setRightView(false, R.drawable.tag_menu_sub_right, true, R.drawable.tag_menu_sub_ok, false, R.drawable.tag_menu_sub_left);
                setBottomView(false, R.drawable.tag_menu_main_back);
                showDialogNoButton(DialogActivity.this, getResources().getString(R.string.sdcard_init_ongoing), R.drawable.icon_versionup_process, false);
                doSdcardformat();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void showDialog(Context context, String content, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        if (commDialog != null) {
            commDialog.cancel();
        }
        commDialog = new CommDialog(context);
        commDialog.setMessage(content);
        commDialog.setDialogWidth(280);
        commDialog.setDialogHeight(200);
        commDialog.setPositiveButtonListener(okListener);
        commDialog.setNegativeButtonListener(cancelListener);
        commDialog.setCancelable(true);
        commDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    if (commDialog != null && commDialog.isShowing()) {
                        commDialog.cancel();
                        finish();
                    }
                }
                return false;
            }
        });
        commDialog.show();
    }

    private void doSdcardformat() {
        AskeyStorageManager storageManager = AskeyStorageManager.getInstance(this.getApplicationContext());
        sdcardFormatAsyncTask = new SdcardFormatAsyncTask(storageManager, this);
        sdcardFormatAsyncTask.execute();
    }

    private void showFormatResultDialog(int type, String title) {
        showDialog(this, type, title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void showDialog(Context context, int type, String content, DialogInterface.OnClickListener okListener) {
        if (commDialog != null) {
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
                Logg.i(TAG, "====setOnKeyListener== KeyEvent.KEYCODE_BACK=0=");
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }
        });
        commDialog.show();
    }

    private void showDialog(Context context, int type, String content, boolean isBackCancel, DialogInterface.OnClickListener okListener) {
        if (commDialog != null) {
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
        //back键是否能取消弹窗
        if (!isBackCancel) {
            commDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    Logg.i(TAG, "====setOnKeyListener== KeyEvent.KEYCODE_BACK=0=");
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        return true;
                    }
                    return false;
                }
            });
        }
        commDialog.show();
    }

    private void showDialogNoButton(Context context, String content, int iconRes, boolean isShowIcon) {
        if (commDialogNoButton != null) {
            commDialogNoButton.cancel();
        }
        commDialogNoButton = new CommDialogNoButton(context);
        commDialogNoButton.setMessage(content);
        commDialogNoButton.setDialogWidth(280);
        commDialogNoButton.setDialogHeight(200);
        commDialogNoButton.setCancelable(true);
        commDialogNoButton.setCanceledOnTouchOutside(false);
        commDialogNoButton.setIcon(iconRes);
        commDialogNoButton.isShowIcon(isShowIcon);
        commDialogNoButton.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        commDialogNoButton.show();
    }

    @Override
    public void onPostExecute(boolean ready) {
        if (ready) {
            showFormatResultDialog(1, getResources().getString(R.string.sdcard_init_success));
        } else {
            showFormatResultDialog(1, getResources().getString(R.string.sdcard_init_fail));
        }
    }

    DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            setBottomView(false, R.drawable.tag_menu_main_back);
            Intent intent = new Intent("com.jvckenwood.versionup.UPDATE_START");
            sendBroadcast(intent);
            dialog.dismiss();
            showDialogNoButton(DialogActivity.this, getString(R.string.dialog_version_up_preparing), R.drawable.icon_versionup_process, true);
        }
    };

    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            finish();
        }
    };

    class VersionUpReceiver extends BroadcastReceiver {
        private String str;

        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra("result", 0);
            Log.d(TAG, "onReceive: " + status);
            if (status == 0) {
                return;
            }
            switch (status) {
                case -1:
                    str = getString(R.string.dialog_version_up_1);
                    break;
                case -2:
                    str = getString(R.string.dialog_version_up_4);
                    break;
                case -3:
                    str = getString(R.string.dialog_version_up_4);
                    break;
//                case -4:
//                    str = getString(R.string.dialog_version_up_4);
//                    break;
                default:
                    finish();
                    return;

            }
            commDialogNoButton.dismiss();
            showDialog(context, CommDialog.TYPE_BUTTON_OK, str, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
        }
    }

}
