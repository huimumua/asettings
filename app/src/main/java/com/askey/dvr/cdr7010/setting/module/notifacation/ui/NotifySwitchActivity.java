package com.askey.dvr.cdr7010.setting.module.notifacation.ui;

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
import android.widget.TextView;
import android.widget.Toast;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotifySwitchActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView tv_title;

    private ListView list_view;

    private VerticalProgressBar vp_progress;

    private SimpleAdapter simpleAdapter;

    private List<HashMap<String, Object>> dataTotal;
    private List<HashMap<String, Object>> currentData;
    private String switch_tag;
    private int PERPAGECOUNT = 6;
    private int screenHeight;
    private int lastPosition;
    private ContentResolver contentResolver;
    private int settingValue;
    private int focusPosition = 0;
    private String[] menuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        initView();
        initData();
    }

    private void initView() {
        list_view = (ListView) findViewById(R.id.list_view);
        vp_progress = (VerticalProgressBar) findViewById(R.id.vp_progress);
        tv_title = (TextView) findViewById(R.id.tv_title);

        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();

        list_view.setOnItemClickListener(this);
    }

    private void initData() {
        //根据不同的tag类型，去操作contentProvider不同的的字段
        switch_tag = getIntent().getStringExtra("switch_tag");
        Log.i("NotifySwitchActivity", "=switch_tag==" + switch_tag);
        tv_title.setText(switch_tag);
        menuInfo = getResources().getStringArray(R.array.all_switch_item);
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
        contentResolver = getContentResolver();
        if (switch_tag.equals(Const.REVERSE_RUN_DETECTION)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_reverse_run, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.SPEED_REGULATION_AREA)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_speed_limit_area, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.PAUSE)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_stop, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.ACCIDENT_FREQUENTLY_OCCURRING_AREA)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_freq_accident_area, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.DRIVING_TIME)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_driving_time, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.RAPID_ACCELERATION_SUDDEN_DECELERATION)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_Intense_driving, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.HANDLING)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_abnormal_handing, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.FLUCTUATION_DETECTION)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_fluctuation_detection, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.DRIVING_OUTSIDE_THE_DESIGNATED_AREA)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_out_of_area, 0);//Default OFF
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        } else if (switch_tag.equals(Const.DRIVING_REPORT)) {
            settingValue = Settings.Global.getInt(contentResolver, Const.NOTIFY_driving_report, 1);//Default ON
            if (settingValue == 0) {
                focusPosition = 1;//OFF
            } else if (settingValue == 1) {//ON
                focusPosition = 0;
            }
        }
        list_view.setSelection(focusPosition);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
//        if (clickItem.equals(menuInfo[position])) {
//            Toast.makeText(this, menuInfo[position], Toast.LENGTH_SHORT).show();
//        }
        if (switch_tag.equals(Const.REVERSE_RUN_DETECTION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_reverse_run, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_reverse_run, 0);
            }
        } else if (switch_tag.equals(Const.SPEED_REGULATION_AREA)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_speed_limit_area, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_speed_limit_area, 0);
            }
        } else if (switch_tag.equals(Const.PAUSE)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_stop, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_stop, 0);
            }
        } else if (switch_tag.equals(Const.ACCIDENT_FREQUENTLY_OCCURRING_AREA)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_freq_accident_area, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_freq_accident_area, 0);
            }
        } else if (switch_tag.equals(Const.DRIVING_TIME)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_driving_time, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_driving_time, 0);
            }
        } else if (switch_tag.equals(Const.RAPID_ACCELERATION_SUDDEN_DECELERATION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_Intense_driving, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_Intense_driving, 0);
            }
        } else if (switch_tag.equals(Const.HANDLING)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_abnormal_handing, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_abnormal_handing, 0);
            }
        } else if (switch_tag.equals(Const.FLUCTUATION_DETECTION)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_fluctuation_detection, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_fluctuation_detection, 0);
            }
        } else if (switch_tag.equals(Const.DRIVING_OUTSIDE_THE_DESIGNATED_AREA)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_out_of_area, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_out_of_area, 0);
            }
        } else if (switch_tag.equals(Const.DRIVING_REPORT)) {
            if (clickItem.equals(Const.ON)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_driving_report, 1);
            } else if (clickItem.equals(Const.OFF)) {
                Settings.Global.putInt(contentResolver, Const.NOTIFY_driving_report, 0);
            }
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
