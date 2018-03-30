package com.hzj.hpai.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzj.hpai.R;
import com.hzj.hpai.bean.VersionBean;


/**
 * Created by zx on 16/8/10.
 */
public class VersionUpdateDialog extends Dialog implements View.OnClickListener {

    private final VersionBean versionBean;
    private ImageView iv_close;
    private TextView tv_download, tv_cancel;
    private TextView tv_msg;
    private Context context;
    private Activity activity;

    public VersionUpdateDialog(Context context, int themeResId, VersionBean versionBean, Activity activity) {
        super(context, themeResId);
        this.versionBean = versionBean;
        this.activity = activity;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_version_update);
        tv_download = (TextView) findViewById(R.id.tv_download);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_cancel.setOnClickListener(this);
        tv_download.setOnClickListener(this);
        tv_msg.setText(versionBean.getVersion_notes());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_download:
                v.setEnabled(false);
                downLoadApk(versionBean.getVersion_download_url(), "huangdengpai_" + versionBean.getVersion() + ".apk");
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void downLoadApk(String url, final String name) {

        Uri uri = Uri.parse(url); //url为你要链接的地址
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
        dismiss();

//        OkHttpUtils//
//                .get()//
//                .url(url)//
//                .build()//
//                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), name)//
//                {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
//                        if (file.exists())
//                            file.delete();
//                    }
//
//
//                    @Override
//                    public void onResponse(File response, int id) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(response),
//                                "application/vnd.android.package-archive");
//                        getContext().startActivity(intent);
//
//                    }
//                });
    }
}
