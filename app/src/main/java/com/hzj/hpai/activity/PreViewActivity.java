package com.hzj.hpai.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzj.hpai.R;
import com.hzj.hpai.bean.RecordParent;
import com.hzj.hpai.bean.RecordeBean;
import com.hzj.hpai.item.Constents;
import com.hzj.hpai.networkrequests.CommonListener;
import com.hzj.hpai.networkrequests.SendActtionTool;
import com.hzj.hpai.tool.NetworkHelper;
import com.hzj.hpai.tool.UrlTool;
import com.hzj.hpai.utils.DialogUtils;
import com.hzj.hpai.utils.LogUtils;
import com.hzj.hpai.utils.Utils;
import com.hzj.hpai.view.LodingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 发布成功后预览页面
 */
public class PreViewActivity extends BaseActivity {

    private WebView webView;
    private LodingDialog lodingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void findViewById() {
        webView = ((WebView) findViewById(R.id.webview));
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.addJavascriptInterface(new JavaScript(), "nativeMethod");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if ((lodingDialog != null) && (!lodingDialog.isShowing())) {
                    lodingDialog.show();
                } else {
                    if (lodingDialog == null) {
                        lodingDialog = DialogUtils.createLoadingDialog(PreViewActivity.this, "");
                        lodingDialog.show();
                    }
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (lodingDialog != null && lodingDialog.isShowing()) {
                    lodingDialog.dismiss();
                }


            }
        });

        Intent intent = getIntent();
        String URL = intent.getStringExtra("PreViewActivity");

        if (NetworkHelper.isNetworkAvailable(getApplicationContext())) {
            if (lodingDialog == null) {
                lodingDialog = DialogUtils.createLoadingDialog(this, "");
            }
            lodingDialog.show();
            //WebView加载web资源
            if (URL != null) {
                URL += "?previewSource=android";
                webView.loadUrl(URL);
            } else {
                Toast.makeText(this, "资源错误！", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "您的网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        //保持当前屏幕常亮显示，即不息屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_pre_view);

    }

    public class JavaScript {
        //调用分享
        @JavascriptInterface
        public void ShareClick(String nid) {
            getShareMessage(nid);
        }

        public void backClick() {
            finish();
        }

    }

    //得到分享需要的信息
    private void getShareMessage(String nid) {
        Map<String, String> map = UrlTool.getMapParams("nid", nid);
        String json = new Gson().toJson(map);
        SendActtionTool.getJson(Constents.GetShareMessage, nid, "shareMessage", new CommonListener() {
            @Override
            public void onSuccess(Object action, Object value) {
                LogUtils.d("onSuccess==" + value.toString());
                try {
                    List<RecordParent.RecordData> datas = new Gson().fromJson(((JSONObject) value).getString("datas"), new TypeToken<List<RecordParent.RecordData>>() {
                    }.getType());
                    if (datas != null) {
                        RecordeBean data = datas.get(0).getData();
                        // LogUtils.d("===="+data.toString());
                        if (data.getTitle() != null && data.getField_pptf_summary() != null && data.getShare_url() != null && data.getField_ppti_image_file() != null) {
                            Utils.shareTo(PreViewActivity.this, data.getTitle(), data.getField_pptf_summary(), data.getShare_url(), data.getField_ppti_image_file(), PreViewActivity.this);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFaile(Object action, Object value) {
                LogUtils.d("onFaile" + value.toString() + action.toString());
            }

            @Override
            public void onException(Object action, Object value) {
                LogUtils.d("onException" + value.toString() + action.toString());

            }

            @Override
            public void onStart(Object action) {

            }

            @Override
            public void onFinish(Object action) {
                LogUtils.d("onfinish");

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
        }
    }
}
