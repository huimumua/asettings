package com.askey.dvr.cdr7010.setting.module.parking.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordingTimeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private TextView tv_title;
    private ListView list_view;

    private VerticalProgressBar vp_progress;

    private SimpleAdapter simpleAdapter;

    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;

    private int PERPAGECOUNT = 6;
    private int screenHeight;
    private int lastPosition;

    private int[] menuInfo = {R.string.parking_recording_off, R.string.time_10s, R.string.time_20s,R.string.time_30s};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_time);

        initView();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        list_view = (ListView) findViewById(R.id.list_view);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);

        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();

        list_view.setOnItemClickListener(this);
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
        simpleAdapter = new SimpleAdapter(this, currentData, R.layout.system_settings_list_item, new String[]{"menu_item"}, new int[]{R.id.list_item});
        list_view.setAdapter(simpleAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.time_10s))) {

        }
        if(clickItem.equals(getResources().getString(R.string.time_20s))) {

        }
        if(clickItem.equals(getResources().getString(R.string.time_30s))) {

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
                        Log.d("tag", "up" + lastPosition);
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
                                Log.d("tag", "up+another = " + lastPosition);
                                getPerPageData(dataTotal, lastPosition);
                                vp_progress.setProgress(lastPosition, lastPosition+PERPAGECOUNT, dataTotal.size());
                                simpleAdapter.notifyDataSetChanged();
                                list_view.setSelection(dataTotal.size()%PERPAGECOUNT-1);//将最后一页的焦点设置到最后一项
                            } else {
                                lastPosition = dataTotal.size()-PERPAGECOUNT;
                                Log.d("tag", "up+another = " + lastPosition);
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
}
