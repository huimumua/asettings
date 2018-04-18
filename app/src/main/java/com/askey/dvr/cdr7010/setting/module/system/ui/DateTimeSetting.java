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
 * 创建时间：2018/4/8 13:35
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:35
 * 修改备注：
 */
public class DateTimeSetting extends BaseActivity implements AdapterView.OnItemClickListener {
    private TextView tv_title;
    private ListView list_view;
    private SimpleAdapter simpleAdapter;
    private VerticalProgressBar vp_progress;
    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;
    private int PERPAGECOUNT = 6;
    private int lastPosition;
    private int[] dateTimeInfo = {R.string.tv_automatic, R.string.tv_manual};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_settings);
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
        for (int i = 0; i < dateTimeInfo.length; i++) {
            map = new HashMap<>();
            map.put("date_time_settings_item", getResources().getString(dateTimeInfo[i]));
            dataTotal.add(map);
        }
        if (dataTotal.size() > PERPAGECOUNT) {
            vp_progress.setProgress(0, PERPAGECOUNT, dataTotal.size());
        } else {
            vp_progress.setVisibility(View.INVISIBLE);
        }
        getPerPageData(dataTotal, lastPosition);
        list_view.setVerticalScrollBarEnabled(false);
        simpleAdapter = new SimpleAdapter(this, currentData, R.layout.system_settings_list_item, new String[]{"date_time_settings_item"}, new int[]{R.id.list_item});
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        String clickItem = currentData.get(position).get("date_time_settings_item").toString();
        if (clickItem.equals(getResources().getString(R.string.tv_automatic))) {
            //自动校时
        } else if (clickItem.equals(getResources().getString(R.string.tv_manual))) {
            Intent intent = new Intent();
            intent.setClass(DateTimeSetting.this, ManualDateTimeSetting.class);
            startActivity(intent);
        }
    }

    public void getPerPageData(List<HashMap<String, Object>> dataTotal, int lastPosition) {
        currentData.clear();
        for (int i = lastPosition; i <= dataTotal.size() - 1 && i >= 0 && i < PERPAGECOUNT + lastPosition; i++) {
            currentData.add(dataTotal.get(i));
        }
    }
}
