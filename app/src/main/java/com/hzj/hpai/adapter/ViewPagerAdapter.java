package com.hzj.hpai.adapter;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by zx
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<ImageView> views;

    public ViewPagerAdapter(List<ImageView> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        if (views != null) return views.size();
        else return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {//???
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//第一：将当前视图添加到container中，第二：返回当前View
        ((ViewPager) container).addView(views.get(position), 0);
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//从当前container中删除指定位置（position）的View
        ((ViewPager) container).removeView(views.get(position));
    }
}
