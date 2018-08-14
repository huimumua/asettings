package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.adapter.HorizontalListViewAdapter;
import com.askey.dvr.cdr7010.setting.module.system.bean.GpsSvInfo;
import com.askey.dvr.cdr7010.setting.module.system.controller.GPSStatusManager;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.view.HorizontalListView;

import java.util.ArrayList;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/24 15:39
 * 修改人：skysoft
 * 修改时间：2018/4/24 15:39
 * 修改备注：
 */
public class SatelliteReceptionStatus extends BaseActivity implements GPSStatusManager.GpsStatusChangedCallback{
    private static final String TAG = "SatelliteReceptionStatus";
    private ArrayList<GpsSvInfo> gpsStatusList;
    private HorizontalListView hListView;
    private HorizontalListViewAdapter hListViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_installation_satellite);

        gpsStatusList = new ArrayList<GpsSvInfo>();
        GPSStatusManager.getInstance(this,this).recordLocation(true);

        setRightView(false,0,true,R.drawable.tag_menu_sub_ok,false,0);
        setTitleView(getResources().getString(R.string.gps_status_title));
        hListView = (HorizontalListView)findViewById(R.id.horizon_listview);

        //获取当前星数
        GPSStatusManager.getInstance(this,this).getGpsUsedInFix();
        gpsStatusList = GPSStatusManager.getInstance(this,this).getGpsStatusList();

        if(null!=gpsStatusList && gpsStatusList.size()>0){
            Logg.i(TAG,"=gpsStatusList.size()="+gpsStatusList.size());
        }else{
            initData();
        }
        initUI();
    }

    private void initData() {
        for(int i=0;i<=7;i++){
            GpsSvInfo gpsSvInfo =new GpsSvInfo();
            gpsSvInfo.setAzimuth(0);
            gpsSvInfo.setElevation(0);
            gpsSvInfo.setPrn(0);
            gpsSvInfo.setSnr(0);
            gpsStatusList.add(gpsSvInfo);
        }
    }


    public void initUI(){
        hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),gpsStatusList);
        hListViewAdapter.setmGpsStatusListSize(gpsStatusList.size());
        hListView.setAdapter(hListViewAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if(hListView!=null){
            hListView=null;
        }
        if(hListViewAdapter!=null){
            hListViewAdapter=null;
        }
        GPSStatusManager.getInstance(this,this).recordLocation(false);
        super.onDestroy();
    }


    @Override
    public void onPostExecute() {
        gpsStatusList = GPSStatusManager.getInstance(this,this).getGpsStatusList();
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(null!=gpsStatusList && gpsStatusList.size()>0){
                    Logg.i(TAG,"=gpsStatusList.size()="+gpsStatusList.size());
                }else{
                    initData();
                }
                hListViewAdapter.setmGpsStatusListSize(gpsStatusList.size());
                hListViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
