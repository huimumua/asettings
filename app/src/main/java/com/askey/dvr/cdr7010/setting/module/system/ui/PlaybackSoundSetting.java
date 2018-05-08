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
    private ImageView playBackSound0, playBackSound1, playBackSound2, playBackSound3, playBackSound4, playBackSound5;
    private int currentVolumeMusic;
    private AudioManager mAudioManager;
    private SoundPool pool;
    private ContentResolver contentResolver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_sound_setting);
        contentResolver = getContentResolver();
        initView();
    }

    private void initView() {
        playBackSound0 = (ImageView) findViewById(R.id.iv_playBackSound0);
        playBackSound1 = (ImageView) findViewById(R.id.iv_playBackSound1);
        playBackSound2 = (ImageView) findViewById(R.id.iv_playBackSound2);
        playBackSound3 = (ImageView) findViewById(R.id.iv_playBackSound3);
        playBackSound4 = (ImageView) findViewById(R.id.iv_playBackSound4);
        playBackSound5 = (ImageView) findViewById(R.id.iv_playBackSound5);
        refreshView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                currentVolumeMusic = currentVolumeMusic - 3;
                if (currentVolumeMusic < 0) {
                    currentVolumeMusic = 0;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolumeMusic, 0);
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_PLAYBACK_VOL, volumeType(currentVolumeMusic));
                refreshView();
                testPlaybackSound();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                currentVolumeMusic = currentVolumeMusic + 3;
                if (currentVolumeMusic > 15) {
                    currentVolumeMusic = 15;
                }
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolumeMusic, 0);
                Settings.Global.putInt(contentResolver, AskeySettings.Global.SYSSET_PLAYBACK_VOL, volumeType(currentVolumeMusic));
                refreshView();
                testPlaybackSound();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void refreshView() {
        getPlaybackSoundInfo();
        if (volumeType(currentVolumeMusic) == 0) {
            playBackSound0.setVisibility(View.VISIBLE);
            playBackSound1.setVisibility(View.INVISIBLE);
            playBackSound2.setVisibility(View.INVISIBLE);
            playBackSound3.setVisibility(View.INVISIBLE);
            playBackSound4.setVisibility(View.INVISIBLE);
            playBackSound5.setVisibility(View.INVISIBLE);
        } else if (volumeType(currentVolumeMusic) == 1) {
            playBackSound0.setVisibility(View.INVISIBLE);
            playBackSound1.setVisibility(View.VISIBLE);
            playBackSound2.setVisibility(View.INVISIBLE);
            playBackSound3.setVisibility(View.INVISIBLE);
            playBackSound4.setVisibility(View.INVISIBLE);
            playBackSound5.setVisibility(View.INVISIBLE);
        } else if (volumeType(currentVolumeMusic) == 2) {
            playBackSound0.setVisibility(View.INVISIBLE);
            playBackSound1.setVisibility(View.VISIBLE);
            playBackSound2.setVisibility(View.VISIBLE);
            playBackSound3.setVisibility(View.INVISIBLE);
            playBackSound4.setVisibility(View.INVISIBLE);
            playBackSound5.setVisibility(View.INVISIBLE);
        } else if (volumeType(currentVolumeMusic) == 3) {
            playBackSound0.setVisibility(View.INVISIBLE);
            playBackSound1.setVisibility(View.VISIBLE);
            playBackSound2.setVisibility(View.VISIBLE);
            playBackSound3.setVisibility(View.VISIBLE);
            playBackSound4.setVisibility(View.INVISIBLE);
            playBackSound5.setVisibility(View.INVISIBLE);
        } else if (volumeType(currentVolumeMusic) == 4) {
            playBackSound0.setVisibility(View.INVISIBLE);
            playBackSound1.setVisibility(View.VISIBLE);
            playBackSound2.setVisibility(View.VISIBLE);
            playBackSound3.setVisibility(View.VISIBLE);
            playBackSound4.setVisibility(View.VISIBLE);
            playBackSound5.setVisibility(View.INVISIBLE);
        } else if (volumeType(currentVolumeMusic) == 5) {
            playBackSound0.setVisibility(View.INVISIBLE);
            playBackSound1.setVisibility(View.VISIBLE);
            playBackSound2.setVisibility(View.VISIBLE);
            playBackSound3.setVisibility(View.VISIBLE);
            playBackSound4.setVisibility(View.VISIBLE);
            playBackSound5.setVisibility(View.VISIBLE);
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
        //音量控制,初始化定义
        mAudioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        //最大音量
        int maxVolumeMusic = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.i("PlaybackSoundSetting", "==maxVolumeMusic=" + maxVolumeMusic);
        //当前音量
        currentVolumeMusic = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.i("PlaybackSoundSetting", "==currentVolumeMusic=" + currentVolumeMusic);

    }

    private int volumeType(int currentVolumeMusic) {
        int volumeType = 0;
        if (currentVolumeMusic == 0) {
            volumeType = 0;
        } else if (currentVolumeMusic > 0 && currentVolumeMusic < 4) {
            volumeType = 1;
        } else if (currentVolumeMusic > 3 && currentVolumeMusic < 7) {
            volumeType = 2;
        } else if (currentVolumeMusic > 6 && currentVolumeMusic < 10) {
            volumeType = 3;
        } else if (currentVolumeMusic > 9 && currentVolumeMusic < 13) {
            volumeType = 4;
        } else if (currentVolumeMusic > 12 && currentVolumeMusic < 16) {
            volumeType = 5;
        }
        return volumeType;
    }
}
