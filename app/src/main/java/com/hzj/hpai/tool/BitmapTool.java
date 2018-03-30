package com.hzj.hpai.tool;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzj.hpai.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * @author zxm
 * @2015年1月22日
 * @descripte 图片加载器
 */
public class BitmapTool {
    //    private BitmapUtils bitmapUtils;
    static BitmapTool TOOL;
//    private static BitmapGlobalConfig globalConfig;

    private BitmapTool() {
    }

    /**
     * 得到实例
     */
    public static BitmapTool getInstance() {

        return TOOL = TOOL == null ? new BitmapTool() : TOOL;
    }

    /**
     * 使用picasso显示网络图片
     *
     * @param imageView
     * @param url
     */
    public void display(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
//        LogUtils.t("display", url);
    }

//    /**
//     * @param context
//     * @return 加载图片工具 对象
//     */
//    public BitmapUtils initAdapterUitl(Context context) {
//        initBitmapUtils(context);
//        return bitmapUtils;
//    }
//
//    /**
//     * 展示网络网络图片
//     *
//     * @return
//     */
//    public BitmapUtils getAdapterUitl() {
//        return bitmapUtils;
//    }
//
//    /**
//     * 展示本地图拍
//     *
//     * @param view
//     * @param value
//     */
//    public void showLocalView(ImageView view, String value) {
//        view.setVisibility(0);
//        bitmapUtils.display(view, value);
//    }
//
//    /**
//     * 清除内存和磁盘缓存
//     */
//    public static void clearCache() {
//        globalConfig.clearCache();
//    }

//    /**
//     * 清除内存缓存
//     */
//    public static void clearMemoryCache() {
//        globalConfig.clearMemoryCache();
//    }
//
//    /**
//     * 清除磁盘缓存
//     */
//    public static void clearDiskCache() {
//        globalConfig.clearDiskCache();
//    }

    /**
     * 初始化对象
     *
     * @param context
     */
//    private void initBitmapUtils(Context context) {
//        bitmapUtils = bitmapUtils == null ? new BitmapUtils(context, LocalCacheUtil.pictureFilePath.getAbsolutePath())
//                : bitmapUtils;
//
//        globalConfig = globalConfig == null ? BitmapGlobalConfig.getInstance(
//                context, LocalCacheUtil.pictureFilePath.getAbsolutePath()) : globalConfig;
//    }

    /**
     * 获取图片缓存的大小
     *
     * @return
     */
    public static String GetPicCacheSize(long length) {
        return FormetFileSize(length);
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
            fileSizeString = "0.00MB";
        } else if (fileS < 1048576) {
            //fileSizeString = df.format((double) fileS / 1024) + "KB";
            fileSizeString = "0" + df.format((double) fileS / 1048576) + "MB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static long getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("获取文件大小", "获取失败!");
        }
        return blockSize;
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            LogUtils.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }
}
