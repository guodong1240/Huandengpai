package com.hzj.hpai.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hzj.hpai.bean.ShareInfo;
import com.hzj.hpai.view.ShareDialog;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zx on 2016/12/26.
 */

public class Utils {

    //从assets中加载图片资源
    public static Bitmap getImageFromAssetsFile(Activity activity, String fileName) {
        Bitmap image = null;
        AssetManager am = activity.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    //得到系统当前时间
    public static String getCurrentTime() {
        String currentTime = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        currentTime = df.format(new Date());
        return currentTime;
    }

    //分享对话框弹出
    public static void shareTo(Context context, String title, String content, String shareURL, String mShareImgUrl, Activity activity) {
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setText(content);
        shareInfo.setTitle(title);
        shareInfo.setShareUrl(shareURL);
        shareInfo.setImageUrl(mShareImgUrl);
        ShareDialog shareDialog = null;
        if (shareDialog == null) {
            shareDialog = new ShareDialog(context, shareInfo,activity);
        } else {
            shareDialog.setmShareInfo(shareInfo);
        }
        shareDialog.show();
//        shareDiaolg=new ShareDialog(this)
    }

    public static String getAgent(SoftReference<Context> softReference) {

        String agent = "";
        try {
            String ua = System.getProperty("http.agent");
            String packageName = softReference.get().getPackageName();
            PackageInfo info = softReference.get().getPackageManager().getPackageInfo(packageName, 0);
            agent = ua + ", " + packageName + "/" + info.versionName;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return agent;
    }


}
