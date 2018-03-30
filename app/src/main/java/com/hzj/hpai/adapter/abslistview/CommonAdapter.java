package com.hzj.hpai.adapter.abslistview;

import android.content.Context;
import android.view.View;
import com.hzj.hpai.adapter.abslistview.base.ItemViewDelegate;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);

            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        AutoUtils.autoSize(holder.getConvertView());
        super.onViewHolderCreated(holder, itemView);
    }
}
