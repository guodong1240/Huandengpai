package com.hzj.hpai.utils;

import android.util.Log;

public class LogUtils {


    public static boolean showLog = true;//debug默认测试，release默认正式包
    // 现在正式环境测试环境配置由build.gradle文件中的 以下代码决定
    // buildConfigField "boolean", "NET_MODE", "false"//false测试服务器
   // public static boolean netMode = BuildConfig.NET_MODE;
    public static String tag = "pai";

    public static void d(String local, String msg) {
        if (showLog)
            Log.d(local, msg);
    }

    public static void t(String local, String msg) {
        if (showLog)
            Log.d(tag, local + "\n" + msg);
    }

    public static void d(String msg) {
        if (showLog)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (showLog)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (showLog)
            Log.e(tag, msg);
    }

    public static void i(String msg) {
        if (showLog)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (showLog)
            Log.v(tag, msg);
    }

    public static void v(String msg) {
        if (showLog)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (showLog)
            Log.w(tag, msg);
    }

    public static void w(String msg) {
        if (showLog)
            Log.w(tag, msg);
    }

    public static void e(String msg) {
        if (showLog)
            Log.e(tag, msg);
    }

    public static void printStack() {
        Throwable throwable = new Throwable();
        Log.w(tag, Log.getStackTraceString(throwable));
    }
}

