package com.askey.dvr.cdr7010.setting.module.system.controller;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.askey.dvr.cdr7010.setting.PlaybackSound;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.module.system.ui.PlaybackSoundSetting;

public class PlaybackSoundService extends Service {
    private AudioManager mAudioManager = (AudioManager) SettingApplication.getContext().getSystemService(this.AUDIO_SERVICE);
    private int currentVolume;
    private int tempVolume = 3;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final PlaybackSound.Stub mBinder = new PlaybackSound.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getPlaybackSoundVolume() throws RemoteException {
            int playbackSoundVolume = 0;
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            Log.i("PlaybackSoundService", "==currentVolume==" + currentVolume);
            if (currentVolume == 0) {
                playbackSoundVolume = 0;
            } else if (0 < currentVolume && currentVolume < 4) {
                playbackSoundVolume = 1;
            } else if (3 < currentVolume && currentVolume < 7) {
                playbackSoundVolume = 2;
            } else if (6 < currentVolume && currentVolume < 10) {
                playbackSoundVolume = 3;
            } else if (9 < currentVolume && currentVolume < 13) {
                playbackSoundVolume = 4;
            } else if (12 < currentVolume && currentVolume < 16) {
                playbackSoundVolume = 5;
            }
            Log.i("PlaybackSoundService", "==playbackSoundVolume==" + playbackSoundVolume);
            return playbackSoundVolume;
        }

        @Override
        public boolean setPlaybackSoundVolume(int volume) throws RemoteException {
            if(volume == 0){
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            }else if(volume == 1){
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 3, 0);
            }else if(volume == 2){
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 6, 0);
            }else if(volume == 3){
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 9, 0);
            }else if(volume == 4){
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 12, 0);
            }else if(volume == 5){
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 15, 0);
            }else {
                return false;
            }
            return true;
        }
    };
}
