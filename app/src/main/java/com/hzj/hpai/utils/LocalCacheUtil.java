package com.hzj.hpai.utils;

import android.os.Environment;

import java.io.File;

/**
 * 本地缓存处理工具类
 * Created by zx on 2017/1/5.
 */

public class LocalCacheUtil {
    private static String ROOT_NAME = "HuandengPai";
    /**
     * 根目录
     **/
    public static File rootFilePath;
    /**
     * 语音存储
     **/
    public static File voiceFilePath;
    public static File temporaryVoicePath;

    static {
        File rootFile = new File(Environment.getExternalStorageDirectory(),
                ROOT_NAME);
        if (!rootFile.exists()) {
            rootFile.mkdir();
        }
        rootFilePath = rootFile;
        initLocalCacheDir();
    }

    /**
     * 初始化本地缓存目录
     */
    public static void initLocalCacheDir() {
        // 根目录创建
        File rootFile = new File(Environment.getExternalStorageDirectory(),
                ROOT_NAME);

        if (!rootFile.exists()) {
            rootFile.mkdir();
        }
        LogUtils.t("initLocalCacheDir()", rootFile.getAbsoluteFile().toString());
        rootFilePath = rootFile;
        // 创建缓存子目录
        File voice = new File(rootFile, "voice");
        voice.mkdirs();
        voiceFilePath = voice;

        File temporaryFile = new File(rootFile, "temporary");
        temporaryFile.mkdirs();
        temporaryVoicePath = temporaryFile;
    }
}
