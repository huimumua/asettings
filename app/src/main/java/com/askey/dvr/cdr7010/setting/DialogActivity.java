package com.askey.dvr.cdr7010.setting;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.controller.SdcardFormatAsyncTask;
import com.askey.dvr.cdr7010.setting.module.system.ui.LevelerActivity;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.widget.CommDialog;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/7/2 11:09
 * 修改人：skysoft
 * 修改时间：2018/7/2 11:09
 * 修改备注：
 */
public class DialogActivity extends BaseActivity implements SdcardFormatAsyncTask.PartitionCallback{
    private static final String TAG = "DialogActivity";
    private SdcardFormatAsyncTask sdcardFormatAsyncTask;
    private CommDialog commDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setRightView(true,R.drawable.tag_menu_sub_right,true,R.drawable.tag_menu_sub_ok,true,R.drawable.tag_menu_sub_left);

        String index_dalog =  getIntent().getStringExtra("index_dialog");
        if("sdcard_init".equals(index_dalog)){
            startSdcardInitDialog();
        }else if("system_init".equals(index_dalog)){
            startSytemInitDialog();
        }else if("system_update".equals(index_dalog)){
            startSytemUpdateDialog();
        }

//            jvcRelativeLayout.setBottom_img(R.drawable.tag_menu_sub_left);
//            jvcRelativeLayout.setTop_img(R.drawable.tag_menu_sub_right);
//            jvcRelativeLayout.setCenter_img(R.drawable.tag_menu_sub_ok);

//            jvcRelativeLayout.setBottom_img(R.drawable.tag_menu_main_movedown);
//            jvcRelativeLayout.setTop_img(R.drawable.tag_menu_main_moveup);
//            jvcRelativeLayout.setCenter_img(R.drawable.tag_menu_main_enter);

    }

    private void startSytemUpdateDialog() {
        showDialog(this, getString(R.string.sure_to_update), okListener, cancelListener);
    }

    private void startSytemInitDialog() {
        //恢复出厂设置
        showDialog(this, getResources().getString(R.string.sure_to_restore_factory_Settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
                finish();
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
                    setRightView(false,R.drawable.tag_menu_sub_right,true,R.drawable.tag_menu_sub_ok,false,R.drawable.tag_menu_sub_left);
                    showFormatResultDialog(0,getResources().getString(R.string.sdcard_init_ongoing));
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
        if(commDialog!=null){
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
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
                    if(commDialog!=null && commDialog.isShowing()){
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
        sdcardFormatAsyncTask = new SdcardFormatAsyncTask(this);
        sdcardFormatAsyncTask.execute();
    }

    private void showFormatResultDialog(int type,String title) {
        showDialog(this,type, title, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
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
                Logg.i(TAG,"====setOnKeyListener== KeyEvent.KEYCODE_BACK=0=");
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
                    Logg.i(TAG,"====setOnKeyListener== KeyEvent.KEYCODE_BACK=1=");
                    if(commDialog!=null && commDialog.isShowing()){
                        commDialog.cancel();
                        finish();
                    }
                }
                return false;
            }
        });
        commDialog.show();
    }


    @Override
    public void onPostExecute(boolean ready) {
        if(ready){
            showFormatResultDialog(1,getResources().getString(R.string.sdcard_init_success));
        }else{
            showFormatResultDialog(1,getResources().getString(R.string.sdcard_init_fail));
        }
    }

    DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent("com.jvckenwood.versionup.UPDATE_START");
            sendBroadcast(intent);
            dialog.dismiss();
            finish();
        }
    };

    DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            finish();
        }
    };

}
