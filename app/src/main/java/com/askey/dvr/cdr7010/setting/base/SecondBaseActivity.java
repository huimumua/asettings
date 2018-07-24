package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.MyAdapter;
import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.controller.FileManager;
import com.askey.dvr.cdr7010.setting.util.Utils;
import com.askey.dvr.cdr7010.setting.widget.JVCRelativeLayout;
import com.askey.dvr.cdr7010.setting.widget.MyListView;
import com.askey.dvr.cdr7010.setting.widget.VerticalProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by skysoft on 2018/4/17.
 */

public class SecondBaseActivity extends AppCompatActivity {

    private static final String TAG = "SecondBaseActivity";

    protected JVCRelativeLayout jvcRelativeLayout;

    protected MyListView list_view;
    protected static Context mContext;
    private TextView menuTitle;
    private ImageView menuTitle_icon;
    protected VerticalProgressBar vp_progress;

    protected MyAdapter myAdapter;

    protected List<HashMap<String, Object>> dataTotal;
    protected List<HashMap<String, Object>> currentData;

    protected int PERPAGECOUNT = 6;
    protected int screenHeight;
    protected int firstPosition;

    protected String[] menuInfo;

    private int index;//进入界面需要默认选中的item index

    private int pageNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    protected void setIndex(int index) {
        this.index = index + 1;//从1开始算
    }

    protected void initView(String title, int titleIconRes, String[] menuInfo, int layoutId) {

        jvcRelativeLayout = (JVCRelativeLayout) findViewById(R.id.root);
        jvcRelativeLayout.setContentView(layoutId);
        View view = jvcRelativeLayout.getMyView();

        list_view = (MyListView) view.findViewById(R.id.list_view);
        list_view.setVerticalScrollBarEnabled(false);
        list_view.setDivider(null);
        menuTitle = (TextView) view.findViewById(R.id.title_tv);
        menuTitle_icon = (ImageView) view.findViewById(R.id.title_icon);//参数待添加
        menuTitle.setText(title);
        menuTitle_icon.setImageResource(titleIconRes);
        vp_progress = (VerticalProgressBar) view.findViewById(R.id.vp_progress);
        setViewAndData(list_view, vp_progress, menuInfo);
    }

    protected void setViewAndData(MyListView list_view, VerticalProgressBar vp_progress, String[] menuInfo) {
        this.list_view = list_view;
        this.vp_progress = vp_progress;
        this.menuInfo = menuInfo;
        currentData = new ArrayList<>();
        dataTotal = new ArrayList<>();
        initData();
    }

    private void initData() {

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

        //获取总页数
//        pageNum = getPageNum(dataTotal.size());


        if (index <= 6) {
            firstPosition = 0;
        } else {
            pageNum = (index / PERPAGECOUNT - 1) + index % PERPAGECOUNT == 0 ? 0 : 1;
            firstPosition += pageNum * PERPAGECOUNT;
        }
        getPerPageData(dataTotal, firstPosition);

        myAdapter = new MyAdapter(this, currentData, R.layout.system_settings_list_item);
        myAdapter.setFileManager(FileManager.getInstance());
        list_view.setAdapter(myAdapter);
        if (index > 6) {
            list_view.setSelection(index % PERPAGECOUNT - 1);
            vp_progress.setProgress(firstPosition, firstPosition + PERPAGECOUNT, dataTotal.size());
        } else {
            list_view.setSelection(index - 1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event.startTracking();
        Log.d(TAG, "onKeyDownSecond: ");
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (!Utils.isFastDoubleClick() && null != dataTotal && dataTotal.size() > 0) {
                    firstPosition += PERPAGECOUNT;
                    Log.d("tag", "down" + firstPosition);
                    if (firstPosition < dataTotal.size()) {
                        getPerPageData(dataTotal, firstPosition);
                    } else {
                        firstPosition = 0;
                        getPerPageData(dataTotal, firstPosition);
                    }
                    vp_progress.setProgress(firstPosition, firstPosition + PERPAGECOUNT, dataTotal.size());
                    myAdapter.notifyDataSetChanged();
                    list_view.setSelection(0);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!Utils.isFastDoubleClick() && null != dataTotal && dataTotal.size() > 0) {
                    if (firstPosition >= PERPAGECOUNT) {
                        Log.d("tag", "up" + firstPosition);
                        firstPosition -= PERPAGECOUNT;
                        if (firstPosition >= 0) {
                            getPerPageData(dataTotal, firstPosition);
                            vp_progress.setProgress(firstPosition, firstPosition + PERPAGECOUNT, dataTotal.size());
                            myAdapter.notifyDataSetChanged();
                            list_view.setSelection(PERPAGECOUNT - 1);
                        }
                    } else {
                        if (firstPosition == 0) {
                            if (dataTotal.size() % PERPAGECOUNT != 0) {
                                firstPosition = dataTotal.size() - dataTotal.size() % PERPAGECOUNT;
                                Log.d("tag", "up+another = " + firstPosition);
                                getPerPageData(dataTotal, firstPosition);
                                vp_progress.setProgress(firstPosition, firstPosition + PERPAGECOUNT, dataTotal.size());
                                myAdapter.notifyDataSetChanged();
                                list_view.setSelection(dataTotal.size() % PERPAGECOUNT - 1);//将最后一页的焦点设置到最后一项
                            } else {
                                firstPosition = dataTotal.size() - PERPAGECOUNT;
                                Log.d("tag", "up+another = " + firstPosition);
                                getPerPageData(dataTotal, firstPosition);
                                vp_progress.setProgress(firstPosition, firstPosition + PERPAGECOUNT, dataTotal.size());
                                myAdapter.notifyDataSetChanged();
                                list_view.setSelection(PERPAGECOUNT - 1);//将最后一页的焦点设置到最后一项
                            }
                        }
                    }

                }
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    protected void getPerPageData(List<HashMap<String, Object>> dataTotal, int lastPosition) {
        currentData.clear();
        for (int i = lastPosition; i <= dataTotal.size() - 1 && i >= 0 && i < PERPAGECOUNT + lastPosition; i++) {
            currentData.add(dataTotal.get(i));
        }
    }

}
