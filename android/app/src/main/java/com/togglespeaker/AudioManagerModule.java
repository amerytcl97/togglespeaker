package com.togglespeaker;

import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AudioManagerModule extends ReactContextBaseJavaModule {
    AudioManager audioManager;
    AudioDeviceInfo speakerDevice = null;

    final static int FORCE_MEDIA = 1;
    final static int FORCE_NONE = 0;
    final static int FORCE_SPEAKER = 1;

    static Boolean IS_SPEAKER = null;

    AudioManagerModule(ReactApplicationContext context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager = context.getSystemService(AudioManager.class);
        }

    }

    @Override
    public String getName() {
        return "AudioManagerModule";
    }

    @ReactMethod
    public void getSpeakerMode(final Promise promise) {
        promise.resolve(IS_SPEAKER);
        System.out.println("Check is speaker mode on");
        System.out.println(audioManager.isSpeakerphoneOn());
    }

    @ReactMethod
    public void setSpeaker(Boolean speakerMode, final Promise promise)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        /**
         * Code is with the help of Michael
         * https://stackoverflow.com/questions/12036221/how-to-turn-speaker-on-off-programmatically-in-android-4-0
         */

        Class audioSystemClass = Class.forName("android.media.AudioSystem");
        Method setForceUse = audioSystemClass.getMethod("setForceUse", int.class, int.class);

        if (audioManager == null) {
            promise.reject("Error setting up Audio Manager. Device might not be supported");
            return;
        }

        if (speakerMode) {
            setForceUse.invoke(null, FORCE_MEDIA, FORCE_SPEAKER);
            promise.resolve("Force speaker successfully");
            IS_SPEAKER = true;
        } else {
            setForceUse.invoke(null, FORCE_MEDIA, FORCE_NONE);
            promise.resolve("Disabled force speaker successfully");
            IS_SPEAKER = false;
        }
        /**
         * else {
         * //audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
         * if (audioManager.isSpeakerphoneOn() == true && speakerMode == true) {
         * promise.resolve("Speaker mode is already on");
         * } else if (audioManager.isSpeakerphoneOn() == false && speakerMode == false)
         * {
         * promise.resolve("Speaker mode is already off");
         * } else {
         * 
         * setForceUse.invoke(null, FORCE_MEDIA, FORCE_SPEAKER);
         * // audioManager.setSpeakerphoneOn(speakerMode);
         * }
         * }
         */
    }

}
