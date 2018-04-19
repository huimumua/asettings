package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

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
    private TextView normal,event,parking,picture;
    private String backslash= "/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_information);

        initview();
        initData();

    }

    private void initData() {
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
            normal.setText(normalCurrentSize+backslash+normalSize);
            event.setText(eventCurrentSize+backslash+eventSize);
            parking.setText(parkingCurrentSize+backslash+parkingSize);
            picture.setText(pictureCurrentSize+backslash+pictureSize);
        }else{
            TextView title = this.findViewById(R.id.sdcard_setting_information);
            TextView normanTitle = this.findViewById(R.id.sdcard_normal_dir);
            TextView eventTitle = this.findViewById(R.id.sdcard_event_dir);
            TextView parkingTitle = this.findViewById(R.id.sdcard_parking_dir);
            TextView pictureTitle = this.findViewById(R.id.sdcard_picture_dir);
            title.setText(getResources().getString(R.string.sdcard_not_exist));
            normanTitle.setVisibility(View.GONE);
            eventTitle.setVisibility(View.GONE);
            parkingTitle.setVisibility(View.GONE);
            pictureTitle.setVisibility(View.GONE);
        }
    }

    private void initview() {
        normal = this.findViewById(R.id.sdcard_normal_dir_count);
        event = this.findViewById(R.id.sdcard_event_dir_count);
        parking = this.findViewById(R.id.sdcard_parking_dir_count);
        picture = this.findViewById(R.id.sdcard_picture_dir_count);
    }


}
