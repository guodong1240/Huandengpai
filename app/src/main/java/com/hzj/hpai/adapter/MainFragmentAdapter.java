package com.hzj.hpai.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> listfragment=new ArrayList<>();
    public MainFragmentAdapter(FragmentManager fm, List<Fragment>listfragment) {
        super(fm);
        this.listfragment=listfragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listfragment.size();
    }
}
