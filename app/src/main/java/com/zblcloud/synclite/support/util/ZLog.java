package com.zblcloud.synclite.support.util;

import android.util.Log;

public class ZLog {

    private static final String DEFAULT_TAG = "ZLog";

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void i(String msg) {
        Log.i(DEFAULT_TAG, msg);
    }

    public static void w(String msg) {
        Log.w(DEFAULT_TAG, msg);
    }

    public static void e(String msg) {
        Log.e(DEFAULT_TAG, msg);
    }

    public static void v(String msg) {
        Log.v(DEFAULT_TAG, msg);
    }

    public static void d(String msg) {
        Log.d(DEFAULT_TAG, msg);
    }
}
