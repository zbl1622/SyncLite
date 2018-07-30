package com.zblcloud.synclite.support.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.zblcloud.synclite.MainApplictation;

/**
 * Preference存储
 */
public class PreferenceUtil {

    private static final String PREFERENCE_MAIN_SPACE_NAME = "synclite";

    private static PreferenceUtil preferenceUtil;

    public static PreferenceUtil getInstance() {
        if (preferenceUtil == null) {
            synchronized (PreferenceUtil.class) {
                if (preferenceUtil == null) {
                    preferenceUtil = new PreferenceUtil();
                }
            }
        }
        return preferenceUtil;
    }

    private SharedPreferences sp;

    private PreferenceUtil() {
        this.sp = MainApplictation.getApplictation().getSharedPreferences(PREFERENCE_MAIN_SPACE_NAME, Context.MODE_PRIVATE);
    }

    private static final String KEY_DEVICE_ID = "key_device_id";

    public void saveDeviceID(String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_DEVICE_ID, value);
        editor.commit();
    }

    public String getDeviceID() {
        return sp.getString(KEY_DEVICE_ID, "");
    }
}
