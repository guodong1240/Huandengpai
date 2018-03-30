package com.hzj.hpai.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hzj.hpai.R;

/**
 * 支付
 */

public class MPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpay);
    }

    public void BtClick(View view){
        switch (view.getId()){
            //支付宝支付
            case R.id.button_first:


                break;
            //微信支付
            case R.id.button_second:

                break;
        }

    }
}
