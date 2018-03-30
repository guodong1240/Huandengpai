package com.hzj.hpai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hzj.hpai.R;
import com.hzj.hpai.adapter.abslistview.CommonAdapter;
import com.hzj.hpai.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * ppt制作——分类
 */
public class CategoryActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private TextView title_tv;
    private RadioButton back_bt;
    private ListView listView;
    private CommonAdapter<String> mAdapter;
    private List<String> list;

    private int choosePostition = -1;

    @Override
    protected void findViewById() {
        title_tv = ((TextView) findViewById(R.id.title_tv));
        title_tv.setText("分类");

        back_bt = ((RadioButton) findViewById(R.id.back_bt));
        back_bt.setOnClickListener(this);

        listView = ((ListView) findViewById(R.id.catgory_listview));
        list = new ArrayList<>();

        initData();
    }

    private void initData() {
        list.add("IT/电信");
        list.add("PPT学");
        list.add("互联网");
        list.add("移动科技");
        list.add("创业创新");
        list.add("商业管理");
        list.add("经济金融");
        list.add("职场成功");
        list.add("生活休闲");
        list.add("娱乐幽默");
        list.add("健康体育");
        list.add("教育学习");
        list.add("人文社科");
        list.add("医药卫生");
        list.add("科技工程");
        list.add("营销销售");

        mAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.category_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, final int position) {
                final TextView view_tv = (TextView) viewHolder.getView(R.id.content_tv);
                viewHolder.setText(R.id.content_tv, item);
                if (choosePostition == position) {
                    view_tv.setTextColor(Color.BLUE);
                } else {
                    view_tv.setTextColor(Color.BLACK);
                }

            }
        };
        listView.setOnItemClickListener(this);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_category);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                finish();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        choosePostition = position;
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent();
        intent.setAction("MakepptActivity");
        if (choosePostition != -1) {
            intent.putExtra("category", list.get(choosePostition));
            sendBroadcast(intent);

        }

    }
}
