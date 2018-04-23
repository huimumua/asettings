package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by skysoft on 2018/4/17.
 */

public class SecondBaseActivity extends AppCompatActivity {

    protected ListView list_view;
    protected static Context mContext;
    protected VerticalProgressBar vp_progress;

    protected SimpleAdapter simpleAdapter;

    protected List<HashMap<String, Object>> dataTotal;
    protected List<HashMap<String, Object>> currentData;

    protected int PERPAGECOUNT = 6;
    protected int screenHeight;
    protected int lastPosition;

    protected String[] menuInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void initView(String[] menuInfo){
        list_view = findViewById(R.id.list_view);
        vp_progress = findViewById(R.id.vp_progress);
        setViewAndData(list_view, vp_progress, menuInfo);
    }

    protected void setViewAndData(ListView list_view, VerticalProgressBar vp_progress, String[] menuInfo) {
        this.list_view = list_view;
        this.vp_progress = vp_progress;
        this.menuInfo = menuInfo;
        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();
        initData();
    }

    private void initData() {

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

        simpleAdapter = new SimpleAdapter(this, currentData, R.layout.system_settings_list_item, new String[]{"menu_item"}, new int[]{R.id.list_item});

        list_view.setVerticalScrollBarEnabled(false);
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

    protected void getPerPageData(List<HashMap<String, Object>> dataTotal, int lastPosition) {
        currentData.clear();
        for (int i = lastPosition; i <= dataTotal.size() - 1 && i >= 0 && i < PERPAGECOUNT + lastPosition; i++) {
            currentData.add(dataTotal.get(i));
        }
    }

}
