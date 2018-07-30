package com.zblcloud.synclite;

import android.app.Application;

public class MainApplictation extends Application {

    private static MainApplictation applictation;

    @Override
    public void onCreate() {
        super.onCreate();
        this.applictation = this;
    }

    public static MainApplictation getApplictation() {
        return applictation;
    }
}
