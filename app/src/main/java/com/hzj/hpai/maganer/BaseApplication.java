package com.hzj.hpai.maganer;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
//import android.support.multidex.MultiDex;


import java.util.List;

/**
 * Created by zx on 2016/12/26.
 */

public class BaseApplication extends Application {
    private  static Context context;

    public   static Context getContext(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //KF5SDKInitializer.init(context);
        //突破最大65535方法限制
    //    MultiDex.install(this);
//
//        //初始化okhttp
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LoggerInterceptor("TAG"))
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                //其他配置
//                .build();
//        OkHttpUtils.initClient(okHttpClient);

    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }
}
