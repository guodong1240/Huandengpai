package com.hzj.hpai.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.hzj.hpai.R;
import com.hzj.hpai.utils.Utils;
import com.hzj.hpai.utils.WeakHandler;

/**
 * 引导页
 */

public class GuideActivity extends AppCompatActivity {

    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //隐藏状态栏和ActionBar     SYSTEM_UI_FLAG_FULLSCREEN表示全屏的意思，也就是会将状态栏隐藏
//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//
//        }
        imgView = ((ImageView) findViewById(R.id.img));
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        Bitmap bitmap = Utils.getImageFromAssetsFile(this, "splish.jpg");
        imgView.setImageBitmap(bitmap);
        setAnimation();
    }

    private void setAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgView, "alpha", 0.0f, 1.0f);
        animator.setDuration(1000);
        animator.start();
        mHandler.sendEmptyMessageDelayed(200, 1500);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // 跳转
//                Intent intent = new Intent(GuideActivity.this, WebViewActivity.class);
//                startActivity(intent);
//
//                finish();
//            }
//        }, 2000);
    }

    private WeakHandler mHandler = new WeakHandler(this) {
        @Override
        public void conventHandleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    Intent intent = new Intent(GuideActivity.this, WebViewActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };
}
