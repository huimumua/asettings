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

public class PlaybackSoundService extends Service {
    private AudioManager mAudioManager = (AudioManager) SettingApplication.getContext().getSystemService(this.AUDIO_SERVICE);
    private int currentVolume;

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
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            Log.i("PlaybackSoundService", "==currentVolume==" + currentVolume);
            return currentVolume;
        }

        @Override
        public boolean setPlaybackSoundVolume(int volume) throws RemoteException {
            if ((volume >= 0) && (volume <= 5)) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
            } else {
                return false;
            }
            return true;
        }
    };
}
