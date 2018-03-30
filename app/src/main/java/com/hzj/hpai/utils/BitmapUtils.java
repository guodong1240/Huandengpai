package com.hzj.hpai.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by zx on 2017/8/28.
 */

public class BitmapUtils {
    public static Bitmap readBitmap(Context context, int resid){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.RGB_565;
        options.inPurgeable=true;
        options.inInputShareable=true;
        InputStream inputStream=context.getResources().openRawResource(resid);
        return BitmapFactory.decodeStream(inputStream,null,options);

    }
}
