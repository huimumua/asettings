package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.filemanagement.SdcardInfo;
import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseKeyAudioActivity;
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
public class SdcardInformation extends BaseKeyAudioActivity {
    private static final String TAG = "SdcardInformation";
    private TextView normal, event, picture;
    private String backslash = "/";
    private SDcardReceiver sdCardReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcard_information);

        initView();
        initData();
        initBroadCast();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sdCardReceiver);
    }

    private void initBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("file");
        intentFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        sdCardReceiver = new SDcardReceiver();
        registerReceiver(sdCardReceiver, intentFilter);
    }

    private void initData() {
        List<SdcardInfo> sdcardInfo = FileManager.getInstance().getSdcardInfo(this);
        if (sdcardInfo != null && sdcardInfo.size() > 0) {
            String totalSize = sdcardInfo.get(0).getTotalSize();
            int normal1CurrentSize = Integer.valueOf(sdcardInfo.get(0).getNormal1CurrentSize());
            int normal2CurrentSize = Integer.valueOf(sdcardInfo.get(0).getNormal2CurrentSize());
            String normalCurrentSize = normal1CurrentSize+normal2CurrentSize+"";
            String eventCurrentSize = sdcardInfo.get(0).getEventCurrentSize();
            String parkingCurrentSize = sdcardInfo.get(0).getParkingCurrentSize();
            String pictureCurrentSize = sdcardInfo.get(0).getPictureCurrentSize();
            String normalSize = sdcardInfo.get(0).getNormalSize();
            String eventSize = sdcardInfo.get(0).getEventSize();
            String parkingSize = sdcardInfo.get(0).getParkingSize();
            String pictureSize = sdcardInfo.get(0).getPictureSize();
            Logg.i(TAG, "==getTotalSize==" + totalSize);
            Logg.i(TAG, "==getNormalCurrentSize==" + normalCurrentSize);
            Logg.i(TAG, "==getEventCurrentSize==" + eventCurrentSize);
            Logg.i(TAG, "==getParkingCurrentSize==" + parkingCurrentSize);
            Logg.i(TAG, "==getPictureCurrentSize==" + pictureCurrentSize);
            Logg.i(TAG, "==getNormalSize==" + normalSize);
            Logg.i(TAG, "==getEventSize==" + eventSize);
            Logg.i(TAG, "==getParkingSize==" + parkingSize);
            Logg.i(TAG, "==getPictureSize==" + pictureSize);
            normal.setText(normalCurrentSize + backslash + normalSize);
            event.setText(eventCurrentSize + backslash + eventSize);
            picture.setText(pictureCurrentSize + backslash + pictureSize);
        } else {
            TextView title = (TextView) this.findViewById(R.id.sdcard_setting_information);
            ImageView normanTitle = (ImageView) this.findViewById(R.id.sdcard_normal_dir);
            ImageView eventTitle = (ImageView) this.findViewById(R.id.sdcard_event_dir);
            TextView parkingTitle = (TextView) this.findViewById(R.id.sdcard_parking_dir);
            TextView pictureTitle = (TextView) this.findViewById(R.id.sdcard_picture_dir);
            title.setVisibility(View.VISIBLE);
            title.setText(getResources().getString(R.string.sdcard_not_exist));
            normanTitle.setVisibility(View.GONE);
            eventTitle.setVisibility(View.GONE);
            parkingTitle.setVisibility(View.GONE);
            pictureTitle.setVisibility(View.GONE);
        }
    }

    private void initView() {
        normal = (TextView) this.findViewById(R.id.sdcard_normal_dir_count);
        event = (TextView) this.findViewById(R.id.sdcard_event_dir_count);
        picture = (TextView) this.findViewById(R.id.sdcard_picture_dir_count);

        setRightView(false, 0, true, R.drawable.tag_menu_sub_ok, false, 0);

        String title = getResources().getString(R.string.sdcard_setting_information);
        setTitleView(title, R.drawable.icon_submenu_sdcard);

        setBottomView(R.drawable.tag_menu_sub_cancel);

    }

    @Override
    public void onKeyShortPressed(int keyCode) {
        super.onKeyShortPressed(keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_ENTER:
                finish();
                break;
        }
    }

    class SDcardReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_MEDIA_EJECT.equals(intent.getAction())) {
                Log.d(TAG, "onReceive: ");
                finish();
            }
        }
    }

}
