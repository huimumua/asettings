package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.util.SystemDateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ManualDateTimeSetting extends BaseActivity {
    private ImageView iv_date, iv_time, iv_year_line, iv_month_line, iv_day_line,
            iv_hour_line, iv_minutes_line, iv_seconds_line;
    private TextView tv_year, tv_month, tv_day, tv_hour, tv_minutes, tv_seconds;
    private int maxDays;
    private String sysYear, sysMonth, sysDay, sysHour, sysMinute, sysSecond;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_date_time_settings);
        String currentTime = getCurrentTime();
        initView();
        initData();
    }

    private void initView() {
        iv_date = (ImageView) findViewById(R.id.iv_date);
        iv_time = (ImageView) findViewById(R.id.iv_time);
        iv_year_line = (ImageView) findViewById(R.id.iv_year_line);
        iv_month_line = (ImageView) findViewById(R.id.iv_month_line);
        iv_day_line = (ImageView) findViewById(R.id.iv_day_line);
        iv_hour_line = (ImageView) findViewById(R.id.iv_hour_line);
        iv_minutes_line = (ImageView) findViewById(R.id.iv_minutes_line);
        iv_seconds_line = (ImageView) findViewById(R.id.iv_seconds_line);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_minutes = (TextView) findViewById(R.id.tv_minutes);
        tv_seconds = (TextView) findViewById(R.id.tv_seconds);
    }

    private void initData() {
        iv_year_line.setVisibility(View.VISIBLE);
        tv_year.setText(sysYear);
        tv_month.setText(sysMonth);
        tv_day.setText(sysDay);
        tv_hour.setText(sysHour);
        tv_minutes.setText(sysMinute);
        tv_seconds.setText(sysSecond);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (iv_year_line.getVisibility() == View.VISIBLE) {
                    iv_year_line.setVisibility(View.INVISIBLE);
                    iv_month_line.setVisibility(View.VISIBLE);
                } else if (iv_month_line.getVisibility() == View.VISIBLE) {
                    iv_month_line.setVisibility(View.INVISIBLE);
                    iv_day_line.setVisibility(View.VISIBLE);
                    maxDays = SystemDateTime.getDaysByYearMonth(Integer.parseInt(tv_year.getText().toString()), Integer.parseInt(tv_month.getText().toString()));
                    if (Integer.parseInt(tv_day.getText().toString()) > maxDays) {
                        tv_day.setText(maxDays + "");
                    }
                } else if (iv_day_line.getVisibility() == View.VISIBLE) {
                    iv_day_line.setVisibility(View.INVISIBLE);
                    iv_hour_line.setVisibility(View.VISIBLE);
                } else if (iv_hour_line.getVisibility() == View.VISIBLE) {
                    iv_hour_line.setVisibility(View.INVISIBLE);
                    iv_minutes_line.setVisibility(View.VISIBLE);
                } else if (iv_minutes_line.getVisibility() == View.VISIBLE) {
                    iv_minutes_line.setVisibility(View.INVISIBLE);
                    iv_seconds_line.setVisibility(View.VISIBLE);
                } else if (iv_seconds_line.getVisibility() == View.VISIBLE) {
                    //设置时间
                    try {
                        SystemDateTime.setDateTime(Integer.parseInt(tv_year.getText().toString()), Integer.parseInt(tv_month.getText().toString()), Integer.parseInt(tv_day.getText().toString()), Integer.parseInt(tv_hour.getText().toString()), Integer.parseInt(tv_minutes.getText().toString()), Integer.parseInt(tv_seconds.getText().toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (iv_year_line.getVisibility() == View.VISIBLE) {
                    int currentYear = Integer.parseInt(tv_year.getText().toString()) - 1;
                    if (currentYear < 2018) {
                        currentYear = 2099;
                    }
                    tv_year.setText(currentYear + "");
                } else if (iv_month_line.getVisibility() == View.VISIBLE) {
                    int currentMonth = Integer.parseInt(tv_month.getText().toString()) - 1;
                    String curMonth = "";
                    if (currentMonth < 1) {
                        curMonth = 12 + "";
                    } else if (currentMonth < 10) {
                        curMonth = "0" + currentMonth;
                    } else {
                        curMonth = currentMonth + "";
                    }
                    tv_month.setText(curMonth);
                } else if (iv_day_line.getVisibility() == View.VISIBLE) {
                    int currentDay = Integer.parseInt(tv_day.getText().toString()) - 1;
                    String curDay = "";
                    if (currentDay < 1) {
                        curDay = maxDays + "";
                    } else if (currentDay < 10) {
                        curDay = "0" + currentDay;
                    } else {
                        curDay = currentDay + "";
                    }
                    tv_day.setText(curDay);
                } else if (iv_hour_line.getVisibility() == View.VISIBLE) {
                    int currentHour = Integer.parseInt(tv_hour.getText().toString()) - 1;
                    String curHour = "";
                    if (currentHour < 0) {
                        curHour = 23 + "";
                    } else if (currentHour < 10) {
                        curHour = "0" + currentHour;
                    } else {
                        curHour = currentHour + "";
                    }
                    tv_hour.setText(curHour);
                } else if (iv_minutes_line.getVisibility() == View.VISIBLE) {
                    int currentMinutes = Integer.parseInt(tv_minutes.getText().toString()) - 1;
                    String curMinutes = "";
                    if (currentMinutes < 0) {
                        curMinutes = 59 + "";
                    } else if (currentMinutes < 10) {
                        curMinutes = "0" + currentMinutes;
                    } else {
                        curMinutes = currentMinutes + "";
                    }
                    tv_minutes.setText(curMinutes);
                } else if (iv_seconds_line.getVisibility() == View.VISIBLE) {
                    //设置时间
                    int currentSeconds = Integer.parseInt(tv_seconds.getText().toString()) - 1;
                    String curSeconds = "";
                    if (currentSeconds < 0) {
                        curSeconds = 59 + "";
                    } else if (currentSeconds < 10) {
                        curSeconds = "0" + currentSeconds;
                    } else {
                        curSeconds = currentSeconds + "";
                    }
                    tv_seconds.setText(curSeconds);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (iv_year_line.getVisibility() == View.VISIBLE) {
                    int currentYear = Integer.parseInt(tv_year.getText().toString()) + 1;
                    if (currentYear > 2099) {
                        currentYear = 2018;
                    }
                    tv_year.setText(currentYear + "");
                } else if (iv_month_line.getVisibility() == View.VISIBLE) {
                    int currentMonth = Integer.parseInt(tv_month.getText().toString()) + 1;
                    String currMonth = "";
                    if (currentMonth > 12) {
                        currMonth = "01";
                    } else if (currentMonth < 10) {
                        currMonth = "0" + currentMonth;
                    } else {
                        currMonth = currentMonth + "";
                    }
                    tv_month.setText(currMonth);
                } else if (iv_day_line.getVisibility() == View.VISIBLE) {
                    int currentDay = Integer.parseInt(tv_day.getText().toString()) + 1;
                    String curDay = "";
                    if (currentDay > maxDays) {
                        curDay = "01";
                    } else if (currentDay < 10) {
                        curDay = "0" + currentDay;
                    } else {
                        curDay = currentDay + "";
                    }
                    tv_day.setText(curDay);
                } else if (iv_hour_line.getVisibility() == View.VISIBLE) {
                    int currentHour = Integer.parseInt(tv_hour.getText().toString()) + 1;
                    String curHour = "";
                    if (currentHour > 23) {
                        curHour = "00";
                    } else if (currentHour < 10) {
                        curHour = "0" + currentHour;
                    } else {
                        curHour = currentHour + "";
                    }
                    tv_hour.setText(curHour);
                } else if (iv_minutes_line.getVisibility() == View.VISIBLE) {
                    int currentMinutes = Integer.parseInt(tv_minutes.getText().toString()) + 1;
                    String curMinutes = "";
                    if (currentMinutes > 59) {
                        curMinutes = "00";
                    } else if (currentMinutes < 10) {
                        curMinutes = "0" + currentMinutes;
                    } else {
                        curMinutes = currentMinutes + "";
                    }
                    tv_minutes.setText(curMinutes);
                } else if (iv_seconds_line.getVisibility() == View.VISIBLE) {
                    //设置时间
                    int currentSeconds = Integer.parseInt(tv_seconds.getText().toString()) + 1;
                    String curSeconds = "";
                    if (currentSeconds > 59) {
                        curSeconds = "00";
                    } else if (currentSeconds < 10) {
                        curSeconds = "0" + currentSeconds;
                    } else {
                        curSeconds = currentSeconds + "";
                    }
                    tv_seconds.setText(curSeconds);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault());
        String date = sdf.format(new java.util.Date());
        sysYear = date.substring(0, 4);
        sysMonth = date.substring(5, 7);
        sysDay = date.substring(8, 10);
        sysHour = date.substring(11, 13);
        sysMinute = date.substring(14, 16);
        sysSecond = date.substring(17, 19);
        return date;
    }

    @Override
    public void onBackPressed() {
        if (iv_year_line.getVisibility() == View.VISIBLE) {
            finish();
            super.onBackPressed();
        } else if (iv_month_line.getVisibility() == View.VISIBLE) {
            iv_month_line.setVisibility(View.INVISIBLE);
            iv_year_line.setVisibility(View.VISIBLE);
        } else if (iv_day_line.getVisibility() == View.VISIBLE) {
            iv_day_line.setVisibility(View.INVISIBLE);
            iv_month_line.setVisibility(View.VISIBLE);
        } else if (iv_hour_line.getVisibility() == View.VISIBLE) {
            iv_hour_line.setVisibility(View.INVISIBLE);
            iv_day_line.setVisibility(View.VISIBLE);
        } else if (iv_minutes_line.getVisibility() == View.VISIBLE) {
            iv_minutes_line.setVisibility(View.INVISIBLE);
            iv_hour_line.setVisibility(View.VISIBLE);
        } else if (iv_seconds_line.getVisibility() == View.VISIBLE) {
            iv_seconds_line.setVisibility(View.INVISIBLE);
            iv_minutes_line.setVisibility(View.VISIBLE);
        }
    }
}
