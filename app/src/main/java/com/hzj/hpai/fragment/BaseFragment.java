package com.hzj.hpai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zx on 2016/12/30.
 */

public abstract class BaseFragment extends Fragment {
    protected boolean isInit = false;
    protected boolean isLoad = false;
   // private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View  view = setContentView(inflater,container,savedInstanceState);
        isInit = true;
        initView(view);
        isLoadData();
        return view;
    }

    protected abstract void initView(View rootView);
    protected abstract View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    //视图是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isLoadData();
    }

    //加载数据
    private void isLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }

    }

    protected abstract void stopLoad() ;

    protected abstract void lazyLoad();


}
