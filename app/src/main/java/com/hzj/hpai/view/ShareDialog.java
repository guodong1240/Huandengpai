package com.hzj.hpai.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.hzj.hpai.R;
import com.hzj.hpai.bean.ShareInfo;
import com.hzj.hpai.utils.LogUtils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author zx
 * @time 16/4/13 下午4:42
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private View contentView;
    private Context mContext;
    private Activity activity;

    public ShareInfo getmShareInfo() {
        return mShareInfo;
    }

    public void setmShareInfo(ShareInfo mShareInfo) {
        this.mShareInfo = mShareInfo;
    }

    private ShareInfo mShareInfo;

    public ShareDialog(Context context, ShareInfo shareInfo, Activity activity) {
        super(context);
        ShareSDK.initSDK(context);
        mContext = context;
        this.mShareInfo = shareInfo;
        this.activity = activity;
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
//        ShareSDK.initSDK(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //这句代码换掉dialog默认背景，否则dialog的边缘发虚透明而且很宽
        // 总之达不到想要的效果
        // getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        contentView = inflater.inflate(R.layout.pop_share, null);
        contentView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_to_top));
        setContentView(contentView);


        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeigh = metrics.heightPixels;
        LogUtils.d("==height==" + screenHeigh);

        Window mWindow = getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        //x 为正代表向右移，y为正代表向下移
        lp.x = -20;
        if (screenHeigh != 0) {
            lp.y = screenHeigh - 100;
        } else {
            lp.y = 400;

        }
        View layout = LayoutInflater.from(mContext).inflate(R.layout.pop_share, null);
        this.setContentView(layout);

        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        findViewById(R.id.sharelayout_wechat).setOnClickListener(this);
        findViewById(R.id.sharelayout_friendcircle).setOnClickListener(this);
        findViewById(R.id.sharelayout_sina).setOnClickListener(this);
        findViewById(R.id.sharelayout_qqplace).setOnClickListener(this);

        setCancelable(true);
//
//
//        setContentView(layout, new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        //
//        Window win = getWindow();
//        win.setWindowAnimations(R.style.AnimBottom); //设置窗口弹出动画
//        win.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = win.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//
//        LogUtils.d("screenHeight==" + ScreenUtils.getScreenHeight(mContext));
//        LogUtils.d("getStatusHeight==" + ScreenUtils.getStatusHeight(mContext));
////        lp.height =ScreenUtils.getScreenHeight(mContext)+ScreenUtils.getStatusHeight(mContext);
//        win.setAttributes(lp);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sharelayout_wechat:
                Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                showShare(weChat.getName());

//                shareWechat();
                break;
            case R.id.sharelayout_friendcircle:
                Platform weChatMoment = ShareSDK.getPlatform(WechatMoments.NAME);
                showShare(weChatMoment.getName());
//                shareWechatCycle();
                break;
            case R.id.sharelayout_sina:
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                showShare(weibo.getName());
//                shareWeibo();
                break;
            case R.id.sharelayout_qqplace:
                Platform qzone = ShareSDK.getPlatform(QQ.NAME);
                showShare(qzone.getName());
//                shareQQ();
                break;
        }

    }

    private void showaBottomPop() {


    }

    /**
     * @param platformToShare 指定直接分享平台名称（一旦设置了平台名称，则九宫格将不会显示）
     */

    private void showShare(String platformToShare) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        if (!TextUtils.isEmpty(platformToShare)) {
            oks.setPlatform(platformToShare);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mShareInfo.getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mShareInfo.getShareUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mShareInfo.getText());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mShareInfo.getShareUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(mShareInfo.getText());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(mContext.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(mShareInfo.getShareUrl());
//        oks.setImageUrl(Constants.Share_Image_Url);
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, cn.sharesdk.framework.Platform.ShareParams paramsToShare) {
                if ("QQ".equals(platform.getName())) {
                    paramsToShare.setTitle(mShareInfo.getTitle());
                    paramsToShare.setText(mShareInfo.getText());
                    paramsToShare.setTitleUrl(mShareInfo.getShareUrl());
                    paramsToShare.setUrl(mShareInfo.getShareUrl());
                    paramsToShare.setImageUrl(mShareInfo.getImageUrl());

                }

                if ("SinaWeibo".equals(platform.getName())) {
                    paramsToShare.setTitle(mShareInfo.getTitle());
                    paramsToShare.setText(mShareInfo.getText() + "http://" + mShareInfo.getShareUrl());
                    paramsToShare.setTitleUrl(mShareInfo.getShareUrl());
                    paramsToShare.setUrl(mShareInfo.getShareUrl());
                    paramsToShare.setImageUrl(mShareInfo.getImageUrl());

                }

                if ("Wechat".equals(platform.getName())) {
                    paramsToShare.setTitle(mShareInfo.getTitle());
//                    paramsToShare.setText(mShareInfo.getText());
                    paramsToShare.setTitleUrl(mShareInfo.getShareUrl());
                    paramsToShare.setUrl(mShareInfo.getShareUrl());
                    paramsToShare.setImageUrl(mShareInfo.getImageUrl());
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);

                }

                if ("WechatMoments".equals(platform.getName())) {
                    paramsToShare.setTitle(mShareInfo.getText());
//                    paramsToShare.setText(mShareInfo.getText());
                    paramsToShare.setTitleUrl(mShareInfo.getShareUrl());
                    paramsToShare.setUrl(mShareInfo.getShareUrl());
//                    paramsToShare.setImageUrl(Constants.Share_Image_Url);
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
//                    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_192x192);
//                    paramsToShare.setImageData(bitmap);
                    paramsToShare.setImageUrl(mShareInfo.getImageUrl());

                }

            }
        });
