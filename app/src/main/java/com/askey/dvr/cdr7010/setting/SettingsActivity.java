package com.askey.dvr.cdr7010.setting;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.askey.dvr.cdr7010.filemanagement.IAskeySettingsAidlInterface;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.controller.FileManager;
import com.askey.dvr.cdr7010.setting.module.driving.ui.DrivingSetting;
import com.askey.dvr.cdr7010.setting.module.emergency.ui.EmergencySetting;
import com.askey.dvr.cdr7010.setting.module.movie.ui.MovieRecordSetting;
import com.askey.dvr.cdr7010.setting.module.service.ui.ServiceSetting;
import com.askey.dvr.cdr7010.setting.module.system.ui.SystemSetting;
import com.askey.dvr.cdr7010.setting.util.AppUtil;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.MyListView;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;
import com.askey.platform.AskeySettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private final String TAG = "SettingsActivity";
    private MyListView list_view;
    private ImageView iv_icon;
    private VerticalProgressBar vp_progress;

    private MyAdapter mAdapter;

    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;

    private int PERPAGECOUNT = 6;
    private int lastPosition;

    private int[] menuInfo = {R.string.main_menu_fp, R.string.main_menu_mirs, R.string.main_menu_em, R.string.main_menu_dsfs
            , R.string.main_menu_ss, R.string.main_menu_all_query};
    private static final int SDCARD_REQUEST_CODE = 10001;//SD卡读写
    private static final int LOCATION_REQUEST_CODE = 10002;//GPS位置权限

    private IAskeySettingsAidlInterface askeySettingsAidlInterface;

    private SDcardReceiver sdCardReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        requestSdcardPermission();
        initView();
        initSetting();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action_sdcard_status");
        sdCardReceiver = new SDcardReceiver();
        registerReceiver(sdCardReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //播放器把文件全部删除后会回到menu界面，此时录影item要变灰，具体逻辑写在adapter
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        list_view = (MyListView) findViewById(R.id.list_view);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);

        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();

        list_view.setVerticalScrollBarEnabled(false);
        list_view.setDivider(null);
        list_view.setOnItemClickListener(this);
        list_view.setOnItemSelectedListener(this);
    }

    private void initSetting() {
        initData();
        FileManager.getInstance().bindFileManageService(this);
        Intent settingIntent = new Intent();
        settingIntent.setAction("com.askey.askeysettingservice.action");
        settingIntent.setPackage("com.askey.dvr.cdr7010.filemanagement");
        bindService(settingIntent, mConnection, Context.BIND_AUTO_CREATE);

        ContentResolver contentResolver = getContentResolver();
        int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
        if (car_type == 1) {
            Intent intent = new Intent(mContext, SetWizardHelpActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        lastPosition = 0;
        HashMap<String, Object> map;
        for (int i = 0; i < menuInfo.length; i++) {
            map = new HashMap<>();
            map.put("menu_item", getString(menuInfo[i]));
            dataTotal.add(map);
        }
        if (dataTotal.size() > PERPAGECOUNT) {
            vp_progress.setProgress(0, PERPAGECOUNT, dataTotal.size());
        } else {
            vp_progress.setVisibility(View.INVISIBLE);
        }
        getPerPageData(dataTotal, lastPosition);
        mAdapter = new MyAdapter(this, currentData, R.layout.menu_list_item);
        mAdapter.setFileManager(FileManager.getInstance());
        list_view.setAdapter(mAdapter);
        list_view.postDelayed(new Runnable() {
            @Override
            public void run() {
                list_view.requestFocusFromTouch();
                list_view.setSelection(0);
            }
        }, 300);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        int sdCardStatus = FileManager.getInstance().getSdcardStatus(this);
        String[] secondMenuItem;
        if (clickItem.equals(getResources().getString(R.string.main_menu_ss))) {
            secondMenuItem = getResources().getStringArray(R.array.system_setting);
            Intent intent = new Intent(mContext, SystemSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getString(R.string.main_menu_mirs))) {
            secondMenuItem = getResources().getStringArray(R.array.movie_record);
            Intent intent = new Intent(mContext, MovieRecordSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getString(R.string.main_menu_fp))) {
//            if (!(sdCardStatus == Const.SDCARD_NOT_SUPPORT || sdCardStatus == Const.SDCARD_UNRECOGNIZABLE || sdCardStatus == Const.SDCARD_NOT_EXIST)) {
//                AppUtil.runAppWithPackageName(mContext, Const.PLAY_BACK_PAKAGE);
//            }
            if (Const.isCan2MediaPlayer) {
                AppUtil.runAppWithPackageName(mContext, Const.PLAY_BACK_PAKAGE);
            }
        } else if (clickItem.equals(getString(R.string.main_menu_dsfs))) {
            secondMenuItem = getResources().getStringArray(R.array.driving_support);
            Intent intent = new Intent(mContext, DrivingSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getString(R.string.main_menu_em))) {
            secondMenuItem = getResources().getStringArray(R.array.communication);
            Intent intent = new Intent(mContext, EmergencySetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getString(R.string.main_menu_all_query))) {
            secondMenuItem = getResources().getStringArray(R.array.service_information);
            Intent intent = new Intent(mContext, ServiceSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.main_menu_ss))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_settings);
        } else if (clickItem.equals(getString(R.string.main_menu_mirs))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_movrec_setting);
        } else if (clickItem.equals(getString(R.string.main_menu_fp))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_playback);
        } else if (clickItem.equals(getString(R.string.main_menu_scm))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_sdcard);
        } else if (clickItem.equals(getString(R.string.main_menu_dsfs))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_driving_support);
        } else if (clickItem.equals(getString(R.string.main_menu_nsg))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_audio_guide);
        } else if (clickItem.equals(getString(R.string.main_menu_em))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_ercall);
        } else if (clickItem.equals(getString(R.string.main_menu_cs))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_communication);
        } else if (clickItem.equals(getString(R.string.main_menu_all_query))) {
            iv_icon.setImageResource(R.drawable.img_menu_main_trouble);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: ");
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (!Utils.isFastDoubleClick()) {
                    lastPosition += PERPAGECOUNT;
                    Log.d(TAG, "down" + lastPosition);
                    if (lastPosition < dataTotal.size()) {
                        getPerPageData(dataTotal, lastPosition);
                    } else {
                        lastPosition = 0;
                        getPerPageData(dataTotal, lastPosition);
                    }
                    vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                    mAdapter.notifyDataSetChanged();
                    list_view.setSelection(0);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!Utils.isFastDoubleClick()) {
                    if (lastPosition >= PERPAGECOUNT) {
                        Log.d(TAG, "up" + lastPosition);
                        lastPosition -= PERPAGECOUNT;
                        if (lastPosition >= 0) {
                            getPerPageData(dataTotal, lastPosition);
                            vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                            mAdapter.notifyDataSetChanged();
                            list_view.setSelection(PERPAGECOUNT - 1);
                        }
                    } else {
                        if (lastPosition == 0) {
                            if (dataTotal.size() % PERPAGECOUNT != 0) {
                                lastPosition = dataTotal.size() - dataTotal.size() % PERPAGECOUNT;
                                Log.d(TAG, "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                                mAdapter.notifyDataSetChanged();
                                list_view.setSelection(dataTotal.size() % PERPAGECOUNT - 1);//将最后一页的焦点设置到最后一项
                            } else {
                                lastPosition = dataTotal.size() - PERPAGECOUNT;
                                Log.d(TAG, "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                                mAdapter.notifyDataSetChanged();
                                list_view.setSelection(PERPAGECOUNT - 1);//将最后一页的焦点设置到最后一项
                            }
                        }
                    }

                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void getPerPageData(List<HashMap<String, Object>> dataTotal, int lastPosition) {
        currentData.clear();
        for (int i = lastPosition; i <= dataTotal.size() - 1 && i >= 0 && i < PERPAGECOUNT + lastPosition; i++) {
            currentData.add(dataTotal.get(i));
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestSdcardPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SDCARD_REQUEST_CODE);
        } else {
            Log.i(TAG, "requestSdcardPermission:true");
            requestLocationPermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            Logg.i(TAG, "requestLocationPermission:true");
        }
    }

    @Override
    protected void onDestroy() {
        Logg.d(TAG, "onDestroy....");
        unregisterReceiver(sdCardReceiver);
        try {
            FileManager.getInstance().unBindFileManageService(this);
        } catch (Exception e) {
            Logg.e(TAG, "onDestroy-->Exception" + e.getMessage());
        }
        try {
            unbindService(mConnection);
        } catch (Exception e) {
            //ignore
        }
        super.onDestroy();
    }

    @Override
    public void onKeyShortPressed(int keyCode) {
        super.onKeyShortPressed(keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                try {
                    if (null != askeySettingsAidlInterface) {
                        askeySettingsAidlInterface.sync();
                    }
                    menuTransition(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                super.onBackPressed();
                break;
        }
    }

    public void menuTransition(int status) {
        Intent intent = new Intent(Const.ACTION_MENU_TRANSITION);
        intent.putExtra("status", status);
        sendOutBroadcast(intent);
    }

    private void sendOutBroadcast(Intent intent) {
        sendBroadcastAsUser(intent, android.os.Process.myUserHandle());
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            askeySettingsAidlInterface = IAskeySettingsAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            askeySettingsAidlInterface = null;
        }
    };

    class SDcardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getStringExtra("data"));
            Log.d(TAG, "onReceive: " + FileManager.getInstance().getSdcardStatus(context));
            String status = intent.getStringExtra("data");
            if (status.equals(Const.BROADCAST_SDCARD_MOUNTED) || status.equals(Const.BROADCAST_SDCARD_NOT_EXIST) || status.equals(Const.BROADCAST_SDCARD_NOT_SUPPORTED)) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
