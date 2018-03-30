package com.hzj.hpai.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzj.hpai.R;
import com.hzj.hpai.bean.Node;
import com.hzj.hpai.view.LodingDialog;

import db.bean.LocalPPT;

/**
 * Created by zx on 2016/12/29.
 */

public class DialogUtils {
    private static TextView share_tv;

    public interface DialogMakeListener {
        void onDeleteListener(LocalPPT ppt);

        void onCountinue(LocalPPT ppt);
    }

    public interface DialogTipListener {
        void onDeleteListener(LocalPPT ppt);
    }

    public interface DialogPListListener {
        void onMakeListener(Node ppt);

        void onPostListener(Node ppt);

        void onDeleteLsitener(Node ppt);

        void onShareListener(Node ppt);
        void onCancleListener();
    }

    public static Dialog createMakeTipDialog(final Activity context, final DialogPListListener listener, final Node node, String contentStr, String sureStr) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_tip, null);
        TextView content_tv = (TextView) view.findViewById(R.id.content_tv);
        TextView cancle_tv = (TextView) view.findViewById(R.id.tip_cancle_tv);
        TextView delete_tv = (TextView) view.findViewById(R.id.tip_delete_tv);
        delete_tv.setText(sureStr);
        content_tv.setText(contentStr);
        LinearLayout layout = ((LinearLayout) view.findViewById(R.id.tip_layout));
        final Dialog dialog = new Dialog(context, R.style.tip_dialog);
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteLsitener(node);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return dialog;
    }

    public static Dialog createTipDialog(final Activity context, final DialogTipListener listener, final LocalPPT ppt, String posiBt) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View tipview = inflater.inflate(R.layout.dialog_tip, null);
        TextView cancle_tv = (TextView) tipview.findViewById(R.id.tip_cancle_tv);
        TextView delete_tv = (TextView) tipview.findViewById(R.id.tip_delete_tv);
        delete_tv.setText(posiBt);
        LinearLayout layout = ((LinearLayout) tipview.findViewById(R.id.tip_layout));
        final Dialog dialog = new Dialog(context, R.style.tip_dialog);
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteListener(ppt);
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return dialog;
    }

    public static Dialog createMakepptDialog(final Activity context, final DialogPListListener listener, final Node ppt, String[] str) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_plist, null);
        TextView make_tv = (TextView) view.findViewById(R.id.make_tv);
        make_tv.setText(str[0]);
        TextView post_tv = (TextView) view.findViewById(R.id.post_tv);
        post_tv.setText(str[1]);
        TextView delete_tv = (TextView) view.findViewById(R.id.delete_tv);
        delete_tv.setText(str[2]);
        TextView cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);
        cancle_tv.setText(str[3]);
        View divider = view.findViewById(R.id.divider);
        share_tv = ((TextView) view.findViewById(R.id.share_tv));
        if (str.length == 4) {
            share_tv.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else if (str.length == 5) {
            share_tv.setVisibility(View.VISIBLE);
            share_tv.setText(str[str.length - 1]);
            divider.setVisibility(View.VISIBLE);
        }
        LinearLayout layout = ((LinearLayout) view.findViewById(R.id.makeppt_ly));
        final Dialog dialog = new Dialog(context, R.style.LocalPPT_dialog);
        share_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onShareListener(ppt);
                dialog.dismiss();
            }
        });
        post_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPostListener(ppt);
                dialog.dismiss();
            }
        });
        make_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMakeListener(ppt);
                dialog.dismiss();
            }
        });
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteLsitener(ppt);
                dialog.dismiss();
            }
        });
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancleListener();
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return dialog;
    }

    public static Dialog createStatusDailog(final Activity context, String titleStr, String contentStr, boolean showtitle) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_status, null);
        TextView title_tv = (TextView) view.findViewById(R.id.title_tv);
        TextView content_tv = (TextView) view.findViewById(R.id.content_tv);
        TextView sure_tv = (TextView) view.findViewById(R.id.sure_tv);
        if (showtitle) {
            title_tv.setVisibility(View.VISIBLE);
            title_tv.setText(titleStr);
        } else {
            title_tv.setVisibility(View.INVISIBLE);
        }
        content_tv.setText(contentStr);
        LinearLayout layout = ((LinearLayout) view.findViewById(R.id.status_ly));
        final Dialog dialog = new Dialog(context, R.style.LocalPPT_dialog);
        dialog.setCancelable(true);
        sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return dialog;
    }

    public static Dialog createPPTDialog(final Activity context, final DialogMakeListener listener, final LocalPPT ppt) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_local, null);
        TextView update_tv = (TextView) view.findViewById(R.id.update_tv);
        TextView delete_tv = (TextView) view.findViewById(R.id.delete_tv);
        TextView cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ppt_ly);
        final Dialog localDialog = new Dialog(context, R.style.LocalPPT_dialog);
        update_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCountinue(ppt);
                localDialog.dismiss();
            }
        });
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteListener(ppt);
                localDialog.dismiss();
            }
        });
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localDialog.dismiss();
            }
        });
        localDialog.setCancelable(true);
        localDialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return localDialog;
    }


    public static LodingDialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
//        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
//        // 加载动画
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
//                context, R.anim.anim_loding);
//        // 使用ImageView显示动画
//        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
//
////        // 加载动画
////
//        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
//        tipTextView=v.findv
//         tipTextView.setText(R.string.loading_down);// 设置加载信息
        ImageView img = (ImageView) v.findViewById(R.id.img);
        Glide.with(context).load(R.drawable.new_loding).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
        LodingDialog loadingDialog = new LodingDialog(context,
                R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);// 可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));// 设置布局
//        loadingDialog.setAnim(hyperspaceJumpAnimation, spaceshipImage);
        return loadingDialog;
    }


}
