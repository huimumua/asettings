package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SdcardInfo;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.controller.FileManager;
import com.askey.dvr.cdr7010.setting.util.Logg;

import java.util.List;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/17 15:50
 * 修改人：skysoft
 * 修改时间：2018/4/17 15:50
 * 修改备注：
 */
public class SdcardInformation extends BaseActivity{
    private static final String TAG = "SdcardInformation";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_information);

        List<SdcardInfo> sdcardInfo = FileManager.getInstance().getSdcardInfo();
        if(sdcardInfo!=null && sdcardInfo.size()>0){
            String totalSize = sdcardInfo.get(0).getTotalSize();
            String normalCurrentSize = sdcardInfo.get(0).getNormalCurrentSize();
            String eventCurrentSize = sdcardInfo.get(0).getEventCurrentSize();
            String parkingCurrentSize = sdcardInfo.get(0).getParkingCurrentSize();
            String pictureCurrentSize = sdcardInfo.get(0).getPictureCurrentSize();
            String normalSize = sdcardInfo.get(0).getNormalSize();
            String eventSize = sdcardInfo.get(0).getEventSize();
            String parkingSize = sdcardInfo.get(0).getParkingSize();
            String pictureSize = sdcardInfo.get(0).getPictureSize();
            Logg.i(TAG,"==getTotalSize=="+totalSize);
            Logg.i(TAG,"==getNormalCurrentSize=="+normalCurrentSize);
            Logg.i(TAG,"==getEventCurrentSize=="+eventCurrentSize);
            Logg.i(TAG,"==getParkingCurrentSize=="+parkingCurrentSize);
            Logg.i(TAG,"==getPictureCurrentSize=="+pictureCurrentSize);
            Logg.i(TAG,"==getNormalSize=="+normalSize);
            Logg.i(TAG,"==getEventSize=="+eventSize);
            Logg.i(TAG,"==getParkingSize=="+parkingSize);
            Logg.i(TAG,"==getPictureSize=="+pictureSize);
        }
    }


}
