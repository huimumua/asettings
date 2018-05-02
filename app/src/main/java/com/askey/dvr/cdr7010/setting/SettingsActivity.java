package com.askey.dvr.cdr7010.setting;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.controller.FileManager;
import com.askey.dvr.cdr7010.setting.module.communication.ui.CommunicationSetting;
import com.askey.dvr.cdr7010.setting.module.dirving.ui.DrivingSetting;
import com.askey.dvr.cdr7010.setting.module.movie.ui.MovieRecordSetting;
import com.askey.dvr.cdr7010.setting.module.notifacation.ui.NotificationSetting;
import com.askey.dvr.cdr7010.setting.module.parking.ui.ParkingRecordSetting;
import com.askey.dvr.cdr7010.setting.module.sdcard.ui.SdcardSetting;
import com.askey.dvr.cdr7010.setting.module.service.ui.ServiceSetting;
import com.askey.dvr.cdr7010.setting.module.system.controller.GPSStatusManager;
import com.askey.dvr.cdr7010.setting.module.system.ui.SystemSetting;
import com.askey.dvr.cdr7010.setting.module.vehicle.ui.VehicleTypeSetting;
import com.askey.dvr.cdr7010.setting.util.AppUtil;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener{
    private final String LOG_TAG = "SettingsActivity";
    private TextView tv_title;
    private ListView list_view;
    private ImageView iv_icon;
    private VerticalProgressBar vp_progress;

    private SimpleAdapter simpleAdapter;

    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;

    private int PERPAGECOUNT = 6;
    private int screenHeight;
    private int lastPosition;

    private int[] menuInfo = {R.string.main_menu_fp,R.string.main_menu_prs,R.string.main_menu_mirs,R.string.main_menu_vt,R.string.main_menu_dsfs
    ,R.string.main_menu_nsg,R.string.main_menu_ss,R.string.main_menu_scm,R.string.main_menu_si,R.string.main_menu_cs};
    private String[] secondMenuItem;
    private int SDCARD_REQUEST_CODE = 10001;//SD卡读写
    private int LOCATION_REQUEST_CODE = 10002;//GPS位置权限


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        requestSdcardPermission();

        initView();
        initData();

        FileManager.getInstance().bindFileManageService();
        GPSStatusManager.getInstance().recordLocation(true);

        boolean isFirstInit = (boolean) PreferencesUtils.get(mContext,Const.SETTTING_FIRST_INIT,true);
        if(isFirstInit){
            String []secondMenuItem = getResources().getStringArray(R.array.vehicle_type);
            Intent intent = new Intent(mContext, VehicleTypeSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }
        //向系统setting里面新增我们需要的字段值，name值为系统下的字段值，自定义的貌似不行
//        Settings.Global.putInt(getContentResolver(), "LED",4);

//        try {
//            Log.i("tag", Settings.Global.getInt(getContentResolver(), "LED")+"");
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        list_view = (ListView) findViewById(R.id.list_view);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);

        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();

        list_view.setOnItemClickListener(this);
        list_view.setOnItemSelectedListener(this);
    }

    private void initData(){

        lastPosition = 0;
        screenHeight = Utils.getScreenHeight(this);

        HashMap<String, Object> map;

        for (int i=0; i <menuInfo.length; i++) {
            map = new HashMap<>();
            map.put("menu_item",getString(menuInfo[i]));
            dataTotal.add(map);
        }

        if(dataTotal.size()>PERPAGECOUNT) {
            vp_progress.setProgress(0, PERPAGECOUNT, dataTotal.size());
        }else {
            vp_progress.setVisibility(View.INVISIBLE);
        }

        getPerPageData(dataTotal,lastPosition);

        list_view.setVerticalScrollBarEnabled(false);
        simpleAdapter = new SimpleAdapter(this, currentData, R.layout.menu_list_item, new String[]{"menu_item"}, new int[]{R.id.list_item});
        list_view.setAdapter(simpleAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.main_menu_ss))) {
            secondMenuItem = getResources().getStringArray(R.array.system_setting);
            Intent intent = new Intent(mContext, SystemSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if (clickItem.equals(getString(R.string.main_menu_prs))) {
            secondMenuItem = getResources().getStringArray(R.array.parking_record);
            Intent intent = new Intent(mContext, ParkingRecordSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if (clickItem.equals(getString(R.string.main_menu_mirs))) {
            secondMenuItem = getResources().getStringArray(R.array.movie_record);
            Intent intent = new Intent(mContext, MovieRecordSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if(clickItem.equals(getString(R.string.main_menu_fp))){
            AppUtil.runAppWithPackageName(mContext, Const.PLAY_BACK_PAKAGE);
        }else if(clickItem.equals(getString(R.string.main_menu_scm))){
            secondMenuItem = getResources().getStringArray(R.array.sdcard_record);
            Intent intent = new Intent(mContext, SdcardSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if (clickItem.equals(getString(R.string.main_menu_dsfs))) {
            secondMenuItem = getResources().getStringArray(R.array.driving_support);
            Intent intent = new Intent(mContext, DrivingSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        } else if (clickItem.equals(getString(R.string.main_menu_nsg))) {
            secondMenuItem = getResources().getStringArray(R.array.notify_sound_guidance);
            Intent intent = new Intent(mContext, NotificationSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if (clickItem.equals(getString(R.string.main_menu_vt))) {
            secondMenuItem = getResources().getStringArray(R.array.vehicle_type);
            Intent intent = new Intent(mContext, VehicleTypeSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if (clickItem.equals(getString(R.string.main_menu_cs))) {
            secondMenuItem = getResources().getStringArray(R.array.communication);
            Intent intent = new Intent(mContext, CommunicationSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }else if (clickItem.equals(getString(R.string.main_menu_si))) {
            secondMenuItem = getResources().getStringArray(R.array.service_information);
            Intent intent = new Intent(mContext, ServiceSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //根据需求自己修改
        if (position%2 == 0) {
            iv_icon.setImageDrawable(getDrawable(R.drawable.ic_info_black_24dp));
        }else {
            iv_icon.setImageDrawable(getDrawable(R.drawable.ic_notifications_black_24dp));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (!Utils.isFastDoubleClick()) {
                    lastPosition += PERPAGECOUNT;
                    Log.d(LOG_TAG, "down" + lastPosition);
                    if (lastPosition < dataTotal.size()) {
                        getPerPageData(dataTotal, lastPosition);
                    }else {
                        lastPosition = 0;
                        getPerPageData(dataTotal, lastPosition);
                    }
                    vp_progress.setProgress(lastPosition, lastPosition+PERPAGECOUNT, dataTotal.size());
                    simpleAdapter.notifyDataSetChanged();
                    list_view.setSelection(0);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!Utils.isFastDoubleClick()) {
                    if(lastPosition>=PERPAGECOUNT) {
                        Log.d(LOG_TAG, "up" + lastPosition);
                        lastPosition -= PERPAGECOUNT;
                        if (lastPosition >= 0) {
                            getPerPageData(dataTotal, lastPosition);
                            vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                            simpleAdapter.notifyDataSetChanged();
                            list_view.setSelection(PERPAGECOUNT-1);
                        }
                    } else {
                        if (lastPosition == 0) {
                            if (dataTotal.size()%PERPAGECOUNT != 0) {
                                lastPosition = dataTotal.size()-dataTotal.size()%PERPAGECOUNT;
                                Log.d(LOG_TAG, "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition+PERPAGECOUNT, dataTotal.size());
                                simpleAdapter.notifyDataSetChanged();
                                list_view.setSelection(dataTotal.size()%PERPAGECOUNT-1);//将最后一页的焦点设置到最后一项
                            } else {
                                lastPosition = dataTotal.size()-PERPAGECOUNT;
                                Log.d(LOG_TAG, "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition+PERPAGECOUNT, dataTotal.size());
                                simpleAdapter.notifyDataSetChanged();
                                list_view.setSelection(PERPAGECOUNT-1);//将最后一页的焦点设置到最后一项
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
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            String setSdcardDetail = getResources().getString(R.string.set_sdcard_permission);
            String inserted_ok = getResources().getString(R.string.inserted_ok);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setMessage(setSdcardDetail)
                        .setPositiveButton(inserted_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) mContext,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SDCARD_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SDCARD_REQUEST_CODE);
            }
        } else {
            Log.i(LOG_TAG, "requestSdcardPermission:true");
            requestLocationPermission();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限
            String setlocationDetail = getResources().getString(R.string.set_location_permission);
            String inserted_ok = getResources().getString(R.string.inserted_ok);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setMessage(setlocationDetail)
                        .setPositiveButton(inserted_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //定位权限
                                ActivityCompat.requestPermissions((Activity) mContext,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //定位权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        } else {
            Logg.i(LOG_TAG, "requestLocationPermission:true");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileManager.getInstance().unBindFileManageService();
        GPSStatusManager.getInstance().recordLocation(false);
    }

}
