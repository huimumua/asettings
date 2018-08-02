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
 * 创建时间：2018/4/8 13:38
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:38
 * 修改备注：
 */
public class PlaybackSoundSetting extends BaseActivity {
    private ImageView playBackSoundView;
    private int currentVolumeMusic, oldVolumeMusic;
    private AudioManager mAudioManager;
    private SoundPool pool;
    private TextView title;
    private ContentResolver contentResolver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_sound_setting);
        contentResolver = getContentResolver();
        //音量控制,初始化定义
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        initView();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title_tv);
        title.setText(getResources().getString(R.string.tv_system_settings_playback_volume));
        playBackSoundView = (ImageView) findViewById(R.id.iv_playback);
        refreshView();
        oldVolumeMusic = currentVolumeMusic;
        setRightView(true, true, true);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(true, R.drawable.tag_menu_sub_increase, true, R.drawable.tag_menu_sub_ok, true, R.drawable.tag_menu_sub_decrease);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                currentVolumeMusic = currentVolumeMusic - 1;
                if (currentVolumeMusic < 0) {
                    currentVolumeMusic = 0;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolumeMusic, 0);
//                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_PLAYBACK_VOL, currentVolumeMusic);
                refreshView();
                testPlaybackSound();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                currentVolumeMusic = currentVolumeMusic + 1;
                if (currentVolumeMusic > 5) {
                    currentVolumeMusic = 5;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolumeMusic, 0);
//                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_PLAYBACK_VOL, currentVolumeMusic);
                refreshView();
                testPlaybackSound();
                break;
            case KeyEvent.KEYCODE_ENTER:
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolumeMusic, 0);
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_PLAYBACK_VOL, currentVolumeMusic);
                finish();
                break;
            case KeyEvent.KEYCODE_BACK:
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, oldVolumeMusic, 0);
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_PLAYBACK_VOL, oldVolumeMusic);
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void refreshView() {
        getPlaybackSoundInfo();
        if (currentVolumeMusic == 0) {
            playBackSoundView.setImageDrawable(getDrawable(R.drawable.icon_large_playback_volume_none));
        } else if (currentVolumeMusic == 1) {
            playBackSoundView.setImageDrawable(getDrawable(R.drawable.icon_large_playback_volume1));
        } else if (currentVolumeMusic == 2) {
            playBackSoundView.setImageDrawable(getDrawable(R.drawable.icon_large_playback_volume2));
        } else if (currentVolumeMusic == 3) {
            playBackSoundView.setImageDrawable(getDrawable(R.drawable.icon_large_playback_volume3));
        } else if (currentVolumeMusic == 4) {
            playBackSoundView.setImageDrawable(getDrawable(R.drawable.icon_large_playback_volume4));
        } else if (currentVolumeMusic == 5) {
            playBackSoundView.setImageDrawable(getDrawable(R.drawable.icon_large_playback_volume5));
        }
    }

    private void testPlaybackSound() {
        if (pool != null) {
            pool.release();
        }
        pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        pool.load(this, R.raw.test_voice_settings, 1);
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1, 1, 1, 0, 1);
            }
        });

    }

    private void getPlaybackSoundInfo() {
        //最大音量
        int maxVolumeMusic = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.i("PlaybackSoundSetting", "==maxVolumeMusic=" + maxVolumeMusic);
        //当前音量
        currentVolumeMusic = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i("PlaybackSoundSetting", "==currentVolumeMusic=" + currentVolumeMusic);
    }
}
