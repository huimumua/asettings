package com.askey.dvr.cdr7010.setting.module.movie.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;
import com.askey.platform.AskeySettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImpactSensitivityActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list_view;

    private VerticalProgressBar vp_progress;

    private SimpleAdapter simpleAdapter;

    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;

    private int PERPAGECOUNT = 6;
    private int screenHeight;
    private int lastPosition;
    private ContentResolver contentResolver;
    private String[] menuInfo;
    private int settingValue;
    private int focusPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impact_sensitivity);
        contentResolver = getContentResolver();
        initView();
        initData();
    }

    private void initView() {
        list_view = (ListView) findViewById(R.id.list_view);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);

        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();

        list_view.setOnItemClickListener(this);
    }

    private void initData() {
        menuInfo = getResources().getStringArray(R.array.impact_setting);
        lastPosition = 0;
        screenHeight = Utils.getScreenHeight(this);

        HashMap<String, Object> map;

        for (int i = 0; i < menuInfo.length; i++) {
            map = new HashMap<>();
            map.put("menu_item", menuInfo[i]);
            dataTotal.add(map);
        }

        if (dataTotal.size() > PERPAGECOUNT) {
            vp_progress.setProgress(0, PERPAGECOUNT, dataTotal.size());
        } else {
            vp_progress.setVisibility(View.INVISIBLE);
        }

        getPerPageData(dataTotal, lastPosition);

        list_view.setVerticalScrollBarEnabled(false);
        simpleAdapter = new SimpleAdapter(this, currentData, R.layout.system_settings_list_item, new String[]{"menu_item"}, new int[]{R.id.list_item});
        list_view.setAdapter(simpleAdapter);
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        settingValue = Settings.Global.getInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 2);//DEFAULT 3
        if (settingValue == 0) {//1
            focusPosition = 0;
        } else if (settingValue == 1) {//2
            focusPosition = 1;
        } else if (settingValue == 2) {//3
            focusPosition = 2;
        } else if (settingValue == 3) {//4
            focusPosition = 3;
        } else if (settingValue == 4) {//5
            focusPosition = 4;
        }
        list_view.setSelection(focusPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals("1")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 0);
        } else if (clickItem.equals("2")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 1);
        } else if (clickItem.equals("3")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 2);
        } else if (clickItem.equals("4")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 3);
        } else if (clickItem.equals("5")) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.RECSET_SHOCK_SENSITIVE, 4);
        }
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

    public void getPerPageData(List<HashMap<String, Object>> dataTotal, int lastPosition) {
        currentData.clear();
        for (int i = lastPosition; i <= dataTotal.size() - 1 && i >= 0 && i < PERPAGECOUNT + lastPosition; i++) {
            currentData.add(dataTotal.get(i));
        }
    }
}
