package com.hzj.hpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hzj.hpai.R;
import com.hzj.hpai.bean.Node;
import com.hzj.hpai.utils.LogUtils;

/**
 * ppt发布成功页面
 */
public class PostSuccessActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {


    private ImageView qr_img;
    private Button copy_bt;
    private Button preview_bt;
    private Button sure_bt;
    private TextView title_tv;
    private RadioButton back_bt;

    private Intent getIntent;
    private Node getNode;

    @Override
    protected void findViewById() {
        qr_img = ((ImageView) findViewById(R.id.img_qr));
        copy_bt = ((Button) findViewById(R.id.copy_bt));
        preview_bt = ((Button) findViewById(R.id.preview_bt));
        sure_bt = ((Button) findViewById(R.id.sure_bt));
        title_tv = ((TextView) findViewById(R.id.title_tv));
        title_tv.setText("发布成功信息");
        back_bt = ((RadioButton) findViewById(R.id.back_bt));

        back_bt.setOnClickListener(this);
        qr_img.setOnLongClickListener(this);
        copy_bt.setOnClickListener(this);
        preview_bt.setOnClickListener(this);
        sure_bt.setOnClickListener(this);

        setimg();
    }

    private void setimg() {
        if (!TextUtils.isEmpty(getNode.getQrUrl())) {
            Glide.with(getApplicationContext()).load(getNode.getQrUrl()).into(qr_img);

        }
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_post_success);
        getIntent = getIntent();
        if (getIntent != null) {
            getNode = (Node) getIntent.getSerializableExtra("PostSuccessActivity");
            if (getNode!=null){
                LogUtils.d("-----------------------"+getNode.toString());
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.copy_bt:
                Toast.makeText(this, "复制成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.preview_bt:

                Intent intent = new Intent(this, PreViewActivity.class);
                intent.putExtra("PreViewActivity", getNode.getUrl());
                startActivity(intent);
               // Toast.makeText(this, "请到作品中查看！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.back_bt:
            case R.id.sure_bt:
                finish();
                break;

        }

    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.img_qr:
                Toast.makeText(this, "复制成功！", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }
}
