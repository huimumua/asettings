package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.system.bean.GpsSvInfo;
import com.askey.dvr.cdr7010.setting.module.system.controller.GPSStatusManager;
import com.askey.dvr.cdr7010.setting.util.Logg;

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
    private ArrayList<GpsSvInfo> gpsStatusList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_installation_satellite);


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
