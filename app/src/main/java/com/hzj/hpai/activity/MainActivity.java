package com.hzj.hpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hzj.hpai.R;
import com.hzj.hpai.networkrequests.CommonListener;
import com.hzj.hpai.networkrequests.SendActtionTool;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.view.GradientIconView;
import com.hzj.hpai.view.GradientTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 废弃
 */

public class MainActivity extends AppCompatActivity implements CommonListener, View.OnClickListener {

    private String url = "http://kaifa.huandengpai.com/apiajax/ppt/app/pptuser/UserLogin?data=";

    private String getUrl = " http://m.mp.huandengpai.com/apiajax/ppt/app/pptuser/UserLogin?data=";

    private String jsonStr = "";

    private List<GradientIconView> mTabIconIndicator = new ArrayList<GradientIconView>();
    private List<GradientTextView> mTabTextIndicator = new ArrayList<GradientTextView>();

    private GradientIconView workIcon;
    private GradientIconView excellentIcon;
    private GradientIconView voiceIcon;
    private GradientIconView listIcon;
    private GradientIconView aboutmeIcon;
    private GradientTextView workTv;
    private GradientTextView excellentTv;
    private GradientTextView voiceTv;
    private GradientTextView listTv;
    private GradientTextView aboutmeTv;
    private LinearLayout workly;
    private LinearLayout excellently;
    private LinearLayout voicely;
    private LinearLayout listly;
    private LinearLayout aboutmely;
    private FrameLayout fragment_container;

    String sid = "xmqOfhszLodaB0wvftt8aSiJ9GmbIADF4IpPikHTYG8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        ShareSDK.initSDK(getApplicationContext(), "1a54a96ceb91d");

        //接口请求
        Map<String, String> map = UrlTool.getMapParams("password", "a123", "username", "15941237200");
        jsonStr = new Gson().toJson(map);
        SendActtionTool.getJson(getUrl, jsonStr, "", this);

        String sidurl = "http://kaifa.huandengpai.com/apiajax/ppt/app/pptuser/getAppPPTUserSimpleInfoBySid?data=";
        Map<String, String> stringMap = UrlTool.getMapParams("sid", sid);
        String json = new Gson().toJson(stringMap);
        SendActtionTool.getJson(sidurl, json, "", this);
    }

    private void initView() {

        // Tab栏
        workIcon = ((GradientIconView) findViewById(R.id.home_icon));
        workIcon.setOnClickListener(this);
        mTabIconIndicator.add(workIcon);
        workIcon.setIconAlpha(1.0f);

        excellentIcon = ((GradientIconView) findViewById(R.id.second_icon));
        excellentIcon.setOnClickListener(this);
        mTabIconIndicator.add(excellentIcon);

        voiceIcon = ((GradientIconView) findViewById(R.id.three_icon));
        voiceIcon.setOnClickListener(this);
        mTabIconIndicator.add(voiceIcon);

        listIcon = ((GradientIconView) findViewById(R.id.four_icon));
        listIcon.setOnClickListener(this);
        mTabIconIndicator.add(listIcon);

        aboutmeIcon = ((GradientIconView) findViewById(R.id.five_icon));
        aboutmeIcon.setOnClickListener(this);
        mTabIconIndicator.add(aboutmeIcon);

        workTv = ((GradientTextView) findViewById(R.id.home_tv));
        workTv.setOnClickListener(this);
        mTabTextIndicator.add(workTv);
        workTv.setTextViewAlpha(1.0f);

        excellentTv = ((GradientTextView) findViewById(R.id.second_tv));
        excellentTv.setOnClickListener(this);
        mTabTextIndicator.add(excellentTv);

        voiceTv = ((GradientTextView) findViewById(R.id.three_tv));
        voiceTv.setOnClickListener(this);
        mTabTextIndicator.add(voiceTv);

        listTv = ((GradientTextView) findViewById(R.id.four_tv));
        listTv.setOnClickListener(this);
        mTabTextIndicator.add(listTv);

        aboutmeTv = ((GradientTextView) findViewById(R.id.five_tv));
        aboutmeTv.setOnClickListener(this);
        mTabTextIndicator.add(aboutmeTv);

        workly = ((LinearLayout) findViewById(R.id.home_tab));
        workly.setOnClickListener(this);

        excellently = ((LinearLayout) findViewById(R.id.second_tab));
        excellently.setOnClickListener(this);

        voicely = ((LinearLayout) findViewById(R.id.three_tab));
        voicely.setOnClickListener(this);

        listly = ((LinearLayout) findViewById(R.id.four_tab));
        listly.setOnClickListener(this);

        aboutmely = ((LinearLayout) findViewById(R.id.five_tab));
        aboutmely.setOnClickListener(this);

        initFragment();


    }

    private void initFragment() {
        fragment_container = ((FrameLayout) findViewById(R.id.fragment_container));


    }


    @Override
    public void onSuccess(Object action, Object value) {

        Log.d("---success-----", value.toString());

    }

    @Override
    public void onFaile(Object action, Object value) {
        Log.d("onfail-----", value.toString());

    }

    @Override
    public void onException(Object action, Object value) {
        Log.d("--onException", value.toString());
    }

    @Override
    public void onStart(Object action) {

    }

    @Override
    public void onFinish(Object action) {
        Log.d("finish", "------");

    }

    @Override
    public void onClick(View view) {
        resetOtherTabs();
        switch (view.getId()) {
            case R.id.home_icon:
            case R.id.home_tv:
            case R.id.home_tab:
                Log.d("---", "click");
                Intent intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.three_icon:
            case R.id.three_tv:
            case R.id.three_tab:
               showShare();
                break;
        }

    }


    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        resetOtherTabIcons();
        resetOtherTabText();
    }

    /**
     * 重置其他的Tab icon
     */
    private void resetOtherTabIcons() {
        for (int i = 0; i < mTabIconIndicator.size(); i++) {
            mTabIconIndicator.get(i).setIconAlpha(0);
        }
    }

    /**
     * 重置其他的Tab text
     */
    private void resetOtherTabText() {
        for (int i = 0; i < mTabTextIndicator.size(); i++) {
            mTabTextIndicator.get(i).setTextViewAlpha(0);
        }
    }


    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

}

