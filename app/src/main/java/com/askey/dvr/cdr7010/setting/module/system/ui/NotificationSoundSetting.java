package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.util.Const;

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

    private ImageView notifiSound0, notifiSound1, notifiSound2, notifiSound3, notifiSound4, notifiSound5;
    int maxVolume, currentVolume;
    private AudioManager mAudioManager;
    private SoundPool pool;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_sound_setting);
        contentResolver = getContentResolver();
        initView();
    }

    private void initView() {
        notifiSound0 = (ImageView) findViewById(R.id.iv_notifisound0);
        notifiSound1 = (ImageView) findViewById(R.id.iv_notifisound1);
        notifiSound2 = (ImageView) findViewById(R.id.iv_notifisound2);
        notifiSound3 = (ImageView) findViewById(R.id.iv_notifisound3);
        notifiSound4 = (ImageView) findViewById(R.id.iv_notifisound4);
        notifiSound5 = (ImageView) findViewById(R.id.iv_notifisound5);
        refreshView();
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
                Settings.Global.putInt(contentResolver, Const.SYSSET_notify_vol, currentVolume);
                refreshView();
                testSound();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                currentVolume = currentVolume + 1;
                if (currentVolume > 5) {
                    currentVolume = 5;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, currentVolume, 0);
                Settings.Global.putInt(contentResolver, Const.SYSSET_notify_vol, currentVolume);
                refreshView();
                testSound();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void refreshView() {
        getNotificationSoundInfo();
        if (currentVolume == 0) {
            notifiSound0.setVisibility(View.VISIBLE);
            notifiSound1.setVisibility(View.INVISIBLE);
            notifiSound2.setVisibility(View.INVISIBLE);
            notifiSound3.setVisibility(View.INVISIBLE);
            notifiSound4.setVisibility(View.INVISIBLE);
            notifiSound5.setVisibility(View.INVISIBLE);
        } else if (currentVolume == 1) {
            notifiSound0.setVisibility(View.INVISIBLE);
            notifiSound1.setVisibility(View.VISIBLE);
            notifiSound2.setVisibility(View.INVISIBLE);
            notifiSound3.setVisibility(View.INVISIBLE);
            notifiSound4.setVisibility(View.INVISIBLE);
            notifiSound5.setVisibility(View.INVISIBLE);
        } else if (currentVolume == 2) {
            notifiSound0.setVisibility(View.INVISIBLE);
            notifiSound1.setVisibility(View.VISIBLE);
            notifiSound2.setVisibility(View.VISIBLE);
            notifiSound3.setVisibility(View.INVISIBLE);
            notifiSound4.setVisibility(View.INVISIBLE);
            notifiSound5.setVisibility(View.INVISIBLE);
        } else if (currentVolume == 3) {
            notifiSound0.setVisibility(View.INVISIBLE);
            notifiSound1.setVisibility(View.VISIBLE);
            notifiSound2.setVisibility(View.VISIBLE);
            notifiSound3.setVisibility(View.VISIBLE);
            notifiSound4.setVisibility(View.INVISIBLE);
            notifiSound5.setVisibility(View.INVISIBLE);
        } else if (currentVolume == 4) {
            notifiSound0.setVisibility(View.INVISIBLE);
            notifiSound1.setVisibility(View.VISIBLE);
            notifiSound2.setVisibility(View.VISIBLE);
            notifiSound3.setVisibility(View.VISIBLE);
            notifiSound4.setVisibility(View.VISIBLE);
            notifiSound5.setVisibility(View.INVISIBLE);
        } else if (currentVolume == 5) {
            notifiSound0.setVisibility(View.INVISIBLE);
            notifiSound1.setVisibility(View.VISIBLE);
            notifiSound2.setVisibility(View.VISIBLE);
            notifiSound3.setVisibility(View.VISIBLE);
            notifiSound4.setVisibility(View.VISIBLE);
            notifiSound5.setVisibility(View.VISIBLE);
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
        //音量控制,初始化定义
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        //最大音量
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        Log.i("NotificationSound", "==maxVolume=" + maxVolume);
        //当前音量
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        Log.i("NotificationSound", "==currentVolume=" + currentVolume);
    }
}
