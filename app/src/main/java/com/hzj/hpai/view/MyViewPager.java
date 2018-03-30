package com.hzj.hpai.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zx on 2017/1/10.
 */

public class MyViewPager extends ViewPager {

    private boolean isCanScroll = true;
    private Context mcontext;

    public MyViewPager(Context context) {
        super(context);
        this.mcontext = context;
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return true;
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (isCanScroll) {
            super.scrollTo(x, y);
        }
    }

    public void setIsCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}
