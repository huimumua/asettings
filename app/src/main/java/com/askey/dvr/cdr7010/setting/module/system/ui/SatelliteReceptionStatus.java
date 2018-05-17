package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.adapter.HorizontalListViewAdapter;
import com.askey.dvr.cdr7010.setting.module.system.bean.GpsSvInfo;
import com.askey.dvr.cdr7010.setting.module.system.controller.GPSStatusManager;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.dvr.cdr7010.setting.view.HorizontalListView;
import com.askey.dvr.cdr7010.setting.widget.MarqueeTextView;

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
public class SatelliteReceptionStatus extends BaseActivity{
    private static final String TAG = "SatelliteReceptionStatus";
    private ArrayList<GpsSvInfo> gpsStatusList = new ArrayList<GpsSvInfo>();
    private HorizontalListView hListView;
    private HorizontalListViewAdapter hListViewAdapter;
    private MarqueeTextView marqueeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_installation_satellite);

        initUI();
//        initData();
        //获取当前星数
        GPSStatusManager.getInstance().getGpsUsedInFix();
        gpsStatusList = GPSStatusManager.getInstance().getGpsStatusList();

        if(null!=gpsStatusList && gpsStatusList.size()>0){
            Logg.i(TAG,"=gpsStatusList.size()="+gpsStatusList.size());
            for (GpsSvInfo gpsSvInfo : gpsStatusList){
                Logg.i(TAG,"=gpsSvInfo.getSnr()="+gpsSvInfo.getSnr());
                Logg.i(TAG,"=gpsSvInfo.getAzimuth()="+gpsSvInfo.getAzimuth());
                Logg.i(TAG,"=gpsSvInfo.getPrn()="+gpsSvInfo.getPrn());
                Logg.i(TAG,"=gpsSvInfo.getElevation()="+gpsSvInfo.getElevation());
            }
        }
    }

    private void initData() {
        for (int i=0;i<6;i++){
            GpsSvInfo gpsSvInfo =new GpsSvInfo();
            gpsSvInfo.setAzimuth((float) 1.0);
            gpsSvInfo.setElevation((float) 1.0);
            gpsSvInfo.setPrn(i);
            gpsSvInfo.setSnr(12);
            gpsStatusList.add(gpsSvInfo);
        }
    }


    public void initUI(){
        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee_text);
        marqueeTextView.setText(getString(R.string.system_setting_install_leveler));
        setRightView(false,0,true,R.drawable.tag_menu_sub_ok,false,0);
        setTitleView(getResources().getString(R.string.gps_status_title));

        hListView = (HorizontalListView)findViewById(R.id.horizon_listview);
        hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),gpsStatusList);
        hListView.setAdapter(hListViewAdapter);

        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                hListViewAdapter.setSelectIndex(position);
                hListViewAdapter.notifyDataSetChanged();

            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
