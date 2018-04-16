package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:31
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:31
 * 修改备注：
 */

public class SystemSetting extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView tv_title;
    private ListView list_view;
    private VerticalProgressBar vp_progress;
    private SimpleAdapter simpleAdapter;
    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;
    private int PERPAGECOUNT = 6;
    private int lastPosition;
    private int[] systemSettingsInfo = {R.string.tv_system_settings_date_time, R.string.tv_system_settings_notification_sound_volume,
            R.string.tv_system_settings_playback_volume, R.string.tv_system_settings_lcd_brightness, R.string.tv_system_settings_screen_power_saving, R.string.tv_system_settings_installation_tool,
            R.string.tv_system_settings_bluetooth, R.string.tv_system_settings_infrared_led, R.string.tv_system_settings_setting_initialization, R.string.tv_system_settings_2nd_camera,
            R.string.tv_system_settings_system_update, R.string.tv_system_settings_system_information};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_settings);
        initView();
        initData();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        list_view = findViewById(R.id.list_view);
        vp_progress = findViewById(R.id.vp_progress);
        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();
        list_view.setOnItemClickListener(this);
    }

    private void initData() {
        lastPosition = 0;
        HashMap<String, Object> map;
        for (int i = 0; i < systemSettingsInfo.length; i++) {
            map = new HashMap<>();
            map.put("system_settings_item", getResources().getString(systemSettingsInfo[i]));
            dataTotal.add(map);
        }
        if (dataTotal.size() > PERPAGECOUNT) {
            vp_progress.setProgress(0, PERPAGECOUNT, dataTotal.size());
        } else {
            vp_progress.setVisibility(View.INVISIBLE);
        }
        getPerPageData(dataTotal, lastPosition);
        list_view.setVerticalScrollBarEnabled(false);
        simpleAdapter = new SimpleAdapter(this, currentData, R.layout.system_settings_list_item, new String[]{"system_settings_item"}, new int[]{R.id.list_item});
        list_view.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (!Utils.isFastDoubleClick()) {
                    lastPosition += PERPAGECOUNT;
                    Log.d("tag", "down" + lastPosition);
                    if (lastPosition < dataTotal.size()) {
                        getPerPageData(dataTotal, lastPosition);
                    } else {
                        lastPosition = 0;
                        getPerPageData(dataTotal, lastPosition);
                    }
                    vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                    simpleAdapter.notifyDataSetChanged();
                    list_view.setSelection(0);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!Utils.isFastDoubleClick()) {
                    if (lastPosition >= PERPAGECOUNT) {
                        Log.d("tag", "up" + lastPosition);
                        lastPosition -= PERPAGECOUNT;
                        if (lastPosition >= 0) {
                            getPerPageData(dataTotal, lastPosition);
                            vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                            simpleAdapter.notifyDataSetChanged();
                            list_view.setSelection(PERPAGECOUNT - 1);
                        }
                    } else {
                        if (lastPosition == 0) {
                            if (dataTotal.size() % PERPAGECOUNT != 0) {
                                lastPosition = dataTotal.size() - dataTotal.size() % PERPAGECOUNT;
                                Log.d("tag", "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                                simpleAdapter.notifyDataSetChanged();
                                list_view.setSelection(dataTotal.size() % PERPAGECOUNT - 1);//将最后一页的焦点设置到最后一项
                            } else {
                                lastPosition = dataTotal.size() - PERPAGECOUNT;
                                Log.d("tag", "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition + PERPAGECOUNT, dataTotal.size());
                                simpleAdapter.notifyDataSetChanged();
                                list_view.setSelection(PERPAGECOUNT - 1);//将最后一页的焦点设置到最后一项
                            }
                        }
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i("SetingActivity", "=position==" + position);
        String clickItem = currentData.get(position).get("system_settings_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_system_settings_date_time))) {
            //            Intent intent = new Intent();
            //            intent.setClass(SystemSetting.this, DateTimeSetting.class);
            //            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_notification_sound_volume))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_playback_volume))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_lcd_brightness))) {
            Intent intent = new Intent();
            intent.setClass(SystemSetting.this, LCDBrightnessSetting.class);
            startActivity(intent);
        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_screen_power_saving))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_installation_tool))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_bluetooth))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_infrared_led))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_setting_initialization))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_2nd_camera))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_update))) {

        } else if (clickItem.equals(getResources().getString(R.string.tv_system_settings_system_information))) {

        }
    }

    public void getPerPageData(List<HashMap<String, Object>> dataTotal, int lastPosition) {
        currentData.clear();
        for (int i = lastPosition; i <= dataTotal.size() - 1 && i >= 0 && i < PERPAGECOUNT + lastPosition; i++) {
            currentData.add(dataTotal.get(i));
        }
    }

}
