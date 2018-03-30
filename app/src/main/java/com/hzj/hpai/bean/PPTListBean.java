package com.hzj.hpai.bean;

/**
 * Created by zx on 2016/12/28.
 */

import java.util.List;

/**
 * {
 * "nodes": [
 * {
 * "node": {
 * "nid": "7610",
 * "title": "幻灯派-内容营销-B2BContentMarketing-20160419-17",
 * "pptstatus": "已发布",
 * "pptstatuskey": "1",
 * "postdate": "2016.05.06",
 * "coverimage": "http://img1.huandengpai.com/ppt/dev.huandengpai.com/share/image/7609/Slide1-s.JPG",
 * "filename": " 幻灯派-内容营销-B2BContentMarketing-20160419-17.pptx",
 * "istransform": "1",
 * "url": "http://kaifa.huandengpai.com/app/c/event2a/465/7610",
 * "qrUrl": "http://kaifa.huandengpai.com/util/image/qrcode?str=http://kaifa.huandengpai.com/app/c/event2a/465/7610&size=10"
 * }
 * },
 */

//ResultBean<VideoPlayInfo> result =
// new Gson().fromJson(value.toString(), new TypeToken<ResultBean<VideoPlayInfo>>() {
//        }.getType());


//List<FocusPictureModel<VideoPlayInfo>> tempdatas =
// new Gson().fromJson(((JSONObject) value).getString("data"), new TypeToken<List<FocusPictureModel<VideoPlayInfo>>>()
// {}.getType());


public class PPTListBean extends BaseBean {
    private List<NodeParent> nodes;

    public List<NodeParent> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeParent> nodes) {
        this.nodes = nodes;
    }
}
