package com.zblcloud.synclite.support.util;

import android.app.Application;
import android.provider.Settings;
import android.text.TextUtils;

import com.zblcloud.synclite.MainApplictation;
import com.zblcloud.synclite.support.persistence.PreferenceUtil;

import java.util.UUID;

/**
 * 获取设备唯一ID
 */
public class DeviceIDUtil {
    public static String getDeviceID() {
        Application application = MainApplictation.getApplictation();
        //获取设备唯一标识
        String deviceID = PreferenceUtil.getInstance().getDeviceID();
        if (!TextUtils.isEmpty(deviceID)) {

            deviceID = Settings.System.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);

            if (TextUtils.isEmpty(deviceID)) {
                //尝试获取SERIAL码
                try {
                    deviceID = android.os.Build.class.getField("SERIAL").get(null).toString();
                } catch (Exception e) {
                    //发生错误的情况下，使用随机生成的UUID
                    deviceID = UUID.randomUUID().toString();
                    e.printStackTrace();
                }
            }
            PreferenceUtil.getInstance().saveDeviceID(deviceID);
        }
        return "android" + deviceID;
    }
}
