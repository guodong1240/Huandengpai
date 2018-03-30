package com.kf5.sdk.ticket.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.kf5.sdk.helpcenter.entity.Attachment;
import com.kf5.sdk.system.ui.ImageActivity;
import com.kf5.sdk.system.base.BaseItemClickListener;
import com.kf5.sdk.system.entity.Field;
import com.kf5.sdk.system.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * author:chosen
 * date:2016/10/20 17:06
 * email:812219713@qq.com
 */

public class AttachmentItemClickListener extends BaseItemClickListener {

    private List<Attachment> list;

    public AttachmentItemClickListener(List<Attachment> list, Context context) {
        super(context);
        this.list = list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String[] name = list.get(position).getName().split("\\.");
        if (!Utils.isImage(name[1])) {
            return;
        }
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Attachment attachment = list.get(i);
            String[] strings = attachment.getName().split("\\.");
            if (Utils.isImage(strings[1])) {
                urls.add(attachment.getContent_url());
            }
        }
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(Field.EXTRA_IMAGE_INDEX, urls.indexOf(list.get(position).getContent_url()));
        intent.putStringArrayListExtra(Field.EXTRA_IMAGE_URLS, (ArrayList<String>) urls);
        context.startActivity(intent);
    }

}