package com.hzj.hpai.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hzj.hpai.R;
import com.hzj.hpai.bean.ChangeStatusBean;
import com.hzj.hpai.bean.DetailBean;
import com.hzj.hpai.bean.Node;
import com.hzj.hpai.item.Constents;
import com.hzj.hpai.networkrequests.CommonListener;
import com.hzj.hpai.networkrequests.SendActtionTool;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 制作PPT
 */
public class MakepptActivity extends BaseActivity implements View.OnClickListener, CommonListener {

    private RadioButton back_bt;
    private TextView title_tv;
    private Node intentNode;
    private EditText ed_title;
    private RadioButton bt_category;
    private RadioButton bt_voice;
    private EditText ed_introduce;
    private LinearLayout saveInfo_ly;
    private LinearLayout save_enter_ly;
    private LinearLayout enter_ly;
    private ReceiveChangeBroadcast broadcast;
    private DetailBean detailBean;


    @Override
    protected void findViewById() {
        back_bt = ((RadioButton) findViewById(R.id.back_bt));
        back_bt.setOnClickListener(this);
        title_tv = ((TextView) findViewById(R.id.title_tv));
        title_tv.setText("基本信息");
        ed_introduce = ((EditText) findViewById(R.id.ed_info_introduce));
        ed_title = ((EditText) findViewById(R.id.ed_title));

        bt_category = ((RadioButton) findViewById(R.id.tv_catgory));
        bt_voice = ((RadioButton) findViewById(R.id.tv_voice));
        bt_category.setOnClickListener(this);
        bt_voice.setOnClickListener(this);

        saveInfo_ly = ((LinearLayout) findViewById(R.id.ll_saveinfo));
        save_enter_ly = ((LinearLayout) findViewById(R.id.ll_saveinfo_enter));
        enter_ly = ((LinearLayout) findViewById(R.id.ll_enter));

        saveInfo_ly.setOnClickListener(this);
        save_enter_ly.setOnClickListener(this);
        enter_ly.setOnClickListener(this);

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_makeppt);
        Intent intent = getIntent();
        if (intent != null) {
            intentNode = (Node) intent.getSerializableExtra("MakepptActivity");
            getURLData();
        }

        //注册广播
        broadcast = new ReceiveChangeBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("MakepptActivity");
        registerReceiver(broadcast, filter);
    }

    //获得ppt基本信息
    private void getURLData() {
        long timeMillis = System.currentTimeMillis();
        if (intentNode.getNid() != null) {
            Map<String, String> map = UrlTool.getMapParams("nid", intentNode.getNid());
            String str = new Gson().toJson(map);
            SendActtionTool.getJson(Constents.GETPPT_DETAIL, str, "getDetail", this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_bt:
                finish();
                break;
            case R.id.tv_catgory:
                Intent intent = new Intent(MakepptActivity.this, CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_voice:
                Intent intent1 = new Intent(this, MakeVoiceActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_saveinfo:
                upLoadChange("saveChange");
                break;
            case R.id.ll_saveinfo_enter:
                upLoadChange("saveAndEnter");
                break;
            case R.id.ll_enter:
                Intent intent2 = new Intent(this, RecordingActivity.class);
                intent2.putExtra("RecordingActivity", intentNode.getNid());
                startActivity(intent2);
                break;

        }

    }

    private void upLoadChange(String saveChange) {
        String title = ed_title.getText().toString();
        String category = bt_category.getText().toString();
        String voice = bt_voice.getText().toString();
        String vv = "";
        if ("无配音".equals(voice)) {
            vv = Constents.VoiceStyle_No;
        } else if ("一对一配音".equals(voice)) {
            vv = Constents.VoiceStyle_HAVE;
        }
        String summary = ed_introduce.getText().toString();
        ChangeStatusBean bean = new ChangeStatusBean(title, summary, category, vv);
        Map<String, ChangeStatusBean> map = new LinkedHashMap<>();
        map.put(intentNode.getNid(), bean);
        String string = new Gson().toJson(map);
        LogUtils.d("=====change==" + string);
        SendActtionTool.getJson(Constents.CHANGE_PPT_ONLINE, string, saveChange, this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }

    @Override
    public void onSuccess(Object action, Object value) {
        LogUtils.d("onSuccess" + value.toString());
        switch (((String) action)) {
            case "getDetail":
                try {
                    detailBean = new Gson().fromJson(((JSONObject) value).getString("data"), DetailBean.class);
                    setView(detailBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "saveChange":
                Toast.makeText(this, "修改状态成功", Toast.LENGTH_SHORT).show();
                break;
            case "saveAndEnter":
                Intent intent = new Intent(this, RecordingActivity.class);
                intent.putExtra("RecordingActivity", intentNode.getNid());
                startActivity(intent);

                break;
        }
    }

    private void setView(DetailBean detailBean) {
        if (detailBean.getCategoryValue() != null) {
            bt_category.setText(detailBean.getCategoryValue());
        }
        String voicestyle = detailBean.getVoiceStyleValue();
        if ("202".equals(voicestyle)) {
            bt_voice.setText("一对一配音");
            save_enter_ly.setVisibility(View.VISIBLE);
            enter_ly.setVisibility(View.VISIBLE);

        } else {
            bt_voice.setText("无配音");
            save_enter_ly.setVisibility(View.GONE);
            enter_ly.setVisibility(View.GONE);
        }
        ed_introduce.setText(detailBean.getSummary());
            ed_title.setText(detailBean.getTitle());

    }

    @Override
    public void onFaile(Object action, Object value) {
        LogUtils.d("onFaile" + value.toString());

    }

    @Override
    public void onException(Object action, Object value) {
        LogUtils.d("onException" + value.toString());
    }

    @Override
    public void onStart(Object action) {

    }

    @Override
    public void onFinish(Object action) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class ReceiveChangeBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("category") != null) {
                bt_category.setText(intent.getStringExtra("category"));

            }
            if (intent.getStringExtra("voice") != null) {
                bt_voice.setText(intent.getStringExtra("voice"));
                if ("无配音".equals(intent.getStringExtra("voice"))) {
                    enter_ly.setVisibility(View.GONE);
                    save_enter_ly.setVisibility(View.GONE);
                } else {
                    enter_ly.setVisibility(View.VISIBLE);
                    save_enter_ly.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