//        oks.setCallback(paListener );
// 启动分享GUI
        oks.show(mContext);
        dismiss();
    }

//    private void shareWechat() {
//
//        Wechat.ShareParams sp = new Wechat.ShareParams();
//        sp.setTitle(mShareInfo.getTitle());
//        sp.setTitleUrl(mShareInfo.getTitleUrl()); // 标题的超链接
//        sp.setText(mShareInfo.getText());
//        sp.setImageUrl(mShareInfo.getImageUrl());
//        sp.setSiteUrl(mShareInfo.getShareUrl());
//        Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
//        weChat.setPlatformActionListener(paListener); // 设置分享事件回调
//        weChat.share(sp);
//    }
//
//    private void shareWechatCycle() {
//
//        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
//        sp.setTitle(mShareInfo.getTitle());
//        sp.setTitleUrl(mShareInfo.getTitleUrl()); // 标题的超链接
//        sp.setText(mShareInfo.getText());
//        sp.setImageUrl(mShareInfo.getImageUrl());
//        sp.setSiteUrl(mShareInfo.getShareUrl());
//
//        Platform weChat = ShareSDK.getPlatform(WechatMoments.NAME);
//        weChat.setPlatformActionListener(paListener); // 设置分享事件回调
//        weChat.share(sp);
//    }
//
//    private void shareWeibo() {
//
//        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//        sp.setTitle(mShareInfo.getTitle());
//        sp.setTitleUrl(mShareInfo.getTitleUrl()); // 标题的超链接
//        sp.setText(mShareInfo.getText());
//        sp.setImageUrl(mShareInfo.getImageUrl());
//        sp.setSiteUrl(mShareInfo.getShareUrl());
//
//        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//        weibo.setPlatformActionListener(paListener); // 设置分享事件回调
//        weibo.share(sp);
//    }
//
//    private void shareQQ() {
//        QZone.ShareParams sp = new QZone.ShareParams();
//
//        sp.setTitle("测试分享的标题");
//        sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
//        sp.setText("测试分享的文本");
//        sp.setImageUrl("http://bbs.mob.com/uc_server/avatar.php?uid=23921&size=middle");
//        sp.setSiteUrl("发布分享网站的地址");
//        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
////        sp.setTitle(mShareInfo.getTitle());
////        sp.setTitleUrl(mShareInfo.getTitleUrl()); // 标题的超链接
////        sp.setText(mShareInfo.getText());
////        sp.setImageUrl(mShareInfo.getImageUrl());
////        sp.setSiteUrl(mShareInfo.getShareUrl());
//        qzone.setPlatformActionListener(paListener); // 设置分享事件回调
//        qzone.share(sp);
//    }
//
//    private final PlatformActionListener paListener = new PlatformActionListener() {
//        @Override
//        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//            Utils.toast("分享成功");
//            ShareDialog.this.dismiss();
//        }
//
//        @Override
//        public void onError(Platform platform, int i, Throwable throwable) {
//            Utils.toast("分享失败");
//            ShareDialog.this.dismiss();
//        }
//
//        @Override
//        public void onCancel(Platform platform, int i) {
//            Utils.toast("分享取消");
//            ShareDialog.this.dismiss();
//            LogUtils.e("paListener---onCancel");
//        }
//    };


}
