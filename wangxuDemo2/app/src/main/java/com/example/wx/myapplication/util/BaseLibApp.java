package com.example.wx.myapplication.util;

import android.app.Application;
import android.content.pm.ApplicationInfo;

public class BaseLibApp {
    private static Application application;

    public static Application get() {
        return application;
    }

    public static void init(Application app) {
        application = app;

    }



    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug() {
        try {
            ApplicationInfo info = application.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
