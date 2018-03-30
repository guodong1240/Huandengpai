package com.hzj.hpai.utils;


import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by zx on 2016/4/19.
 */
public abstract class WeakHandler extends Handler {
    public final WeakReference<Activity> mActivity;

    public WeakHandler(Activity act) {
        mActivity = new WeakReference(act);
        if (act != null) {
            LogUtils.d("act != null---WeakHandler");
        } else {
            LogUtils.d("act == null---WeakHandler");
        }
    }

    @Override
    public void handleMessage(Message msg) {
        if (mActivity.get() == null) {
            return;
        }
        conventHandleMessage( msg);
        super.handleMessage(msg);
    }

    public abstract void conventHandleMessage( Message msg);

}
