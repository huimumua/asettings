// PlaybackSound.aidl
package com.askey.dvr.cdr7010.setting;

// Declare any non-default types here with import statements

interface PlaybackSound {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    //get current playback music volume
    int getPlaybackSoundVolume();
    //set playback sound mute or restore previous volume.
    boolean setPlaybackSoundMute();
}
