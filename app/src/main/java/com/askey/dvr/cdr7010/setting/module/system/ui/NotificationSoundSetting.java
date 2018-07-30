package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:36
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:36
 * 修改备注：
 */
public class NotificationSoundSetting extends BaseActivity {

    private ImageView notificationView;
    int maxVolume, currentVolume, oldVolume;
    private AudioManager mAudioManager;
    private SoundPool pool;
    private TextView title;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sound_setting);
        contentResolver = getContentResolver();
        //音量控制,初始化定义
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        int notifyVolume = Settings.Global.getInt(contentResolver, AskeySettings.Global.SYSSET_NOTIFY_VOL, 3);
        mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notifyVolume, 0);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_tv);
        title.setText(getResources().getString(R.string.tv_system_settings_notification_sound_volume));
        notificationView = (ImageView) findViewById(R.id.iv_notification);
        refreshView();
        oldVolume = currentVolume;
        setRightView(true, true, true);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(true, R.drawable.tag_menu_sub_increase, true, R.drawable.tag_menu_sub_ok, true, R.drawable.tag_menu_sub_decrease);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                currentVolume = currentVolume - 1;
                if (currentVolume < 0) {
                    currentVolume = 0;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVolume, 0);
//                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_NOTIFY_VOL, currentVolume);
                refreshView();
                testSound();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                currentVolume = currentVolume + 1;
                if (currentVolume > 5) {
                    currentVolume = 5;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVolume, 0);
//                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_NOTIFY_VOL, currentVolume);
                refreshView();
                testSound();
                break;
            case KeyEvent.KEYCODE_ENTER:
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVolume, 0);
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_NOTIFY_VOL, currentVolume);
                finish();
                break;
            case KeyEvent.KEYCODE_BACK:
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, oldVolume, 0);
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_NOTIFY_VOL, oldVolume);
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void refreshView() {
        getNotificationSoundInfo();
        if (currentVolume == 0) {
            notificationView.setImageDrawable(getDrawable(R.drawable.icon_large_noti_volume_none));
        } else if (currentVolume == 1) {
            notificationView.setImageDrawable(getDrawable(R.drawable.icon_large_noti_volume1));
        } else if (currentVolume == 2) {
            notificationView.setImageDrawable(getDrawable(R.drawable.icon_large_noti_volume2));
        } else if (currentVolume == 3) {
            notificationView.setImageDrawable(getDrawable(R.drawable.icon_large_noti_volume3));
        } else if (currentVolume == 4) {
            notificationView.setImageDrawable(getDrawable(R.drawable.icon_large_noti_volume4));
        } else if (currentVolume == 5) {
            notificationView.setImageDrawable(getDrawable(R.drawable.icon_large_noti_volume5));
        }
    }

    private void testSound() {
        if (pool != null) {
            pool.release();
        }
        pool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
        pool.load(this, R.raw.test_voice_settings, 1);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1, 1, 1, 0, 1);
            }
        });
    }

    private void getNotificationSoundInfo() {
        //最大音量
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        Log.i("NotificationSound", "==maxVolume=" + maxVolume);
        //当前音量
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        Log.i("NotificationSound", "==currentVolume=" + currentVolume);
    }
}
