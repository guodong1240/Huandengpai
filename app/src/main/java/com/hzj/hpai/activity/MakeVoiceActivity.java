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
 * ppt制作配音方案
 */
public class MakeVoiceActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private List<String> list;
    private CommonAdapter<String> mAdapter;
    private ListView listview;
    private TextView title_tv;
    private RadioButton back_bt;

    private int choosePostion = -1;

    @Override
    protected void findViewById() {
        title_tv = ((TextView) findViewById(R.id.title_tv));
        title_tv.setText("配音方案");
        back_bt = ((RadioButton) findViewById(R.id.back_bt));
        back_bt.setOnClickListener(this);

        listview = ((ListView) findViewById(R.id.listview));
        list = new ArrayList<>();
        initDate();
    }

    private void initDate() {
        list.add("无配音");
        list.add("一对一配音");
        mAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.category_item, list) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView content_tv = (TextView) viewHolder.getView(R.id.content_tv);
                content_tv.setText(item);
                if (choosePostion == position) {
                    content_tv.setTextColor(Color.BLUE);
                } else {
                    content_tv.setTextColor(Color.BLACK);
                }
            }
        };
        listview.setAdapter(mAdapter);
        listview.setOnItemClickListener(this);

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_make_voice);

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
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent();
        intent.setAction("MakepptActivity");
        if (choosePostion != -1) {
            intent.putExtra("voice", list.get(choosePostion));
            sendBroadcast(intent);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        choosePostion = i;
        mAdapter.notifyDataSetChanged();
    }
}
