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

//        initData();
//        initUI();

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

        initUI();

    }

    private void initData() {
        GpsSvInfo gpsSvInfo =new GpsSvInfo();
        gpsSvInfo.setAzimuth((float) 1.0);
        gpsSvInfo.setElevation((float) 1.0);
        gpsSvInfo.setPrn(72);
        gpsSvInfo.setSnr(001);
        gpsStatusList.add(gpsSvInfo);

        GpsSvInfo gpsSvInfo1 =new GpsSvInfo();
        gpsSvInfo1.setAzimuth((float) 1.0);
        gpsSvInfo1.setElevation((float) 1.0);
        gpsSvInfo1.setPrn(39);
        gpsSvInfo1.setSnr(007);
        gpsStatusList.add(gpsSvInfo1);

        GpsSvInfo gpsSvInfo2 =new GpsSvInfo();
        gpsSvInfo2.setAzimuth((float) 1.0);
        gpsSvInfo2.setElevation((float) 1.0);
        gpsSvInfo2.setPrn(0);
        gpsSvInfo2.setSnr(010);
        gpsStatusList.add(gpsSvInfo2);

        GpsSvInfo gpsSvInfo3 =new GpsSvInfo();
        gpsSvInfo3.setAzimuth((float) 1.0);
        gpsSvInfo3.setElevation((float) 1.0);
        gpsSvInfo3.setPrn(44);
        gpsSvInfo3.setSnr(11);
        gpsStatusList.add(gpsSvInfo3);

        GpsSvInfo gpsSvInfo4 =new GpsSvInfo();
        gpsSvInfo4.setAzimuth((float) 1.0);
        gpsSvInfo4.setElevation((float) 1.0);
        gpsSvInfo4.setPrn(68);
        gpsSvInfo4.setSnr(18);
        gpsStatusList.add(gpsSvInfo4);

        GpsSvInfo gpsSvInfo5 =new GpsSvInfo();
        gpsSvInfo5.setAzimuth((float) 1.0);
        gpsSvInfo5.setElevation((float) 1.0);
        gpsSvInfo5.setPrn(24);
        gpsSvInfo5.setSnr(27);
        gpsStatusList.add(gpsSvInfo5);

//        GpsSvInfo gpsSvInfo6 =new GpsSvInfo();
//        gpsSvInfo6.setAzimuth((float) 1.0);
//        gpsSvInfo6.setElevation((float) 1.0);
//        gpsSvInfo6.setPrn(24);
//        gpsSvInfo6.setSnr(28);
//        gpsStatusList.add(gpsSvInfo6);
//
//        GpsSvInfo gpsSvInfo7 =new GpsSvInfo();
//        gpsSvInfo7.setAzimuth((float) 1.0);
//        gpsSvInfo7.setElevation((float) 1.0);
//        gpsSvInfo7.setPrn(59);
//        gpsSvInfo7.setSnr(30);
//        gpsStatusList.add(gpsSvInfo7);
    }


    public void initUI(){
        marqueeTextView = (MarqueeTextView) findViewById(R.id.marquee_text);
        marqueeTextView.setText(getString(R.string.system_setting_install_leveler));
        setRightView(false,0,true,R.drawable.tag_menu_sub_ok,false,0);
        setTitleView(getResources().getString(R.string.gps_status_title));

        hListView = (HorizontalListView)findViewById(R.id.horizon_listview);
        if(null != gpsStatusList && gpsStatusList.size()>0){
            hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(),gpsStatusList);
            hListView.setAdapter(hListViewAdapter);
        }

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
