package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.module.sdcard.controller.SdcardFormatAsyncTask;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.util.SdcardUtil;
import com.askey.dvr.cdr7010.setting.widget.CommDialog;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:29
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:29
 * 修改备注：
 */
public class SdcardSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener, SdcardFormatAsyncTask.PartitionCallback{

    private static final String TAG = "SdcardSetting";
    private Boolean isExist = false;
    private SdcardFormatAsyncTask sdcardFormatAsyncTask;
    private CommDialog commDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.sdcard_setting),R.drawable.icon_submenu_sdcard,menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);

}

    @Override
    protected void onResume() {
        super.onResume();
        isExist = SdcardUtil.checkSdcardExist();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.sdcard_setting_information)) && isExist) {
            startActivity(new Intent(mContext, SdcardInformation.class));
        }
        if(clickItem.equals(getResources().getString(R.string.sdcard_setting_initialization)) /*&& isExist*/) {
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
        }

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


    private void showDialog(Context context, String content, DialogInterface.OnClickListener okListener, DialogInterface.OnClickListener cancelListener) {
        CommDialog commDialog = new CommDialog(context);
        commDialog.setMessage(content);
        commDialog.setDialogWidth(280);
        commDialog.setDialogHeight(200);
        commDialog.setPositiveButtonListener(okListener);
        commDialog.setNegativeButtonListener(cancelListener);
        commDialog.setCancelable(true);
        commDialog.setCanceledOnTouchOutside(false);
        commDialog.show();
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


    @Override
    public void onPostExecute(boolean ready) {
        if(ready){
            showFormatResultDialog(1,getResources().getString(R.string.sdcard_init_success));
        }else{
            showFormatResultDialog(1,getResources().getString(R.string.sdcard_init_fail));
        }
    }

}
